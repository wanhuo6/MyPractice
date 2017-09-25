package com.ahuo.fire.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-9-22
 */
public class MyGlSurfaceView extends GLSurfaceView {
    public MyGlSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new MyRenderer());
    }
}
