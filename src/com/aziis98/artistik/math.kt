package com.aziis98.artistik

import javafx.geometry.Point2D

/*
 * Created by aziis98 on 03/08/2017.
 */

data class Complex(val real: Double, val immaginary: Double) {

    operator fun plus(other: Complex) = Complex(real + other.real, immaginary + other.immaginary)

    /**
     * (a + bi)(c + di) = ac - bd + (ad + bc)i
     */
    operator fun times(other: Complex) = Complex(real * other.real - immaginary * other.immaginary, real * other.immaginary + immaginary * other.real)

    override fun toString() = "($real + $immaginary i)"

    fun norm() = real * real - immaginary * immaginary

    fun conjugate() = Complex(real, -immaginary)

}

fun Double.plus(other: Complex) = Complex(other.real + this, other.immaginary)

fun Double.times(other: Complex) = Complex(other.real * this, other.immaginary * this)

val Double.i : Complex
    get() = Complex(0.0, this)

fun Point2D.toComplex() = Complex(x, y)

operator fun Point2D.component1() = x

operator fun Point2D.component2() = y