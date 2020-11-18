 package com.wbrawner.materialive

import android.app.WallpaperColors
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowInsets
import java.io.FileDescriptor
import java.io.PrintWriter

class MaterialiveService : WallpaperService() {

    override fun onCreateEngine() = Engine()

    inner class Engine: WallpaperService.Engine() {
        override fun getDesiredMinimumWidth(): Int {
            return super.getDesiredMinimumWidth()
        }

        override fun getDesiredMinimumHeight(): Int {
            return super.getDesiredMinimumHeight()
        }

        override fun isVisible(): Boolean {
            return super.isVisible()
        }

        override fun isPreview(): Boolean {
            return super.isPreview()
        }

        override fun setTouchEventsEnabled(enabled: Boolean) {
            super.setTouchEventsEnabled(enabled)
        }

        override fun setOffsetNotificationsEnabled(enabled: Boolean) {
            super.setOffsetNotificationsEnabled(enabled)
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            Log.d("Materialize", "onCreate")
            super.onCreate(surfaceHolder)
        }

        override fun onDestroy() {
            super.onDestroy()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
        }

        override fun onApplyWindowInsets(insets: WindowInsets?) {
            super.onApplyWindowInsets(insets)
        }

        override fun onTouchEvent(event: MotionEvent?) {
            super.onTouchEvent(event)
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            super.onOffsetsChanged(
                xOffset,
                yOffset,
                xOffsetStep,
                yOffsetStep,
                xPixelOffset,
                yPixelOffset
            )
        }

        override fun onCommand(
            action: String?,
            x: Int,
            y: Int,
            z: Int,
            extras: Bundle?,
            resultRequested: Boolean
        ): Bundle {
            return super.onCommand(action, x, y, z, extras, resultRequested)
        }

        override fun onDesiredSizeChanged(desiredWidth: Int, desiredHeight: Int) {
            super.onDesiredSizeChanged(desiredWidth, desiredHeight)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            Log.d("Materialize", "onSurfaceChanged")
            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder?) {
            Log.d("Materialize", "onSurfaceRedrawNeeded")
            super.onSurfaceRedrawNeeded(holder)
            holder?.draw { canvas ->
                canvas.drawColor(getColor(R.color.surface))
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            Log.d("Materialize", "onSurfaceCreated")
            super.onSurfaceCreated(holder)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
        }

        override fun onZoomChanged(zoom: Float) {
            super.onZoomChanged(zoom)
        }

        override fun notifyColorsChanged() {
            super.notifyColorsChanged()
        }
    }
}

 fun SurfaceHolder.draw(block: (canvas: Canvas) -> Unit) {
     val canvas = lockCanvas()
     block(canvas)
     unlockCanvasAndPost(canvas)
 }