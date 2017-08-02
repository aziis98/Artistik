package com.aziis98.example1

import com.aziis98.artistik.*
import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.awt.Point

/*
 * Created by aziis98 on 02/08/2017.
 */

object ArtistikProject1 : ArtistikProject<ArtistikProject1.State>, ArtistikModel<ArtistikProject1.State> {

    data class State(
        val list: List<Point2D>
    )

    override fun initialize(globals: ArtistikGlobals) = State(
            listOf(Point2D(
                    (0.0 .. globals.dimensions.width).random(), (0.0 .. globals.dimensions.height).random()
            ))
    )

    override fun step(globals: ArtistikGlobals, previousState: State): State {
        return State(listOf(

                previousState.list,

                listOf(Point2D(
                        (0.0 .. globals.dimensions.width).random(), (0.0 .. globals.dimensions.height).random()
                ))

        ).flatten())
    }

    override fun render(state: State, g: GraphicsContext) {
        g.clearAll()
        g.stroke = Color.BLACK

        g.stroke {

            val first = state.list.first()
            g.moveTo(first.x, first.y)

            state.list.forEach {
                g.lineTo(it.x, it.y)
            }

        }
    }

}

class ArtistikTestApplication : Application() {
    override fun start(primaryStage: Stage) {
        val (_, _, canvas, _, stateRef) = constructArtistikFx(ArtistikProject1, ArtistikProject1, primaryStage)

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED) { _ ->
            println(stateRef.value.list.size)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(ArtistikTestApplication::class.java, *args)
}