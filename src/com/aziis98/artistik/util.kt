package com.aziis98.artistik

import javafx.scene.canvas.GraphicsContext
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.currentStackTrace

/*
 * Created by aziis98 on 02/08/2017.
 */

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

fun mutableListOfSize(size: Int) = (1 .. size).map { Unit }.toMutableList()

object Time {

    fun milliTime() = System.nanoTime() / 1000000

}







