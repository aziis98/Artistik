package com.aziis98.artistik

import javafx.scene.canvas.GraphicsContext

/*
 * Created by aziis98 on 02/08/2017.
 */

val GraphicsContext.dimensions: Dimensions<Double>
    get() = Dimensions(canvas.width, canvas.height)

inline fun GraphicsContext.block(block: () -> Unit) {
    save()
    block()
    restore()
}

inline fun GraphicsContext.normalized(block: () -> Unit) {
    block {
        val (width, height) = dimensions

        scale(width, height)
        lineWidth = 2.0 / (width + height)

        block()
    }
}

inline fun GraphicsContext.semiNormalized(block: (ratio: Double) -> Unit) {
    block {
        val (width, height) = dimensions
        val side = width

        scale(side, side)
        lineWidth = 1.0 / side

        block(width / height)
    }
}

inline fun GraphicsContext.stroke(block: () -> Unit) {
    beginPath()
    block()
    stroke()
}

fun GraphicsContext.clearAll() {
    clearRect(0.0, 0.0, canvas.width, canvas.height)
}

fun GraphicsContext.strokeCircle(x: Double, y: Double, radius: Double) {
    strokeOval(x - radius, y - radius, radius * 2, radius * 2)
}

fun GraphicsContext.fillCircle(x: Double, y: Double, radius: Double) {
    fillOval(x - radius, y - radius, radius * 2, radius * 2)
}

fun GraphicsContext.strokeCenteredRectangle(x: Double, y: Double, width: Double, height: Double) {
    strokeRect(x - width / 2, y - height / 2, width, height)
}

fun GraphicsContext.fillCenteredRectangle(x: Double, y: Double, width: Double, height: Double) {
    fillRect(x - width / 2, y - height / 2, width, height)
}

