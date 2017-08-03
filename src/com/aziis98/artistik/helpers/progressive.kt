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

    abstract fun hasNext(sender: Any): Boolean

    abstract fun next(sender: Any): A

    protected abstract fun compute(param: A)

    fun update(sender: Any = Thread.currentThread().id) {
        if (isGoing && hasNext(sender))
        {
            val next = next(sender)
            computationCount++
            compute(next)
        }
    }

    fun abort() {
        isAborted = true
    }

}

abstract class WritableImageComputation(val image: WritableImage, val sections: Int) : ProgressiveTask<Point<Int>, WritableImage>(), WithThreadCount {

    val width
        get() = image.width.toInt()
    val height
        get() = image.height.toInt()

    abstract fun computePixel(x: Int, y: Int): Color

    override val threadCount: Int
        get() = sections

    override val totalComputationCount = width * height

    val countMap = LinkedHashMap<Any, Pair<Int, Int>>()

    override fun hasNext(sender: Any) = (countMap[sender]?.second ?: 0) < totalComputationCount / sections

    override fun next(sender: Any): Point<Int> {

        val counter = countMap.getOrPut(sender) { countMap.size to 0 }.second
        val pt = Point(counter % width, counter / width + height / sections * countMap[sender]!!.first)
        countMap[sender] = Pair(countMap[sender]!!.first, counter + 1)

        return pt
    }

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