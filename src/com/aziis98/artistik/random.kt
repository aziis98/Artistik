package com.aziis98.artistik

import java.util.*

/*
 * Created by aziis98 on 02/08/2017.
 */

object Randoms {

    private val random = Random(0L)

    var seed = 0L
        get() = field
        set(value) {
            field = value
            random.setSeed(value)
        }

    fun randomInt(min: Int, max: Int) = random.nextInt(max - min + 1) + min

    fun randomDouble(min: Double, max: Double) =
        if (random.nextBoolean()) {
            (1.0 - random.nextDouble()) * (max - min) + min
        }
        else {
            random.nextDouble() * (max - min) + min
        }

    fun randomDouble(): Double = randomDouble(0.0, 1.0)

    fun randomGaussian(): Double = random.nextGaussian()

    fun <T> randomIn(list: List<T>) = list[randomInt(0, list.lastIndex)]

}

fun ClosedRange<Int>.random() = Randoms.randomInt(start, endInclusive)

fun ClosedRange<Double>.random() = Randoms.randomDouble(start, endInclusive)
