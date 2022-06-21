package com.myquotes

import android.graphics.Color
import java.lang.Math.random
import java.util.*
import kotlin.math.roundToInt

class Utils {
    companion object{
        fun randomLightColor(): Int {
            val base = 255

            val rnd = Random()
            val red: Int = base - rnd.nextInt(256) / 2
            val green: Int = base - rnd.nextInt(256) / 2
            val blue: Int = base - rnd.nextInt(256) / 2

            return Color.rgb(red, green, blue)
        }
    }
}