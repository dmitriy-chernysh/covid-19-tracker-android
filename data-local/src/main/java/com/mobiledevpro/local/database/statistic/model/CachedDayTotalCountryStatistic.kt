package com.mobiledevpro.local.database.statistic.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Data class for collect day statistic by country
 * @property country is parent name of country
 * @property date is a date of statistic
 * @property count is a count people of statistic
 */
@Entity(
    tableName = "day_total_country_statistic",
    indices = [Index("date")],
    foreignKeys = [(ForeignKey(
        entity = CachedStatisticCountry::class,
        parentColumns = ["country", "province"],
        childColumns = ["date"],
        onDelete = ForeignKey.CASCADE
    ))],
    primaryKeys = ["date", "country", "province"]
)
data class CachedDayTotalCountryStatistic(
    val province: String,
    val country: String,
    val date: String,
    val count: Long
)