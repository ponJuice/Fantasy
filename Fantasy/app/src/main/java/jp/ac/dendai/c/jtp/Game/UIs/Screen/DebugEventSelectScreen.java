package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/10/10.
 */

public class DebugEventSelectScreen implements Screenable{
    protected boolean freeze;
    protected Bitmap image,black,red,green;
    protected Event event;
    protected float black_x = 0f,black_y = 0f;
    protected float black_lx = 0.4f,black_ly = 0.4f;
    protected float red_x = 0.0f,red_y = 0.0f;
    protected float red_lx = 0.2f,red_ly = 0.2f;
    protected float green_x = 0.4f,green_y = 0.4f;
    protected float green_lx = 0.2f,green_ly = 0.2f;

    public DebugEventSelectScreen(){
        //event = ADVEventParser.createEvent("event_text.event");
        //event.preparation();
        image = GLES20Util.loadBitmap(R.mipmap.town_01);
        black = GLES20Util.createBitmap(0,0,0,255);
        red = GLES20Util.createBitmap(255,0,0,255);
        green = GLES20Util.createBitmap(0,255,0,255);
    }
    @Override
    public void Proc() {
        if(freeze)
            return;
        //event.proc(null);
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        //event.draw();
        GLES20Util.DrawGraph(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f,GLES20Util.getWidth_gl(),GLES20Util.getHeight_gl(), image,1, GLES20COMPOSITIONMODE.ALPHA);
        /*if(!GLES20.glIsEnabled(GLES20.GL_STENCIL_TEST)){

        }*/
        //int a = 1+1;
        GLES20.glEnable(GLES20.GL_STENCIL_TEST);
        GLES20.glStencilMask(~0);
        GLES20.glClear(GLES20.GL_STENCIL_BUFFER_BIT);

        GLES20.glClearStencil(0);

        GLES20.glColorMask(false,false,false,false);
        GLES20.glStencilOp(GLES20.GL_KEEP,GLES20.GL_REPLACE,GLES20.GL_REPLACE);
        GLES20.glStencilFunc(GLES20.GL_ALWAYS,1,~0);
        GLES20Util.DrawGraph(black_x + black_lx , black_y + black_lx,black_lx,black_ly,black , 1f, GLES20COMPOSITIONMODE.ALPHA);

        GLES20.glColorMask(true, true, true, true);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);
        GLES20.glStencilFunc(GLES20.GL_EQUAL,1, ~0);
        GLES20Util.DrawGraph(red_x + red_lx , red_y + red_lx,red_lx,red_ly,red , 1f, GLES20COMPOSITIONMODE.ALPHA);

        GLES20.glColorMask(true, true, true, true);
        GLES20.glStencilOp(GLES20.GL_KEEP, GLES20.GL_KEEP, GLES20.GL_KEEP);
        GLES20.glStencilFunc(GLES20.GL_EQUAL,1, ~0);
        GLES20Util.DrawGraph(green_x + green_lx , green_y + green_lx,red_lx,green_ly,green , 1f, GLES20COMPOSITIONMODE.ALPHA);

        GLES20.glDisable(GLES20.GL_STENCIL_TEST);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        //event.touch();
    }

    @Override
    public void death() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void freeze() {
        freeze = true;
    }

    @Override
    public void unFreeze() {
        freeze = false;
    }
}
