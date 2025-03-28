package io.github.skreeps.api.prototypes

import io.github.skreeps.api.global.CircleStyle
import io.github.skreeps.api.global.LineStyle
import io.github.skreeps.api.global.ShapeStyle
import io.github.skreeps.api.global.TextStyle

/**
 * Room visuals provide a way to show various visual debug info in game rooms. You can use the
 * [RoomVisual] object to draw simple shapes that are visible only to you. Every existing
 * [Room] object already contains the [visual][Room.visual] property, but you also can create
 * new [RoomVisual] objects for any room (even without visibility) using the constructor.
 *
 * Room visuals are not stored in the database, their only purpose is to display something in you
 * browser. All drawings will persist for one tick and will disappear if not updated.
 * All [RoomVisual] API calls have no added CPU cost (their cost is natural and mostly related
 * to simple [JSON.serialize] calls). However, there is a usage limit: you cannot post
 * more than 500 KB of serialized data per one room (see [getSize] method).
 *
 * All draw coordinates are measured in game coordinates and centered to tile centers, i.e. (10,10)
 * will point to the center of the creep at `x:10; y:10` position. Fractional coordinates are allowed
 *
 * @constructor You can directly create new [RoomVisual] object in any room, even if it's invisible to your script
 * @param roomName The room name. If undefined, visuals will be posted to all rooms simultaneously
 */
external class RoomVisual(
    /**
     * The name of the room
     */
    val roomName: String
) {

    /**
     * Draw a line
     *
     * @param x1 The start X coordinate
     * @param y1 The start Y coordinate
     * @param x2 The finish X coordinate
     * @param y2 The finish Y coordinate
     */
    fun line(x1: Number, y1: Number, x2: Number, y2: Number, style: LineStyle = definedExternally): RoomVisual

    /**
     * Draw a line
     *
     * @param pos1 The start position object
     * @param pos2 The finish position object
     */
    fun line(pos1: RoomPosition, pos2: RoomPosition, style: LineStyle = definedExternally): RoomVisual

    /**
     * Draw a circle
     *
     * @param x The X coordinate of the center
     * @param y The Y coordinate of the center
     */
    fun circle(x: Number, y: Number, style: CircleStyle = definedExternally): RoomVisual

    /**
     * Draw a circle
     *
     * @param pos The position object of the center
     */
    fun circle(pos: RoomPosition, style: CircleStyle = definedExternally): RoomVisual

    /**
     * Draw a rectangle
     *
     * @param x The X coordinate of the top-left corner
     * @param y The Y coordinate of the top-left corner
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    fun rect(x: Number, y: Number, width: Number, height: Number, style: ShapeStyle = definedExternally): RoomVisual

    /**
     * Draw a rectangle
     *
     * @param topLeftPos The position object of the top-left corner
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    fun rect(topLeftPos: RoomPosition, width: Number, height: Number, style: ShapeStyle = definedExternally): RoomVisual

    /**
     * Draw a polyline
     *
     * @param points An array of points. Every item should be either an array with 2 numbers (i.e. `[10,15]`)
     */
    fun poly(points: Array<Array<Number>>, style: ShapeStyle = definedExternally): RoomVisual

    /**
     * Draw a polyline
     *
     * @param points An array of position objects
     */
    fun poly(points: Array<RoomPosition>, style: ShapeStyle = definedExternally): RoomVisual

    /**
     * Draw a text label. You can use any valid Unicode characters, including [emoji](http://unicode.org/emoji/charts/emoji-style.txt)
     *
     * @param x The X coordinate of the label baseline point
     * @param y The Y coordinate of the label baseline point
     */
    fun text(text: String, x: Number, y: Number, style: TextStyle = definedExternally): RoomVisual

    /**
     * Draw a text label. You can use any valid Unicode characters, including [emoji](http://unicode.org/emoji/charts/emoji-style.txt)
     *
     * @param pos The position object of the label baseline point
     */
    fun text(text: String, pos: RoomPosition, style: TextStyle = definedExternally): RoomVisual

    /**
     * Remove all visuals from the room
     */
    fun clear(): RoomVisual

    /**
     * Get the stored size of all visuals added in the room in the current tick. It must not exceed 512,000 (500 KB)
     *
     * @return The size of the visuals in bytes
     */
    fun getSize(): Number

    /**
     * Returns a compact representation of all visuals added in the room in the current tick
     *
     * @return A string with visuals data. There's not much you can do with the string besides store them for later
     */
    fun export(): String

    /**
     * Add previously exported (with [RoomVisual.export]) room visuals to the room visual data of the current tick
     *
     * @param val The string returned from [RoomVisual.export]
     */
    fun import(`val`: String): RoomVisual
}