package com.wbrawner.materialive

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.util.TypedValue
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import kotlin.math.max
import kotlin.random.Random

class MaterialiveService : WallpaperService() {

    override fun onCreateEngine() = Engine()

    inner class Engine : WallpaperService.Engine() {
        private val translations = mutableListOf<Pair<Float, Float>>()
        private val random = Random(System.currentTimeMillis())

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder?) {
            Log.d("Materialize", "onSurfaceRedrawNeeded")
            super.onSurfaceRedrawNeeded(holder)
            holder?.draw { canvas ->
                val canvasRect = Rect(0, 0, canvas.width, canvas.height)
                ContextCompat.getDrawable(applicationContext, R.drawable.wallpaper_sky)
                    ?.apply {
                        bounds = canvasRect
                        draw(canvas)
                    }

                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> drawStars(canvas)
                    else -> drawSun(canvas)
                }

                ContextCompat.getDrawable(applicationContext, R.drawable.ic_desert)
                    ?.apply {
                        bounds = Rect(0, 0, canvas.width, canvas.width / 2)
                        canvas.save()
                        canvas.translate(0f, (canvas.height - bounds.bottom).toFloat())
                        draw(canvas)
                        canvas.restore()
                    }
            }
        }

        private fun drawStars(canvas: Canvas) {
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_sun)?.apply {
                val padding = 5f.toDp()
                bounds = Rect(0, 0, padding.toInt(), padding.toInt())
                canvas.save()
                val stepMin = canvas.width.toFloat() / 4
                val stepMax = canvas.width.toFloat()
                var totalY = 0f
                while (totalY < canvas.height) {
                    var returnX = 0f
                    while (true) {
                        val x = ((stepMax - stepMin) * random.nextFloat())
                        if (returnX + x >= canvas.width) break
                        canvas.translate(x, 0f)
                        draw(canvas)
                        returnX += x
                    }
                    val y = 10f.toDp()
                    canvas.translate(returnX * -1, y)
                    totalY += y
                }
                canvas.restore()
            }
        }

        private fun drawSun(canvas: Canvas) {
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_sun)?.apply {
                val padding = 50f.toDp()
                bounds = Rect(0, 0, padding.toInt(), padding.toInt())
                canvas.save()
                canvas.translate(
                    max(padding, (canvas.width - intrinsicWidth - padding) * random.nextFloat()),
                    intrinsicHeight + padding
                )
                draw(canvas)
                canvas.restore()
            }
        }
    }
}

fun SurfaceHolder.draw(block: (canvas: Canvas) -> Unit) {
    val canvas = lockCanvas()
    block(canvas)
    unlockCanvasAndPost(canvas)
}

fun Float.toDp(): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
)