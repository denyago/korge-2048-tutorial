
import Number.* // ktlint-disable no-unused-imports (used for convenience)
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.Font

class Block(number: Number, private val cellSize: Double, font: Font) : Container() {
  init {
    roundRect(cellSize, cellSize, 5.0, fill = number.color)
    val textColor = when (number) {
      ZERO, ONE -> Colors.BLACK
      else -> Colors.WHITE
    }
    text(number.value.toString(), textSizeFor(number), textColor, font) {
      centerBetween(0.0, 0.0, cellSize, cellSize)
    }
  }

  private fun textSizeFor(number: Number) = when (number) {
    ZERO, ONE, TWO, THREE, FOUR, FIVE -> cellSize / 2
    SIX, SEVEN, EIGHT -> cellSize * 4 / 9
    NINE, TEN, ELEVEN, TWELVE -> cellSize * 2 / 5
    THIRTEEN, FOURTEEN, FIFTEEN -> cellSize * 7 / 20
    SIXTEEN -> cellSize * 3 / 10
  }
}

fun Container.block(number: Number, cellSize: Double, font: Font) = Block(number, cellSize, font).addTo(this)