import com.soywiz.korge.Korge
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.vector.roundRect

object Style {
  val WINDOW_BACKGROUND = Colors["#fdf7f0"]
  val LOGO_BACKGROUND = Colors["#edc403"]
  val GAME_FIELD_BACKGROUND = Colors["#b9aea0"]
  val GAME_FIELD_CELL = Colors["#d7c9bb"]
  val SCORE_BACKGROUND = GAME_FIELD_BACKGROUND
  val SCORE_TEXT = GAME_FIELD_CELL
  val BUTTON_BACKGROUND = GAME_FIELD_BACKGROUND
}

class Game {
  companion object {
    const val DEFAULT_WIDTH = 480
    const val DEFAULT_HEIGHT = 640
    const val ROUND = 5.0
  }

  suspend fun run() = Korge(
    width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT, title = "2048",
    bgcolor = Style.WINDOW_BACKGROUND
  ) {
    val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val restartImg = resourcesVfs["restart.png"].readBitmap()
    val undoImg = resourcesVfs["undo.png"].readBitmap()

    val cellSize = views.virtualWidth / 5.0
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0

    val bgLogo = roundRect(cellSize, cellSize, ROUND, fill = Style.LOGO_BACKGROUND) {
      position(leftIndent, 30.0)
    }
    text("2048", cellSize * 0.5, Colors.WHITE, font).centerOn(bgLogo)

    val bgField = roundRect(fieldSize, fieldSize, ROUND, fill = Style.GAME_FIELD_BACKGROUND) {
      position(leftIndent, topIndent)
    }

    val bgBest = roundRect(cellSize * 1.5, cellSize * 0.8, ROUND, fill = Style.SCORE_BACKGROUND) {
      alignRightToRightOf(bgField)
      alignTopToTopOf(bgLogo)
    }

    text("BEST", cellSize * 0.25, Style.SCORE_TEXT, font) {
      centerXOn(bgBest)
      alignTopToTopOf(bgBest, 5.0)
    }
    text("0", cellSize * 0.5, Colors.WHITE, font) {
      setTextBounds(Rectangle(0.0, 0.0, bgBest.width, cellSize - 24.0))
      alignment = TextAlignment.MIDDLE_CENTER
      alignTopToTopOf(bgBest, 12.0)
      centerXOn(bgBest)
    }

    val bgScore = roundRect(cellSize * 1.5, cellSize * 0.8, ROUND, fill = Style.SCORE_BACKGROUND) {
      alignRightToLeftOf(bgBest, 24)
      alignTopToTopOf(bgBest)
    }

    text("SCORE", cellSize * 0.25, Style.SCORE_TEXT, font) {
      centerXOn(bgScore)
      alignTopToTopOf(bgScore, 5.0)
    }
    text("0", cellSize * 0.5, Colors.WHITE, font) {
      setTextBounds(Rectangle(0.0, 0.0, bgScore.width, cellSize - 24.0))
      alignment = TextAlignment.MIDDLE_CENTER
      centerXOn(bgScore)
      alignTopToTopOf(bgScore, 12.0)
    }

    val btnSize = cellSize * 0.3
    val restartBlock = container {
      val background = roundRect(btnSize, btnSize, ROUND, fill = Style.BUTTON_BACKGROUND)
      image(restartImg) {
        size(btnSize * 0.8, btnSize * 0.8)
        centerOn(background)
      }
      alignTopToBottomOf(bgBest, 5)
      alignRightToRightOf(bgField)
    }
    val undoBlock = container {
      val background = roundRect(btnSize, btnSize, ROUND, fill = Style.BUTTON_BACKGROUND)
      image(undoImg) {
        size(btnSize * 0.6, btnSize * 0.6)
        centerOn(background)
      }
      alignTopToTopOf(restartBlock)
      alignRightToLeftOf(restartBlock, 5.0)
    }

    graphics {
      position(leftIndent, topIndent)
      fill(Style.GAME_FIELD_CELL) {
        for (i in 0..3) {
          for (j in 0..3) {
            roundRect(10 + (10 + cellSize) * i, 10 + (10 + cellSize) * j, cellSize, cellSize, ROUND)
          }
        }
      }
    }

    val blocks = mutableMapOf<Int, Block>()

    var freeId = 0
  }
}