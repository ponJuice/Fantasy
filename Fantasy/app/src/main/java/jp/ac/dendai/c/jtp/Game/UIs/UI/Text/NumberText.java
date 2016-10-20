package jp.ac.dendai.c.jtp.Game.UIs.UI.Text;

import java.util.HashMap;


import jp.ac.dendai.c.jtp.Game.UIs.Math.Vector2;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/09.
 */
public class NumberText extends Image {
    public enum ORIENTATION{
        horizontal,
        vertical
    }
    protected ORIENTATION orientation = ORIENTATION.horizontal;
    protected float r = 0.5f,g = 0.5f,b = 0.5f;
    protected static HashMap<String,StringBitmap[]> numberFont;
    protected Vector2 texOffset = new Vector2(),texScale = new Vector2(1,1);
    protected Vector2 maskOffset = new Vector2(),maskScale = new Vector2(1,1);
    protected StringBitmap[] number;
    protected float offset_x = 0,offset_y = 0;
    protected float top_margin,bottom_margin,base_margin;
    //width,height は　一文字の横,縦の長さ aspectは一文字のアスペクト比
    protected int num;
    protected int dig = 1;
    protected float mask_alpha = 1f;
    protected UIAlign.Align align_h = UIAlign.Align.CENTOR,align_v = UIAlign.Align.CENTOR;
    public NumberText(String fontName){
        if(numberFont == null)
            numberFont = new HashMap<>();
        if(numberFont.containsKey(fontName)){
            //フォントが既に登録されている
            number = numberFont.get(fontName);
        }else{
            //まだ登録されていない
            number = new StringBitmap[11];
            for(int n = 0;n < 10;n++){
                number[n] = GLES20Util.stringToStringBitmap(String.valueOf(n),fontName,50,255,255,255);
            }
            //マイナス記号の作成
            number[10] = GLES20Util.stringToStringBitmap("-",fontName,50,255,255,255);
            numberFont.put(fontName,number);
        }
        //aspect = (float)number[0].bitmap.getWidth() / (float)(-number[0].fm.ascent+number[0].fm.descent);
        float fm_height = number[0].fm.bottom - number[0].fm.top;
        //float fm_height = number[0].bitmap.getHeight();
        top_margin = ( fm_height - (number[0].fm.bottom - number[0].fm.ascent))*1.5f/fm_height;
        bottom_margin = (fm_height - (- number[0].fm.top))/fm_height;
        base_margin =(fm_height - (number[0].fm.bottom))/fm_height;
        aspect = calcAspect(number[0].bitmap);
        height = 1;
        width = height * aspect;
        updatePosition();

    }

    public void setOrientation(ORIENTATION o){
        orientation = o;
        updatePosition();
    }

    public ORIENTATION getOrientation(){
        return orientation;
    }

    @Override
    public void setX(float x){
        this.x = x;
    }

    @Override
    public void setY(float y){
        this.y = y;
    }

    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    /**
     * 横の長さを設定します
     * @param width
     */
    @Override
    public void setWidth(float width){
        float numDigit = (float) getNumOfDigit(num);
        if(numDigit < dig)
            numDigit = dig;
        if(num < 0)
            numDigit++;
        if(orientation == ORIENTATION.horizontal) {
            this.width = width / numDigit;
        }else{
            this.width = width;
        }
        if(useAspect){
            //アスペクトを使用する場合はオリエンテーションによって処理が異なる
            if(orientation == ORIENTATION.horizontal){
                //横書き
                height = this.width / aspect;
            }else{
                //縦書き
                height = this.width / aspect * numDigit;
            }
        }
        updatePosition();
    }

    public void setR(int r){
        this.r = (float)r/255f;

    }
    public void setG(int g){
        this.g = (float)g/255f;
    }
    public void setB(int b){
        this.b =(float)b/255;
    }

