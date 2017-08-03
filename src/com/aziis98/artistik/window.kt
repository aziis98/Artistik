package com.aziis98.artistik

import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.awt.Toolkit

/*
 * Created by aziis98 on 02/08/2017.
 */

data class ArtistikWindow(
    var ticks: Long,
    var time: Long,
    var dimensions: Dimensions<Double>
)

fun <S> constructArtistikFx(
    renderer: ArtistikRenderer<S>,
    stateRef: Ref<S>,
    stage: Stage,
    title: String = "<Untitled>",
    initialWidth: Double = 600.0, initialHeight: Double = 500.0
): ArtistikWindow {

    val windowScalingFactor = Toolkit.getDefaultToolkit().screenResolution * 0.01
    val canvas = Canvas()

    stage.scene = Scene(
            Pane(canvas),
            initialWidth * windowScalingFactor,
            initialHeight * windowScalingFactor
    )

    stage.minWidth = 400.0
    stage.minHeight = 400.0

    stage.title = title

    canvas.widthProperty().bind(stage.scene.widthProperty())
    canvas.heightProperty().bind(stage.scene.heightProperty())

    val artistikWindow = ArtistikWindow(0, 0, canvas.graphicsContext2D.dimensions)

    val startTime = Time.milliTime()

    val at = object : AnimationTimer() {
        override fun handle(now: Long) {

            val g = canvas.graphicsContext2D

            g.block {
                renderer.render(g, stateRef.value)
            }

            artistikWindow.time = Time.milliTime() - startTime
            artistikWindow.ticks++

        }
    }

//    canvas.setOnMousePressed {
//        memorydump()
//    }

    stage.show()
    at.start()

    return artistikWindow
}
