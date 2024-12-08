@file:OptIn(ExperimentalJsExport::class, ExperimentalStdlibApi::class, ExperimentalStdlibApi::class)
@file:Suppress("NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.global

import io.github.skreeps.api.prototypes.RoomPosition

/**
 * @see [GameMap.visual]
 */
external class MapVisual {

    /**
     * Draw a line
     *
     * @param pos1 The start position object
     * @param pos2 The finish position object
     *
     * @return The [MapVisual] object itself, so that you can chain calls
     */
    fun line(pos1: RoomPosition, pos2: RoomPosition, style: LineStyle = definedExternally): MapVisual

    /**
     * Draw a circle
     *
     * @param pos The position object of the center
     *
     * @return The [MapVisual] object itself, so that you can chain calls
     */
    fun circle(pos: RoomPosition, style: CircleStyle = definedExternally): MapVisual

    /**
     * Draw a rectangle
     *
     * @param topLeftPost The position object of the top-left corner
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     *
     * @return The [MapVisual] object itself, so that you can chain calls
     */
    fun rect(topLeftPost: RoomPosition, width: Number, height: Number, style: ShapeStyle = definedExternally): MapVisual

    /**
     * Draw a polygon
     *
     * @param points An array of points. Every item should be a [RoomPosition] object
     */
    fun poly(points: Array<RoomPosition>, style: ShapeStyle = definedExternally): MapVisual

    /**
     * Draw a text label. You can use any valid Unicode characters,
     * including [emoji](http://unicode.org/emoji/charts/emoji-style.txt)
     *
     * @param text The text to draw
     * @param pos The position object of the label baseline
     */
    fun text(text: String, pos: RoomPosition, style: TextStyle = definedExternally): MapVisual
}

@JsExport
class LineStyle(
    /**
     * Line width, default is 0.1
     */
    val width: Number = 0.1,

    /**
     * Line color in the following format: #ffffff (hex triplet). Default is white (#ffffff)
     */
    val color: Color = Color.White,

    /**
     * Opacity value, default is 0.5
     */
    val opacity: Number = 0.5,

    /**
     * Either solid line, dashed, or dotted. Default is solid
     */
    @JsName("lineStyle")
    val lineVariant: LineVariant = LineVariant.Solid,
)

@JsExport
open class ShapeStyle(

    /**
     * Fill color in the following format: #ffffff (hex triplet). Default is white (#ffffff)
     */
    @JsName("fill")
    val fillColor: Color = Color.White,

    /**
     * Opacity value, default is 0.5
     */
    val opacity: Number = 0.5,

    /**
     * Style of the line. Line opacity is ignored
     */
    lineStyle: LineStyle = LineStyle()
) {
    protected val stroke: Color = lineStyle.color
    protected val strokeWidth: Number = lineStyle.width
    protected val lineStyle: LineVariant = lineStyle.lineVariant
}

@JsExport
class CircleStyle(

    /**
     * Radius of the circle, default is 10
     */
    val radius: Number = 10,

    /**
     * Fill color in the following format: #ffffff (hex triplet). Default is white (#ffffff)
     */
    fillColor: Color = Color.White,

    /**
     * Opacity value, default is 0.5
     */
    opacity: Number = 0.5,

    /**
     * Style of the line. Line opacity is ignored
     */
    lineStyle: LineStyle = LineStyle()
) : ShapeStyle(fillColor, opacity, lineStyle)

@JsExport
class TextStyle(

    /**
     * Font color in the following format: #ffffff (hex triplet). Default is white (#ffffff)
     */
    val color: Color = Color.White,

    /**
     * The font family, default is sans-serif
     */
    val fontFamily: String = "sans-serif",

    /**
     * The font size in game coordinates, default is 10
     */
    val fontSize: Number = 10,

    /**
     * The font style ('normal', 'italic' or 'oblique')
     */
    val fontStyle: FontStyle = FontStyle.Normal,

    /**
     * The font variant ('normal' or 'small-caps')
     */
    val fontVariant: FontVariant = FontVariant.Normal,

    /**
     * Stroke color in the following format: #ffffff (hex triplet). Default is undefined (no stroke)
     */
    @JsName("stroke")
    val strokeColor: Color? = null,

    /**
     * Stroke width, default is 0.15
     */
    val strokeWidth: Number = 0.15,

    /**
     * Background color in the following format: #ffffff (hex triplet).
     * Default is undefined (no background).
     * When background is enabled, text vertical align is set to middle (default is baseline)
     */
    val backgroundColor: Color? = null,

    /**
     * Background rectangle padding, default is 0.5
     */
    val backgroundPadding: Number = 0.5,

    /**
     * Text align, either center, left, or right. Default is center
     */
    val align: TextAlign = TextAlign.Center,

    /**
     * Opacity value, default is 0.5
     */
    val opacity: Number = 0.5,
)

interface LineVariant {
    companion object {
        fun of(value: String) =
            value.unsafeCast<LineVariant>()

        val Solid = of("undefined")
        val Dashed = of("dashed")
        val Dotted = of("dotted")
    }
}

interface FontStyle {
    companion object {
        fun of(value: String) =
            value.unsafeCast<FontStyle>()

        val Normal = of("normal")
        val Italic = of("italic")
        val Oblique = of("oblique")
    }
}

interface FontVariant {
    companion object {
        fun of(value: String) =
            value.unsafeCast<FontVariant>()

        val Normal = of("normal")
        val SmallCaps = of("small-caps")
    }
}

interface TextAlign {
    companion object {
        fun of(value: String) =
            value.unsafeCast<TextAlign>()

        val Center = of("center")
        val Left = of("left")
        val Right = of("right")
    }
}

interface Color {
    companion object {
        fun of(value: String): Color =
            value.unsafeCast<Color>()

        fun hexTriplet(color: Int): Color = of(
            (color and 0x00ffffff.toInt())
                .toHexString(HexFormat {
                    number{
                        prefix = "#"
                        removeLeadingZeros = true
                        minLength = 6
                    }
                })
        )

       fun rgb(red: Int, green: Int, blue: Int): Color =
           hexTriplet((red shl 16) or (green shl 8) or blue)

       fun rgb(red: UByte, green: UByte, blue: UByte): Color =
           rgb(red.toInt(), green.toInt(), blue.toInt())

        val Transparent = of("transparent")
        val White = of("#ffffff")
        val Black = of("#000000")
        val Red = of("#ff0000")
        val Green = of("#00ff00")
        val Blue = of("#0000ff")
    }
}
