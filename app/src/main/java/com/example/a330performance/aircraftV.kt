package com.example.a330performance

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class AircraftV
{
    fun round5down(x: Double): Int {
        return (kotlin.math.floor(x / 5) * 5).toInt()
    }

    fun round10down(x: Double): Int {
        return (kotlin.math.floor(x / 10) * 10).toInt()
    }

    fun f2corr(f: Int, a: Int): Int {
        return if (f == 2) (kotlin.math.abs(a * 2e-4)).toInt() else 0
    }


    /**
     * Calculates the trim value based on the given parameters and speed.
     *
     * @param keyInicial The initial key value
     * @param keyMaximo The maximum key value
     * @param valorInicial The initial value
     * @param valorMaximo The maximum value
     * @param punto The point value
     * @param speed The speed value (default is 1)
     * @return The calculated trim value rounded to one decimal place
     */
    fun calculateTrim(
        keyInicial: Double,
        keyMaximo: Double,
        valorInicial: Double,
        valorMaximo: Double,
        punto: Double,
        speed: Double = 1.0
    ): String {
        val velocidadNormalizada = max(0.0, min(2.0, speed))

        val proporcion = if (velocidadNormalizada == 0.0) {
            0.0
        } else {
            (punto - keyInicial) / (keyMaximo - keyInicial).pow(
                (1 / speed.pow(speed)) / velocidadNormalizada
            )
        }

        val valorCalculado = valorInicial + proporcion * (valorMaximo - valorInicial)
        val valorFinal = "%.1f".format(valorCalculado).toDouble()

        return if (valorFinal >= 0) {
            "UP${valorFinal.toString().replace("+", "")}"
        } else {
            "DN${valorFinal.toString().replace("-", "")}"
        }
    }
}