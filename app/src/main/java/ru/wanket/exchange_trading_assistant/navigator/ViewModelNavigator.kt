package ru.wanket.exchange_trading_assistant.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.ui.favorite_settings.FavoriteSettingsActivity
import ru.wanket.exchange_trading_assistant.ui.favorites.FavoritesActivity
import ru.wanket.exchange_trading_assistant.ui.rate_info.RateInfoActivity
import ru.wanket.exchange_trading_assistant.ui.search.SearchActivity
import javax.inject.Inject

@ViewModelScoped
class ViewModelNavigator @Inject constructor(@ApplicationContext private val context: Context) : BaseNavigator() {
    fun navigateFavorites() = startActivity<FavoritesActivity>(context)

    fun navigateSearch() = startActivity<SearchActivity>(context)

    fun navigateRateInfo(rateBaseInfo: RateBaseInfo) =
        navigateRateBaseInfoTemplate<RateInfoActivity>(context, rateBaseInfo)

    fun navigateFavoriteSettings(rateBaseInfo: RateBaseInfo) =
        navigateRateBaseInfoTemplate<FavoriteSettingsActivity>(context, rateBaseInfo)

    private inline fun <reified T : RateBaseInfoActivity> navigateRateBaseInfoTemplate(context: Context, rateBaseInfo: RateBaseInfo) =
        startActivity<T>(context) { putRate(rateBaseInfo) }

    private inline fun <reified T : Activity> startActivity(context: Context, crossinline intentSetup: Intent.() -> Unit = {}) =
        context.startActivity(
            Intent(context, T::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK

                intentSetup()
            }
        )
}
