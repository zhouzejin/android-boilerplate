package uk.co.ribot.androidboilerplate.utils

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import retrofit2.HttpException

/**
 * 没有网络
 */
const val NETWORKTYPE_INVALID = 0
/**
 * wap网络
 */
const val NETWORKTYPE_WAP = 1
/**
 * 2G网络
 */
const val NETWORKTYPE_2G = 2
/**
 * 3G和3G以上网络，或统称为快速网络
 */
const val NETWORKTYPE_3G = 3
/**
 * wifi网络
 */
const val NETWORKTYPE_WIFI = 4

/**
 * Returns true if the Throwable is an instance of RetrofitError with an
 * http status code equals to the given one.
 */
fun isHttpStatusCode(throwable: Throwable, statusCode: Int): Boolean {
    return throwable is HttpException && throwable.code() == statusCode
}

fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

/**
 * get the network type (wifi,wap,2g,3g)
 *
 * @param context
 * @return
 */
@Suppress("DEPRECATION")
fun getNetWorkType(context: Context): Int {

    var mNetWorkType = NETWORKTYPE_INVALID
    val manager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetworkInfo
    if (networkInfo?.isConnected == true) {
        val type = networkInfo.typeName
        if (type.equals("WIFI", ignoreCase = true)) {
            mNetWorkType = NETWORKTYPE_WIFI
        } else if (type.equals("MOBILE", ignoreCase = true)) {
            val proxyHost = android.net.Proxy.getDefaultHost()
            mNetWorkType = if (TextUtils.isEmpty(proxyHost))
                if (isFastMobileNetwork(context)) NETWORKTYPE_3G else NETWORKTYPE_2G
            else
                NETWORKTYPE_WAP
        }
    } else {
        mNetWorkType = NETWORKTYPE_INVALID
    }
    return mNetWorkType
}

private fun isFastMobileNetwork(context: Context): Boolean {
    val telephonyManager = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    when (telephonyManager.networkType) {
        TelephonyManager.NETWORK_TYPE_1xRTT -> return false // ~ 50-100 kbps
        TelephonyManager.NETWORK_TYPE_CDMA -> return false // ~ 14-64 kbps
        TelephonyManager.NETWORK_TYPE_EDGE -> return false // ~ 50-100 kbps
        TelephonyManager.NETWORK_TYPE_EVDO_0 -> return true // ~ 400-1000 kbps
        TelephonyManager.NETWORK_TYPE_EVDO_A -> return true // ~ 600-1400 kbps
        TelephonyManager.NETWORK_TYPE_GPRS -> return false // ~ 100 kbps
        TelephonyManager.NETWORK_TYPE_HSDPA -> return true // ~ 2-14 Mbps
        TelephonyManager.NETWORK_TYPE_HSPA -> return true // ~ 700-1700 kbps
        TelephonyManager.NETWORK_TYPE_HSUPA -> return true // ~ 1-23 Mbps
        TelephonyManager.NETWORK_TYPE_UMTS -> return true // ~ 400-7000 kbps
        TelephonyManager.NETWORK_TYPE_EHRPD -> return true // ~ 1-2 Mbps
        TelephonyManager.NETWORK_TYPE_EVDO_B -> return true // ~ 5 Mbps
        TelephonyManager.NETWORK_TYPE_HSPAP -> return true // ~ 10-20 Mbps
        TelephonyManager.NETWORK_TYPE_IDEN -> return false // ~25 kbps
        TelephonyManager.NETWORK_TYPE_LTE -> return true // ~ 10+ Mbps
        TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
        else -> return false
    }
}
