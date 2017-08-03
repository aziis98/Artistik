package com.aziis98.artistik

import javafx.embed.swing.SwingFXUtils
import javafx.scene.canvas.Canvas
import java.awt.image.BufferedImage

/*
 * Created by aziis98 on 03/08/2017.
 */

val DIMENSION_480s = Dimensions(480, 480)

val DIMENSION_720s = Dimensions(720, 720)

val DIMENSION_480p = Dimensions(720, 480)

val DIMENSION_720p = Dimensions(1280, 720)
val DIMENSION_HD = DIMENSION_720p

val DIMENSION_1080p = Dimensions(1920, 1080)
val DIMENSION_FULLHD = DIMENSION_1080p

val DIMENSION_4K : Dimensions<Int> = DIMENSION_FULLHD * 2

fun <S> export(renderer: ArtistikRenderer<S>, state: S, dimensions: Dimensions<Int> = DIMENSION_FULLHD, scale: Double = 1.0): BufferedImage {

    val canvas = Canvas(dimensions.width * scale, dimensions.height * scale)

    renderer.render(canvas.graphicsContext2D, state)

    return SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null)

}