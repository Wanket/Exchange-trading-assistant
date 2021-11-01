package ru.wanket.exchange_trading_assistant

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ru.wanket.exchange_trading_assistant.entity.data.RateBaseInfo
import ru.wanket.exchange_trading_assistant.entity.data.RateType
import ru.wanket.exchange_trading_assistant.ui.favorite_settings.FavoriteSettingsActivity
import ru.wanket.exchange_trading_assistant.ui.favorites.FavoritesActivity
import ru.wanket.exchange_trading_assistant.ui.rate_info.RateInfoActivity
import ru.wanket.exchange_trading_assistant.ui.search.SearchActivity
import javax.inject.Inject

abstract class RateBaseInfoActivity : ComponentActivity() {
    protected fun Intent.getRate() = RateBaseInfo(
        getStringExtra(IntentConstants.RATE_ID)!!,
        RateType.values()[getIntExtra(IntentConstants.RATE_TYPE, -1)]
    )
}

private object IntentConstants {
    const val RATE_ID = "rate_id"
    const val RATE_TYPE = "rate_type"
}

@ViewModelScoped
class Navigator @Inject constructor(@ApplicationContext private val context: Context) {
    fun navigateFavorites() = startActivity<FavoritesActivity>()

    fun navigateSearch() = startActivity<SearchActivity>()

    fun navigateRateInfo(rateBaseInfo: RateBaseInfo) =
        navigateRateBaseInfoTemplate<RateInfoActivity>(rateBaseInfo)

    fun navigateFavoriteSettings(rateBaseInfo: RateBaseInfo) =
        navigateRateBaseInfoTemplate<FavoriteSettingsActivity>(rateBaseInfo)

    private fun Intent.putRate(rateBaseInfo: RateBaseInfo) = rateBaseInfo.apply {
        putExtra(IntentConstants.RATE_ID, codeName)
        putExtra(IntentConstants.RATE_TYPE, type.ordinal)
    }

    private inline fun <reified T : RateBaseInfoActivity> navigateRateBaseInfoTemplate(rateBaseInfo: RateBaseInfo) =
        startActivity<T> { putRate(rateBaseInfo) }

    private inline fun <reified T : Activity> startActivity(crossinline intentSetup: Intent.() -> Unit = {}) =
        context.startActivity(
            Intent(context, T::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK

                intentSetup()
            }
        )
}

