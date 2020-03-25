package com.mobiledevpro.local.database.statistic.model

import androidx.room.Entity
import androidx.room.Index

/***
 * Data class for collect countries per static
 * @property province is a name of province or state
 * @property country is a name of country
 * @property latitude is a latitude by coordinate
 * @property longitude is a longitude by coordinate
 */
@Entity(
    tableName = "countries_statistic",
    primaryKeys = ["province"],
    indices = [Index(value = ["province"], unique = false)]
)
data class CachedStatisticCountry(
    val country: String,
    val province: String,
    val latitude: Double,
    val longitude: Double
)