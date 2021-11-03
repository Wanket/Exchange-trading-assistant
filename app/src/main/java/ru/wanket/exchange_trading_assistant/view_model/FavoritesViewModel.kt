package ru.wanket.exchange_trading_assistant.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import ru.wanket.exchange_trading_assistant.navigator.ViewModelNavigator
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val viewModelNavigator: ViewModelNavigator
) : ViewModel() {
    val favorites = favoritesRepository.getFavoriteRates()

    fun onEditClicked(rateBaseInfo: RateBaseInfo) = viewModelNavigator.navigateFavoriteSettings(rateBaseInfo)

    fun onDeleteClicked(rateBaseInfo: RateBaseInfo) = viewModelScope.launch {
        favoritesRepository.deleteFavorite(rateBaseInfo)
    }

    fun onRateClicked(rateBaseInfo: RateBaseInfo) = viewModelNavigator.navigateRateInfo(rateBaseInfo)
}
