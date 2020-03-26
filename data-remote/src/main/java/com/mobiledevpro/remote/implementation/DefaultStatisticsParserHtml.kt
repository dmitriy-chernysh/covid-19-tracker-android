package com.mobiledevpro.remote.implementation

import com.mobiledevpro.data.model.statistic.CoordEntity
import com.mobiledevpro.data.model.statistic.CountyStatisticEntity
import com.mobiledevpro.data.model.statistic.DayStatisticEntity
import com.mobiledevpro.data.model.statistic.StatisticEntity
import com.mobiledevpro.data.repository.parcer.StatisticsParserHtml
import io.reactivex.Single
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DefaultStatisticsParserHtml : StatisticsParserHtml {

    override fun getConfirmedStatistics(): Single<ArrayList<StatisticEntity>> =
        Single.fromCallable { getDataStatistic(CONFIRMED_FILE_NAME) }

    override fun getDeathsStatistics(): Single<ArrayList<StatisticEntity>> =
        Single.fromCallable { getDataStatistic(DEATHS_FILE_NAME) }

    override fun getRecoveredStatistics(): Single<ArrayList<StatisticEntity>> =
        Single.fromCallable { getDataStatistic(RECOVERED_FILE_NAME) }


    private fun getDataStatistic(fileName: String): ArrayList<StatisticEntity> {

        val counties = ArrayList<StatisticEntity>(COUNT_COUNTRIES)

        try {
            val docHtml: Document = Jsoup.connect("$BASE_URL$fileName").get()

            val titlesHtml = docHtml
                .getElementById("${LINE_IDENTIFICATION}1")
                .parent()
                .getElementsByTag(LINE_SEPARATOR)

            for (i in 2..COUNT_COUNTRIES) {

                val countryHtml = docHtml
                    .getElementById("$LINE_IDENTIFICATION${i}")
                    .parent()
                    .getElementsByTag(ROW_SEPARATOR)

                val countryName = countryHtml.toStringValue(2)

                var provinceName = countryHtml.toStringValue(1).apply {
                    when (this.isEmpty()) {
                        true -> countryName
                        else -> this
                    }
                }

                if (provinceName.isEmpty()) provinceName = countryName

                val countyStatisticEntity = CountyStatisticEntity(
                    provinceName = provinceName,
                    countryName = countryName
                )

                val coordEntity = CoordEntity(
                    lat = countryHtml.toDoubleValue(3),
                    long = countryHtml.toDoubleValue(4)
                )

                val dayCountsEntity = ArrayList<DayStatisticEntity>()

                for (j in 5 until countryHtml.size) {
                    dayCountsEntity.add(
                        DayStatisticEntity(
                            date = titlesHtml.toStringValue(j - 1),
                            count = countryHtml.toLongValue(j)
                        )
                    )
                }

                counties.add(
                    StatisticEntity(
                        country = countyStatisticEntity,
                        coord = coordEntity,
                        dayStatistic = dayCountsEntity
                    )
                )
            }
        } catch (e: Exception) {
            // TODO: create exception
            throw Exception("Parser Error: ${e.message}")
        }

        return counties
    }

    private fun Elements.toStringValue(index: Int): String =
        if (this[index].childNodes().isEmpty()) ""
        else this[index].childNodes().first().toString()

    private fun Elements.toDoubleValue(index: Int): Double =
        if (this[index].childNodes().isEmpty()) 0.0
        else this[index].childNodes().first().toString().toDouble()

    private fun Elements.toLongValue(index: Int): Long =
        if (this[index].childNodes().isEmpty()) 0L
        else this[index].childNodes().first().toString().toLong()

    private companion object {

        const val BASE_URL =
            "https://github.com/CSSEGISandData/COVID-19/blob/master/csse_covid_19_data/csse_covid_19_time_series/"

        const val CONFIRMED_FILE_NAME = "time_series_19-covid-Confirmed.csv"

        const val DEATHS_FILE_NAME = "time_series_19-covid-Deaths.csv"

        const val RECOVERED_FILE_NAME = "time_series_19-covid-Recovered.csv"

        const val LINE_IDENTIFICATION = "L"

        const val LINE_SEPARATOR = "th"

        const val ROW_SEPARATOR = "td"

        const val COUNT_COUNTRIES = 488
    }
}