package com.example.composenavigationsafeargs

import kotlinx.serialization.Serializable

/* 傳變數前要先把Routes包起來 */
sealed class Routes {
    @Serializable
    data object FirstScreen : Routes()

    @Serializable
    data class SecondScreen(val name: String?, val age:Int) : Routes()
}