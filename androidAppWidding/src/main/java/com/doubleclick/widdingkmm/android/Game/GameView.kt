package com.doubleclick.widdingkmm.android.Game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Picture
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.doubleclick.widdingkmm.android.R
import com.doubleclick.widdingkmm.android.`interface`.setOnGame
import java.security.SecureRandom
import java.util.*
import kotlin.math.log

class GameView(
    context: Context?,
    attrs: AttributeSet?/*, var COLUMNS: Int = 5, var ROWS: Int = 5*/
) :
    View(context, attrs) {
    private var COLUMNS = 7
    private var ROWS = 7
    private lateinit var cells: Array<Array<Cell?>>
    private var player: Cell? = null
    private var exit: Cell? = null
    private var cellSize = 0f
    private var hMargin = 0f
    private var vMargin = 0f
    private val wallPaint: Paint
    private val playerPaint: Paint
    private val exitPaint: Paint
    private val TAG = "GameView"
    private lateinit var setOnGame: setOnGame

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }


    private val random = SecureRandom()

    init {
        wallPaint = Paint()
        wallPaint.color = resources.getColor(R.color.wells);
        wallPaint.strokeWidth = WALL_THICKNESS
        playerPaint = Paint()
        playerPaint.color = Color.RED
        exitPaint = Paint()
        exitPaint.color = Color.BLUE
        createMaze()
    }

    private fun createMaze() {
        val stack = Stack<Cell?>()
        var current: Cell?
        var next: Cell?
        cells = Array(COLUMNS) { arrayOfNulls(ROWS) }
        for (i in 0 until COLUMNS) {
            for (j in 0 until ROWS) {
                cells[i][j] = Cell(i, j)
            }
        }
        player = cells[0][0]
        exit = cells[COLUMNS - 1][ROWS - 1]
        current = cells[0][0]
        current!!.visited = true
        do {
            next = getNeighbour(current)
            if (next != null) {
                removeWall(current, next)
                stack.push(current)
                current = next
                current.visited = true
            } else current = stack.pop()
        } while (!stack.empty())
    }

    private fun getNeighbour(current: Cell?): Cell? {
        val neighbours = ArrayList<Cell?>()

        // left neighbour
        if (current!!.column > 0) if (!cells[current.column - 1][current.row]!!.visited) neighbours.add(
            cells[current.column - 1][current.row]
        )

        // right neighbour
        if (current.column < COLUMNS - 1) if (!cells[current.column + 1][current.row]!!.visited) neighbours.add(
            cells[current.column + 1][current.row]
        )

        // top neighbour
        if (current.row > 0) if (!cells[current.column][current.row - 1]!!.visited) neighbours.add(
            cells[current.column][current.row - 1]
        )

        // bottom neighbour
        if (current.row < ROWS - 1) if (!cells[current.column][current.row + 1]!!.visited) neighbours.add(
            cells[current.column][current.row + 1]
        )
        if (neighbours.size > 0) {
            val index = random.nextInt(neighbours.size)
            return neighbours[index]
        }
        return null
    }

    private fun removeWall(current: Cell?, next: Cell) {
        if (current!!.column == next.column && current.row == next.row + 1) {
            current.topWall = false
            next.bottomWall = false
        }
        if (current.column == next.column && current.row == next.row - 1) {
            current.bottomWall = false
            next.topWall = false
        }
        if (current.column == next.column + 1 && current.row == next.row) {
            current.leftWall = false
            next.rightWall = false
        }
        if (current.column == next.column - 1 && current.row == next.row) {
            current.rightWall = false
            next.leftWall = false
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(resources.getColor(R.color.white))
        val width = width
        val height = height
        cellSize =
            if (width / height < COLUMNS / ROWS) (width / (COLUMNS + 1)).toFloat() else (height / (ROWS + 1)).toFloat()

        hMargin = (width - COLUMNS * cellSize) / 2
        vMargin = (height - ROWS * cellSize) / 2
        canvas.translate(hMargin, vMargin)
        try {
            for (x in 0 until COLUMNS) {
                for (y in 0 until ROWS) {
                    if (cells[x][y]!!.topWall) {
                        canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint
                        )
                    }
                    if (cells[x][y]!!.leftWall) {
                        canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            wallPaint
                        )
                    }
                    if (cells[x][y]!!.bottomWall) {
                        canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint
                        )
                    }
                    if (cells[x][y]!!.rightWall) {
                        canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint
                        )
                    }
                }
            }
            val margin = cellSize / 10
            canvas.drawRect(
                player!!.column * cellSize + margin,
                player!!.row * cellSize + margin,
                (player!!.column + 1) * cellSize - margin,
                (player!!.row + 1) * cellSize - margin,
                playerPaint
            )
            canvas.drawRect(
                exit!!.column * cellSize + margin,
                exit!!.row * cellSize + margin,
                (exit!!.column + 1) * cellSize - margin,
                (exit!!.row + 1) * cellSize - margin,
                exitPaint
            )
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e(TAG, "onDraw: ${e.message}")
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) return true
        if (event.action == MotionEvent.ACTION_MOVE) {
            Log.e(TAG, "onTouchEvent: ${event.action}")
            val x = event.x
            val y = event.y
            val playerCenterX = hMargin + (player!!.column + 0.5f) * cellSize
            val playerCenterY = vMargin + (player!!.row + 0.5f) * cellSize
            val dx = x - playerCenterX
            val dy = y - playerCenterY
            val absDx = Math.abs(dx)
            val absDy = Math.abs(dy)
            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    // move in x-direction
                    if (dx > 0) movePlayer(Direction.RIGHT) else movePlayer(Direction.LEFT)
                } else {
                    // move in y-direction
                    if (dy > 0) movePlayer(Direction.DOWN) else movePlayer(Direction.UP)
                }
            }
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun movePlayer(direction: Direction) {
        when (direction) {
            Direction.UP -> if (!player!!.topWall) player = cells[player!!.column][player!!.row - 1]
            Direction.DOWN -> if (!player!!.bottomWall) player =
                cells[player!!.column][player!!.row + 1]
            Direction.LEFT -> if (!player!!.leftWall) player =
                cells[player!!.column - 1][player!!.row]
            Direction.RIGHT -> if (!player!!.rightWall) player =
                cells[player!!.column + 1][player!!.row]
        }
        checkExit()
        invalidate()
    }

    private fun checkExit() {
        if (player === exit) {
            COLUMNS++
            ROWS++
            setOnGame.setOnClickGame(COLUMNS + 1)
            createMaze()
        }
    }

    private inner class Cell(var column: Int, var row: Int) {
        var topWall = true
        var leftWall = true
        var rightWall = true
        var bottomWall = true
        var visited = false
    }

    companion object {
        private const val WALL_THICKNESS = 4f
    }

    fun onItemGameChange(onGame: setOnGame) {
        setOnGame = onGame
    }

    fun setVerticx(p: Int) {
//        COLUMNS++;
//        ROWS++
    }

}
