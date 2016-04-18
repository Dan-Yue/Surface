package com.cain.surface;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private SurfaceHolder holder;

    private SurfaceView surface;

    private DrawRunnable drawRunnable;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surface = (SurfaceView) findViewById(R.id.main_surface);
        holder = surface.getHolder();
        holder.addCallback(this);
        //Canvas有可能为空：创建之前，销毁之后，调用改变方法的瞬间
        //在Create之前Destroyed之后都为空。Change有一个瞬间为空
//        Canvas canvas = holder.lockCanvas();
//        if (canvas != null) {
//
//            //所有的绘制必须在这之间写,可以在子线程中绘制
//            holder.unlockCanvasAndPost(canvas);
//
//        }
//        drawRunnable = new DrawRunnable(holder);
//        new Thread(drawRunnable).start();
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "HD.mp4").getAbsolutePath());
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        drawRunnable.setRunning(false);
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();
        ViewGroup.LayoutParams layoutParams = surface.getLayoutParams();
        layoutParams.height = videoHeight;
        layoutParams.width = videoWidth;
        surface.setLayoutParams(layoutParams);
//        mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        mediaPlayer.start();
    }
}
