package com.aziis98.artistik

import javafx.scene.canvas.GraphicsContext

/*
 * Created by aziis98 on 02/08/2017.
 */

val GraphicsContext.dimensions: Dimensions<Double>
    get() = Dimensions(canvas.width, canvas.height)

inline fun GraphicsContext.block(safe: () -> Unit) {
    save()
    safe()
    restore()
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

