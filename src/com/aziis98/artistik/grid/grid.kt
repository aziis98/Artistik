package com.aziis98.artistik.grid

import com.aziis98.artistik.IDimensioned
import com.aziis98.artistik.Point

/*
 * Created by aziis98 on 03/08/2017.
 */

//interface Grid<T> : Iterable<Pair<Point<Int>, T>> {
//
//    operator fun get(x: Int, y: Int): T?
//
//    operator fun set(x: Int, y: Int, value: Any)
//
//}
//
//class ArrayGrid<T>(override val width: Int, override val height: Int, initialValue: T) : Grid<T>, IDimensioned<Int> {
//
//    val array = Array(width * height) { initialValue as Any }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun get(x: Int, y: Int) =
//        if (x < 0 || x >= width || y < 0 || y >= height)
//            null
//        else
//            array[x + y * width] as T
//
//    override fun set(x: Int, y: Int, value: Any) {
//        array[x + y * width] = value
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun iterator() =
//        array
//            .mapIndexed { index, any -> Pair(Point(index % width, index / width), any as T) }
//            .iterator()
//
//}