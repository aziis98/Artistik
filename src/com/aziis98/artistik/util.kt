package com.aziis98.artistik

import javafx.geometry.Point2D
import kotlin.test.currentStackTrace

/*
 * Created by aziis98 on 02/08/2017.
 */

object Time {

    fun milliTime() = System.nanoTime() / 1000000

    fun sleep(millis: Long) = Thread.sleep(millis)

}

fun stacktrace(offset: Int) = currentStackTrace()[offset + 1].toString()

fun log(message: Any, sender: String = "Logger", offset: Int = 0) {
    println("[$sender] [${stacktrace(offset + 2)}]\n$message\n")
}

fun memorydump() {
    println()
    println("-----< Memory Log >-----")

    val runtime = Runtime.getRuntime()

    val freeMemory = runtime.freeMemory()
    val allocatedMemory = runtime.totalMemory()
    val maxMemory = runtime.maxMemory()

    println(" Used: ${ (allocatedMemory - freeMemory) * 100 / allocatedMemory }%")
    println(" Free Memory: ${freeMemory / 1024}")
    println(" Allocated Memory: ${allocatedMemory / 1024}")
    println(" Max Memory: ${ maxMemory / 1024 }")

    println("------------------------")
}

// -----------< Utility Methods >----------- //

// Lists

/**
 * Returns a pair containing the first n elements in the left and the remaining in the right
 */
fun <T> List<T>.divide(index: Int) : Pair<List<T>, List<T>> = Pair(take(index), drop(index))

/**
 * Extension method for Haskell lovers, returns Pair(first(), drop(1))
 */
fun <T> List<T>.headTail() = Pair(first(), drop(1))

/**
 * Extension method for Haskell lovers, returns Pair(dropLast(1), last())
 */
fun <T> List<T>.initialLast() = Pair(dropLast(1), last())

fun listOfSize(size: Int) = (1 .. size).map { Unit }


// Dimensions

operator fun Dimensions<Int>.times(scalar: Int) = Dimensions(width * scalar, height * scalar)

operator fun Dimensions<Int>.times(scalar: Double) = Dimensions((width * scalar).toInt(), (height * scalar).toInt())

// Point2D

operator fun Point2D.plus(other: Point2D): Point2D = add(other)

operator fun Point2D.minus(other: Point2D): Point2D = subtract(other)

operator fun Point2D.times(scalar: Double): Point2D = this.multiply(scalar)

operator fun Point2D.div(scalar: Double): Point2D = this.multiply(1.0 / scalar)

operator fun Point2D.times(other: Point2D): Double = this.dotProduct(other)






