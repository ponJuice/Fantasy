package jp.ac.dendai.c.jtp.Game;

import android.graphics.Bitmap;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.Battle.Player.PlayerData;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/07/08.
 */
public class Constant {
    public enum BITMAP{
        white,
        black,
        bilinear,
        stream_text,
        system_button
    }
    private static float sens = 1.0f;
    public static void setSens(float n){ sens = n;}
    public static float getSens(){return sens;}
    public static final String fontName = "custom_font.ttf";
    protected static Bitmap text_effect_white,text_effect_mask,system_button,black,stream_text;
    protected static PlayerData playerData;
    public final static int talk_text_size = 25;
    public final static int talk_text_color = 0xFFFFFFFF;  //a,r,g,b
    public final static float talk_textbox_x = 0;
    public final static float talk_textbox_y = 0;
    public final static int talk_text_max_length_x = 20;
    public final static int talk_text_max_length_y = 3;
    public final static String talk_text_font_name = "メイリオ";
    public final static int talk_text_height_offset = 0;
    public final static float talk_text_width = 1.1f;
    public final static float talk_text_height = 0.25f;
    //public final static float


    public static void init(){
        if(text_effect_white == null)
            text_effect_white = GLES20Util.loadBitmap(R.mipmap.text_effect_white);
        if(text_effect_mask == null)
            text_effect_mask = GLES20Util.loadBitmap(R.mipmap.text_effect_mask);
        if(system_button == null)
            system_button = GLES20Util.loadBitmap(R.mipmap.button);
        if(black == null)
            black = GLES20Util.createBitmap(255,0,0,0);
        if(stream_text == null)
            stream_text = GLES20Util.loadBitmap(R.mipmap.text_mask);
    }
    public static Bitmap getBitmap(BITMAP f){
        if(f == BITMAP.white)
            return text_effect_white;
        else if(f == BITMAP.bilinear)
            return text_effect_mask;
        else if(f == BITMAP.system_button)
            return system_button;
        else if(f == BITMAP.black){
            return black;
        }else if(f == BITMAP.stream_text){
            return stream_text;
        }
        return text_effect_white;
    }

}