    @Override
    public void setHeight(float height){
        float numDigit = (float) getNumOfDigit(num);
        if(numDigit < dig)
            numDigit = dig;
        if(num < 0)
            numDigit++;
        if(orientation == ORIENTATION.horizontal) {
            this.height = height * numDigit;
        }else{
            this.height = height;
        }
        if(useAspect){
            //アスペクトを使用する場合はオリエンテーションによって処理が異なる
            if(orientation == ORIENTATION.horizontal){
                //横書き
                width = this.height * aspect;
            }else{
                //縦書き
                width = this.height * aspect * numDigit;
            }
        }
        updatePosition();
    }

    @Override
    public void useAspect(boolean flag){
        useAspect = flag;
    }

    public void setStuffing(int n){
        dig = n;
        updatePosition();
    }
    public int getStuffing(){
        return dig;
    }

    @Override
    public void draw(float offsetX,float offsetY) {
        boolean flag = num < 0;
        int l = getNumOfDigit(num);
        if(l < dig)
            l = dig;
        //if(flag)
        //    l++;
        float _x = x + offset_x + offsetX;
        float _y = y + offset_y + offsetY;
        for(int n = 0;n < l;n++){
            if(flag && n == 0) {
                image = number[10].bitmap;
                GLES20Util.DrawGraph(_x,_y,width,height,r,g,b,image,alpha,GLES20COMPOSITIONMODE.ALPHA);
                if(orientation == ORIENTATION.horizontal) {
                    _x += width;
                }else {
                    _y -= height;
                }
            }
            image = number[getDigit(num,l-n)].bitmap;
            GLES20Util.DrawGraph(_x,_y,width,height,r,g,b,image,alpha,GLES20COMPOSITIONMODE.ALPHA);
            if(orientation == ORIENTATION.horizontal) {
                _x += width;
            }else {
                _y -= height;
            }
        }
    }

    @Override
    public void setHorizontal(UIAlign.Align align){
        horizontal = align;
        updatePosition();
    }

    @Override
    public void setVertical(UIAlign.Align align){
        vertical = align;
        updatePosition();
    }


    protected int pow(int num, int count) {
        if (count < 0) {
            return 0;
        }
        else if (count < 1) {
            return 1;
        }
        int temp = num;
        for (; count > 1; count--) {
            temp *= num;
        }
        return temp;
    }

    protected  int getDigit(int num, int digit){
        digit = Math.abs(digit);
        if (digit <= 0)
            return 0;
        num = Math.abs(num);
        int c = pow(10, digit - 1);
        int d = pow(10, digit);
        int a = num / c;
        int b = num / d;

        return a - (b * 10);
    }

    protected int getNumOfDigit(int num){
        num = Math.abs(num);
        if(num <= 0){
            return 1;
        }
        return (int) Math.log10(num) + 1;
    }

    public void setNumber(int num){
        this.num = num;
        updatePosition();
    }

    protected void updatePosition(){
        int digit = getNumOfDigit(num);
        if(digit < dig)
            digit = dig;
        if(num < 0 )
            digit++;
        offset_y = 0;//base_margin * height;
        offset_x = 0;
        if(orientation == ORIENTATION.horizontal) {
            if (horizontal == UIAlign.Align.LEFT) {
                offset_x = width / 2f;
            } else if (horizontal == UIAlign.Align.CENTOR) {
                offset_x = - width * (float) digit / 2f + width / 2f;
            } else {
                offset_x = - width * (float) digit + width / 2f;
            }
            if(vertical == UIAlign.Align.TOP){
                offset_y = -height /2f + top_margin * height;
            }else if(vertical == UIAlign.Align.BOTTOM){
                offset_y = height /2f - bottom_margin * height;
            }
        }
        else {
            if (vertical == UIAlign.Align.TOP) {
                offset_y = -height / 2f + top_margin*height;
            } else if (vertical == UIAlign.Align.CENTOR) {
                offset_y = height * (float) digit / 2f - height/2f;
            } else {
                offset_y = height * (float) digit - height / 2f -bottom_margin * height;
            }
            if(horizontal == UIAlign.Align.LEFT){
                offset_x = width/2f;
            }else if(horizontal == UIAlign.Align.RIGHT){
                offset_x = -width/2f;
            }
        }
    }

    public int getNumber(){
        return num;
    }
}
