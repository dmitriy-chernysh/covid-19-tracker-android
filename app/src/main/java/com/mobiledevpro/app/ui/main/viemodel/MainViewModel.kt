package com.mobiledevpro.app.ui.main.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.analytics.FirebaseAnalytics
import com.mobiledevpro.app.common.BaseViewModel
import com.mobiledevpro.app.common.Event
import com.mobiledevpro.app.utils.FabActionNavigation
import com.mobiledevpro.app.utils.Navigation
import com.mobiledevpro.app.utils.logEventCountrySelected
import com.mobiledevpro.app.utils.logEventShareAppLink


/**
 * Main view model for Main Activity
 *
 * Created by Dmitriy Chernysh on Mar 23, 2020.
 *
 * http://androiddev.pro
 *
 */
class MainViewModel(
    private val analytics: FirebaseAnalytics
) : BaseViewModel() {

    private val _eventNavigateTo = MutableLiveData<Event<Navigation>>()
    val eventNavigateTo: LiveData<Event<Navigation>> = _eventNavigateTo

    private val _eventNavigateToCountryDetails = MutableLiveData<Event<String>>()
    val eventNavigateToCountryDetails: LiveData<Event<String>> = _eventNavigateToCountryDetails

    private val _eventFabAction = MutableLiveData<Event<FabActionNavigation>>()
    val eventFabAction: LiveData<Event<FabActionNavigation>> = _eventFabAction

    fun showCountriesList() {
        _eventNavigateTo.value =
            Event(Navigation.NAVIGATE_TO_COUNTRIES_LIST)
    }

    fun showCountryDetails(countryName: String) {
        _eventNavigateToCountryDetails.value =
            Event(countryName)

        analytics.logEventCountrySelected(countryName)
    }

    fun showSearchCountryBar() {
        _eventNavigateTo.value =
            Event(Navigation.NAVIGATE_TO_SEARCH_COUNTRY)
    }

    fun closeSearchCountryBar() {
        _eventNavigateTo.value =
            Event(Navigation.NAVIGATE_CLOSE_SEARCH_COUNTRY)
    }

    fun setFabActionShowCountries() {
        _eventFabAction.value =
            Event(FabActionNavigation.ACTION_SHOW_COUNTRIES)
    }

    fun setFabActionShowCountrySearch() {
        _eventFabAction.value =
            Event(FabActionNavigation.ACTION_SHOW_COUNTRY_SEARCH_BAR)
    }

    fun setFabActionCloseCountrySearch() {
        _eventFabAction.value =
            Event(FabActionNavigation.ACTION_CLOSE_COUNTRY_SEARCH_BAR)
    }

    fun setFabHide() {
        _eventFabAction.value =
            Event(FabActionNavigation.ACTION_HIDE)
    }

    fun shareTheAppLink(): Boolean {
        _eventNavigateTo.value =
            Event(Navigation.NAVIGATE_TO_SHARE_THE_APP)

        analytics.logEventShareAppLink()
        return true
    }
}