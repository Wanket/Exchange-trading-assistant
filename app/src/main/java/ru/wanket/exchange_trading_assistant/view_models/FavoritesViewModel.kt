package ru.wanket.exchange_trading_assistant.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.repository.FavoritesRepository
import ru.wanket.exchange_trading_assistant.Navigator
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val navigator: Navigator
) : ViewModel() {
    val favorites = favoritesRepository.getFavoriteRates()

    fun onEditClicked(rateBaseInfo: RateBaseInfo) = navigator.navigateFavoriteSettings(rateBaseInfo)

    fun onDeleteClicked(rateBaseInfo: RateBaseInfo) = viewModelScope.launch {
        favoritesRepository.deleteFavorite(rateBaseInfo)
    }

    fun onRateClicked(rateBaseInfo: RateBaseInfo) = navigator.navigateRateInfo(rateBaseInfo)
}
