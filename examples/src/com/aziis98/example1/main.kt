package com.aziis98.example1

import com.aziis98.artistik.*
import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineJoin
import javafx.stage.Stage
import java.util.*
import kotlin.concurrent.timer

/*
 * Created by aziis98 on 02/08/2017.
 */

fun computeState(n: Int) = Random(0).let { r ->
    listOfSize(n).map {
        Point2D(0.5, 0.5) + r.randomUnitVector() * r.nextGaussian() * 0.1
    }
}

object Project1 : ArtistikRenderer<List<Point2D>> {

    override fun render(g: GraphicsContext, state: List<Point2D>) {

        g.normalized {

            g.clearAll()

            g.fill = Color.web("#000")
            g.fillRect(0.0, 0.0, 1.0, 1.0)

            g.stroke = Color.web("#fff")
            g.lineJoin = StrokeLineJoin.ROUND

            g.stroke {

                val (head, tail) = state.headTail()

                g.moveTo(head.x, head.y)

                tail.forEach {
                    g.lineTo(it.x, it.y)
                }

            }

        }

    }

}

class ArtistikTestApplication : Application() {
    override fun start(primaryStage: Stage) {

        val stateRef = Ref(computeState(2))

        val artistikWindow = constructArtistikFx(Project1, stateRef, primaryStage,
                                                 "Project1", DIMENSION_720s)

        timer(daemon = true, initialDelay = 70L, period = 1000L / 15L) {
            stateRef.value = computeState((artistikWindow.time / 30).toInt())
        }

    }
}

fun main(args: Array<String>) {
    Application.launch(ArtistikTestApplication::class.java, *args)
}