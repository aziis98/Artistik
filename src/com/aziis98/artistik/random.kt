package com.aziis98.artistik

import javafx.geometry.Point2D
import java.util.*

/*
 * Created by aziis98 on 02/08/2017.
 */

/**
 * Instance random object
 */
val RANDOM = Random()

fun ClosedRange<Int>.random() = RANDOM.randomInt(start, endInclusive)

fun ClosedRange<Double>.random() = RANDOM.randomDouble(start, endInclusive)

fun Random.randomInt(min: Int, max: Int) = nextInt(max - min + 1) + min

fun Random.randomDouble(min: Double, max: Double): Double {
    return if (nextBoolean()) {
        (1.0 - nextDouble()) * (max - min) + min
    }
    else {
        nextDouble() * (max - min) + min
    }
}

fun <T> Random.randomIn(list: List<T>) = list[randomInt(0, list.lastIndex)]

fun Random.randomDouble() = randomDouble(0.0, 1.0)

fun Random.randomUnitVector() = randomDouble(0.0, 2 * Math.PI).let { theta -> Point2D(Math.cos(theta), Math.sin(theta)) }