package com.cain.surface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Administrator on 16-4-18.
 * User:cain[FR]
 * Date:16-4-18
 * Email:1543880711@qq.com
 * ProjectName:Surface
 */
public class DrawRunnable implements Runnable {

    private static final String TAG = DrawRunnable.class.getSimpleName();

    private SurfaceHolder holder;

    private boolean running;

    private Paint paint;

    public DrawRunnable(SurfaceHolder holder) {
        this.holder = holder;
        running = true;
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: " + Thread.currentThread().getName());
        while (running){
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                canvas.drawText("Surface绘制",20,200,paint);
                holder.unlockCanvasAndPost(canvas);
            }
        }
        Log.d(TAG, "run: 结束");
    }
}
