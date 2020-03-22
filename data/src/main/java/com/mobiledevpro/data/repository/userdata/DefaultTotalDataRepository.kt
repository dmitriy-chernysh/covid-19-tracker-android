package com.mobiledevpro.data.repository.userdata

import com.mobiledevpro.data.mapper.throwableToDomain
import com.mobiledevpro.data.mapper.toCacheEntity
import com.mobiledevpro.data.mapper.toDomain
import com.mobiledevpro.data.mapper.toEntity
import com.mobiledevpro.data.model.CountryEntity
import com.mobiledevpro.data.model.TotalEntity
import com.mobiledevpro.data.model.TotalValueEntity
import com.mobiledevpro.domain.model.Country
import com.mobiledevpro.domain.model.Total
import com.mobiledevpro.domain.totaldata.TotalDataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function3

/**
 * Repository for Total data screen
 *
 *
 * Created by Dmitriy Chernysh
 *
 * http://androiddev.pro
 *
 * #MobileDevPro
 */
class DefaultTotalDataRepository(
    private val covidCache: CovidCache,
    private val covidRemote: CovidRemote
) : TotalDataRepository {

    override fun getLocalTotalDataObservable(): Observable<Total> = covidCache
        .getTotalDataObservable()
        .map(TotalEntity::toDomain)
        .throwableToDomain()

    override fun setLocalTotalData(total: Total): Completable = Single
        .just(total)
        .map(Total::toCacheEntity)
        .flatMapCompletable(covidCache::updateTotalData)
        .throwableToDomain()

    override fun getTotalData(): Single<Total> = Single
        .zip<TotalValueEntity, TotalValueEntity, TotalValueEntity, Total>(
            covidRemote.getTotalConfirmed(),
            covidRemote.getTotalDeaths(),
            covidRemote.getTotalRecovered(),
            Function3 { countConfirmed, countDeaths, countRecovered ->
                Total(
                    confirmed = countConfirmed.count,
                    deaths = countDeaths.count,
                    recovered = countRecovered.count
                )
            })
        .throwableToDomain()


    override fun getLocalCountriesObservable(query: String): Observable<List<Country>> = covidCache
        .getLocalCountriesObservable(query)
        .map { it.map(CountryEntity::toDomain) }
        .throwableToDomain()

    override fun getCountries(): Single<List<Country>> = covidRemote
        .getCountries()
        .map { it.map(CountryEntity::toDomain) }
        .throwableToDomain()

    override fun setLocalCountriesData(countries: List<Country>): Completable = Single
        .just(countries)
        .map { it.map(Country::toEntity) }
        .flatMapCompletable(covidCache::updateCountries)
        .throwableToDomain()
}

