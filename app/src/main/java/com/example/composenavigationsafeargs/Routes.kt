package com.example.composenavigationsafeargs

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


/* 傳變數前要先把Routes包起來 */
sealed class Routes {
    @Serializable
    data object FirstScreen : Routes()

    @Serializable
    data class SecondScreen(val myCustomModel: MyCustomModel) : Routes()
}

/*
* 客製化模型
* 我們必須加上 @Parcelize
* 然後，Compose Destination 需要知道如何序列化和反序列化此自訂方法。它帶有一個特殊的 NavType 抽象類
*  */
@Parcelize
@Serializable
data class MyCustomModel(
    val name: String?,
    val id: Int,
) : Parcelable

/*
* 繼承NavType
* 客製化模型 我們需要透過以下方式繼承它 讓他可以將它送給其他Screen之前轉成“NavType”
* */
class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            bundle.getParcelable(key)
        }

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putParcelable(key, value)

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    override val name: String = clazz.name
}
