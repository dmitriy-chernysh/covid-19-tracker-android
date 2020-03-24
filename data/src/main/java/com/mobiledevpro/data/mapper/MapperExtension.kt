package com.mobiledevpro.data.mapper

import com.mobiledevpro.data.model.AccessDeniedThrowableEntity
import com.mobiledevpro.data.model.CountryEntity
import com.mobiledevpro.data.model.NetworkThrowableEntity
import com.mobiledevpro.data.model.NotFoundThrowableEntity
import com.mobiledevpro.data.model.ServiceUnavailableThrowableEntity
import com.mobiledevpro.data.model.CountryTotalEntity
import com.mobiledevpro.data.model.TotalEntity
import com.mobiledevpro.data.model.UnknownThrowableEntity
import com.mobiledevpro.domain.error.AccessDeniedThrowable
import com.mobiledevpro.domain.error.NetworkThrowable
import com.mobiledevpro.domain.error.NotFoundThrowable
import com.mobiledevpro.domain.error.ServiceUnavailableThrowable
import com.mobiledevpro.domain.error.UnknownThrowable
import com.mobiledevpro.data.model.statistic.CountryStatisticEntity
import com.mobiledevpro.domain.model.Country
import com.mobiledevpro.domain.model.Total
import java.util.*

/**
 * Extensions for mapping data level models to domain level models and vise versa
 *
 * Created by Dmitriy Chernysh
 *
 * http://androiddev.pro
 *
 */

fun Total.toCacheEntity() = TotalEntity(
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    lastUpdateTime = if (updateTime <= 0) Date().time else updateTime
)

fun TotalEntity.toDomain() = Total(
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    updateTime = lastUpdateTime
)

fun CountryTotalEntity.toDomain() = Country(
    id = id,
    country = country,
    updated = updated,
    latitude = latitude,
    longitude = longitude,
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    active = active
)

fun Country.toEntity() = CountryTotalEntity(
    id = id,
    country = country,
    updated = updated,
    latitude = latitude,
    longitude = longitude,
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    active = active
)

fun CountryStatisticEntity.toDomain() = Country(
    id = id,
    country = country,
    updated = updated,
    confirmed = confirmed.toInt(),
    deaths = deaths.toInt()
)
fun Throwable.throwableToDomain() = when (this) {
    is NetworkThrowableEntity -> NetworkThrowable()
    is NotFoundThrowableEntity -> NotFoundThrowable()
    is AccessDeniedThrowableEntity -> AccessDeniedThrowable()
    is ServiceUnavailableThrowableEntity -> ServiceUnavailableThrowable()
    is UnknownThrowableEntity -> UnknownThrowable()
    else -> UnknownThrowable()
}