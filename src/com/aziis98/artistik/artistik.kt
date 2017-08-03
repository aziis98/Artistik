package com.aziis98.artistik

import javafx.scene.canvas.GraphicsContext

interface ArtistikRenderer<in S> {
    fun render(g: GraphicsContext, state: S)
}