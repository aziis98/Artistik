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

data class Ref<T>(var value: T)