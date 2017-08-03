package com.aziis98.artistik

/*
 * Created by aziis98 on 02/08/2017.
 */

// -----------< Abstractions >----------- //

interface IDimensioned<out T> {
    val width: T
    val height: T
}

interface ILocated<out T> {
    val x: T
    val y: T
}

interface ReadRef<out T> {
    val value: T
}

// -----------< Concrete Classes >----------- //

data class Rectangle<out T>(
    override val x: T,
    override val y: T,
    override val width: T,
    override val height: T
) : ILocated<T>, IDimensioned<T>

data class Dimensions<out T>(
    override val width: T,
    override val height: T = width
) : IDimensioned<T>

data class Ref<T>(override var value: T) : ReadRef<T> {
    val readOnly : ReadRef<T> = this
}

// -----------< Concrete Classes >----------- //

operator fun Dimensions<Int>.times(scalar: Int) = Dimensions(width * scalar, height * scalar)

operator fun Dimensions<Int>.times(scalar: Double) = Dimensions(width * scalar, height * scalar)

inline fun <T, R> Dimensions<T>.map(transform: (T) -> R) = Dimensions(transform(width), transform(height))