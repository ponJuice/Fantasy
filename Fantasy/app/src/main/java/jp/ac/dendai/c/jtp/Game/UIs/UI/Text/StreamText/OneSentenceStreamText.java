package jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by Goto on 2016/10/07.
 */

public class OneSentenceStreamText implements UI {
    protected UIAlign.Align holizontal = UIAlign.Align.CENTOR,vertical = UIAlign.Align.CENTOR;
    protected String[] string;
    protected Bitmap text;
    protected Bitmap mask;
    protected int max_x_length,offset;
    protected int char_x = 0,char_y = 0;    //現在表示位置
    protected float aspect; //縦：横 = 1 : aspect
    protected float length_x = 0,length_y = 0;  //テキスト画像の縦横長さ
    protected float x = 0,y = 0;    //表示位置
    public OneSentenceStreamText(String[] string,Bitmap text,Bitmap mask,int max_x_length,int offset){
        this.string = string;
        this.text = text;
        this.max_x_length = max_x_length;
        this.mask = mask;
        aspect = (float)text.getWidth() / (float)text.getHeight();
        length_y = 1;
        length_x = aspect;
        this.offset = offset;
    }

    public void setHeight(float n){
        length_y = n;
        length_x = n * aspect;
    }
    public void setWidth(float n){
        length_x = n;
        length_y = n / aspect;
    }
    public int getStringLength(){
        int temp = 0;
        for(int n = 0;n < string.length;n++){
            temp += string[n].length();
        }
        return temp;
    }

    /**
     * 表示するテキストが最後まで来たかを返します
     * @return
     */
    public boolean isEnd(){
        return char_y+1 >= string.length && char_x >= string[string.length-1].length();
    }
    public void setChar_x(int n){
        char_x = n;
    }
    public void setChar_y(int n){
        char_y = n;
    }
    public int getChar_x(){
        return char_x;
    }
    public int getChar_y(){
        return  char_y;
    }
    public void nextCharX(){
        char_x++;
        if(string[char_y].length() < char_x){
            char_x = 1;
            nextCharY();
        }
    }
    public void nextCharY(){
        char_y++;
        if(string.length <= char_y){
            char_y = string.length-1;
            char_x = string[char_y].length();
        }
    }
    public void allDraw(){
        char_x = max_x_length;
        char_y = string.length-1;
    }
    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x,float offset_y) {
        float mojisuu = (float)max_x_length/2f - char_x;
        float pos_x = (float)text.getWidth()/(float)max_x_length * mojisuu /(float)text.getWidth();
        GLES20Util.DrawString(x + UIAlign.convertAlign(length_x,holizontal)+offset_x,y + UIAlign.convertAlign(length_y,vertical)+offset_y
                ,length_x,length_y
                ,0,0,1,1
                ,pos_x , ((float)(offset * char_y) / (float)text.getHeight()) - (float)char_y
                ,Constant.talk_text_max_length_y,0,text,mask,1, GLES20COMPOSITIONMODE.ALPHA);
    }

    public static OneSentenceStreamText createStreamText(String text,Bitmap mask,int size,int max_length_x,int max_length_y,String fontName,int height_offset,int color) {
        return createStreamText(text, mask, size,max_length_x,max_length_y,fontName,height_offset, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static OneSentenceStreamText createStreamText(String text,Bitmap mask,int size,int max_length_x,int max_length_y,String fontName,int height_offset,int r,int g,int b){
        text = text.substring(0,Math.min(text.length(),max_length_x*max_length_y));
        //横の最大文字数で分割
        int lines = text.length() / max_length_x + 1;
        String[] line = new String[lines];
        for(int n = 0;n < line.length;n++){
            //文字列を抽出する
            int start = n * max_length_x;
            int end = Math.min(text.length(),(n + 1) * max_length_x);
            line[n] = text.substring(start,end);
        }
        //描画するテキスト
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(r, g, b));
        paint.setTextSize(size);
        int maxTextLength = 0;
        int textWidth = 0;
        int textHeight = 0;
        int lineHeight = 0;
        int charWidth = 0;
        Typeface type;
        try {
            type = Typeface.createFromAsset(GameManager.act.getAssets(), fontName);
            if (type != null)
                paint.setTypeface(type);
        }catch(Exception e){

        }
        Paint.FontMetrics fm = paint.getFontMetrics();
        for(int n = 0;n < line.length;n++){
            //テキストの表示範囲を設定
            maxTextLength = Math.max(line[n].length(),maxTextLength);
            charWidth = Math.max((int)paint.measureText(line[0].substring(0,1)),charWidth);
            textWidth = Math.max((int) paint.measureText(line[n]),textWidth);
            textHeight += (int) (Math.abs(fm.top) + fm.bottom);
            lineHeight = Math.max((int) (Math.abs(fm.top) + fm.bottom),lineHeight);
        }
        textHeight = Math.max(textHeight,lineHeight * max_length_y);
        textWidth = Math.max(textWidth,max_length_x * charWidth);
        maxTextLength = Math.max(maxTextLength,max_length_x);
        Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawARGB(255,0,0,0);
        for(int n = 0;n < line.length;n++) {
            //キャンバスからビットマップを取得
            float _x = 0;
            float _y = Math.abs(fm.top)+lineHeight*n + height_offset*n;
            canvas.drawText(line[n], 0,_y, paint);
        }
        return new OneSentenceStreamText(line,bitmap,mask,maxTextLength,height_offset);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public UIAlign.Align getVertical() {
        return vertical;
    }

    public void setVertical(UIAlign.Align vertical) {
        this.vertical = vertical;
    }

    public UIAlign.Align getHolizontal() {
        return holizontal;
    }

    public void setHolizontal(UIAlign.Align holizontal) {
        this.holizontal = holizontal;
    }
}
