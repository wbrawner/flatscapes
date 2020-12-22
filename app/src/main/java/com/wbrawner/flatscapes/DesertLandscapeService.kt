package com.wbrawner.flatscapes

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.service.wallpaper.WallpaperService
import android.util.TypedValue
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import kotlin.math.max
import kotlin.random.Random

class DesertLandscapeService : WallpaperService() {

    override fun onCreateEngine() = Engine()

    inner class Engine : WallpaperService.Engine() {
        private val random = Random(24849010328)
        private val stars = mutableListOf<Drawable>()
        private var lastKnownUiModeConfig: Int? = null
        private var holder: SurfaceHolder? = null

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            for (i in 1..5) {
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_sun)?.apply {
                    val padding = i.toFloat().toDp()
                    bounds = Rect(0, 0, padding.toInt(), padding.toInt())
                    stars.add(this)
                }
            }
        }

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder?) {
            super.onSurfaceRedrawNeeded(holder)
            this.holder = holder
            lastKnownUiModeConfig = applicationContext?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
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

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (applicationContext?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != lastKnownUiModeConfig) {
                onSurfaceRedrawNeeded(this.holder)
            }
        }

        private fun drawStars(canvas: Canvas) {
            canvas.save()
            var totalY = 0f
            while (totalY < canvas.height) {
                var returnX = 0f
                while (true) {
                    val star = stars[random.nextInt(0, stars.size - 1)]
                    val x = ((canvas.width / 2) * random.nextFloat())
                    if (returnX + x >= canvas.width) break
                    canvas.translate(x, 0f)
                    star.draw(canvas)
                    returnX += x
                }
                val y = 8f.toDp()
                canvas.translate(returnX * -1, y)
                totalY += y
            }
            canvas.restore()
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