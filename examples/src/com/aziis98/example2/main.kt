package com.aziis98.example2

import com.aziis98.artistik.*
import com.aziis98.artistik.helpers.WritableImageComputation
import com.aziis98.artistik.helpers.run
import javafx.application.Application
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import javafx.scene.transform.Affine
import javafx.stage.Stage
import kotlin.system.measureNanoTime

/*
 * Created by aziis98 on 03/08/2017.
 */

data class PixelGrid(
    val size: Int = 1000,
    var centerX: Double = 0.0,
    var centerY: Double = 0.0,
    var scale: Double = 1.0,
    val image: WritableImage = WritableImage(size, size)
)

val renderer = object : ArtistikRenderer<PixelGrid> {
    override fun render(g: GraphicsContext, state: PixelGrid) {
        g.clearAll()

        g.normalized {
            g.drawImage(state.image, 0.0, 0.0, 1.0, 1.0)
        }
    }
}

class MandelbrotComputation(image: WritableImage, val centerX: Double = 0.0, val centerY: Double = 0.0, val scale: Double = 1.0) : WritableImageComputation(image) {

    @Suppress("NAME_SHADOWING")
    override fun computePixel(x: Int, y: Int): Color {

        val (x0, y0) = (Point2D(x.toDouble(), y.toDouble()) / width.toDouble() - Point2D(0.5, 0.5)) / scale + Point2D(centerX, centerY)

        var x = 0.0
        var y = 0.0

        var iters = 0

        while (x * x + y * y < 4.0 && iters < 1000) {
            val xtemp = x * x - y * y + x0
            y = 2 * x * y + y0
            x = xtemp
            iters++
        }

        return Color.BLACK.interpolate(Color.WHITE, iters / 1000.0)
    }
}

class App : Application() {
    override fun start(primaryStage: Stage) {
        val pixelGridRef = Ref(PixelGrid())

        constructArtistikFx(renderer, pixelGridRef, primaryStage, "PixelGrid", DIMENSION_720s * 1.2)

        val computation = MandelbrotComputation(pixelGridRef.value.image)

        computation.run(1)

        val time = measureNanoTime {
            while (computation.isGoing) {
                Thread.sleep(0)
            }
        }

        println("Result: ${time / 1000000}ms")

    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}