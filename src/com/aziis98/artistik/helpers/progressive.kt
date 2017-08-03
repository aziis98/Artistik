package com.aziis98.artistik.helpers

import com.aziis98.artistik.Point
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import kotlin.concurrent.thread

/*
 * Created by aziis98 on 03/08/2017.
 */

abstract class ProgressiveTask<A, B> {

    /**
     * Progress of the task, between 0.0 and 1.0
     */
    val progress
        get() = computationCount.toDouble() / totalComputationCount

    var isAborted: Boolean = false
        private set

    val isGoing
        get() = computationCount < totalComputationCount && !isAborted

    var computationCount = 0
        private set

    abstract val totalComputationCount: Int

    abstract fun next(count: Int): A

    protected abstract fun compute(param: A)

    fun update() {
        if (isGoing) {
            val next = next(computationCount++)
            compute(next)
        }
    }

    fun abort() {
        isAborted = true
    }

}

abstract class WritableImageComputation(val image: WritableImage) : ProgressiveTask<Point<Int>, WritableImage>() {

    val width
        get() = image.width.toInt()
    val height
        get() = image.height.toInt()

    abstract fun computePixel(x: Int, y: Int): Color

    override val totalComputationCount = width * height

    override fun next(count: Int): Point<Int> = Point(count % width, count / width)

    override fun compute(param: Point<Int>) {
        image.pixelWriter.setColor(param.x, param.y, computePixel(param.x, param.y))
    }

}

interface WithThreadCount {
    val threadCount: Int
}

fun <A, B> ProgressiveTask<A, B>.run(numOfThreads: Int = 1) = let { progressiveTask ->

    val threadCount = if (progressiveTask is WithThreadCount) progressiveTask.threadCount else numOfThreads

    repeat(threadCount) { index ->
        thread(isDaemon = true) {
            println("Starting thread $index")
            while (progressiveTask.isGoing) {
                progressiveTask.update()
            }
            println("Ending thread $index")
        }
    }
}