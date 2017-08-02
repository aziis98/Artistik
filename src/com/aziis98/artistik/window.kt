package com.aziis98.artistik

import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.awt.Toolkit

/*
 * Created by aziis98 on 02/08/2017.
 */

interface ArtistikModel<State> {
    fun initialize(globals: ArtistikGlobals): State
    fun step(globals: ArtistikGlobals, previousState: State): State
}

interface ArtistikProject<in State> {
    fun render(state: State, g: GraphicsContext)
}

data class ArtistikGlobals(
    val dimensions: Dimensions<Double>,
    val renderTicks: Long,
    val passedMillis: Long
)

data class Artistik<S>(
    val model: ArtistikModel<S>,
    val project: ArtistikProject<S>,

    val canvas: Canvas,
    val animationTimer: AnimationTimer,
    val stateRef: Ref<S>
)

fun <S> constructArtistikFx(
    artistikProject: ArtistikProject<S>,
    artistikModel: ArtistikModel<S>,
    stage: Stage,
    title: String = "<Untitled>",
    initialWidth: Double = 600.0, initialHeight: Double = 500.0
): Artistik<S> {

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

    var ticks = 0L
    val stateRef = Ref(artistikModel.initialize(
            ArtistikGlobals(
                    Dimensions(canvas.width, canvas.height),
                    ticks,
                    0L
            ))
    )


    val at = object : AnimationTimer() {
        override fun handle(now: Long) {

            val globals = ArtistikGlobals(
                    Dimensions(canvas.width, canvas.height),
                    ticks,
                    (now / 1e6).toLong()
            )

            stateRef.value = artistikModel.step(globals, stateRef.value)

            artistikProject.render(
                    stateRef.value,
                    canvas.graphicsContext2D)

            ticks++

        }
    }

    canvas.setOnMousePressed {
        memorydump()
    }

    stage.show()
    at.start()

    return Artistik(artistikModel, artistikProject, canvas, at, stateRef)
}
