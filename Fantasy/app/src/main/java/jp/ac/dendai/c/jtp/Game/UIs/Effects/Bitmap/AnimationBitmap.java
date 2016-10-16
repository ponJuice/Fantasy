package jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/09/09.
 */
public class AnimationBitmap {
    Bitmap[] animation;
    public AnimationBitmap(Bitmap[] anim){
        animation = anim;
    }

    /**
     * 切り出しを行いアニメーションビットマップに変換
     * @param res_id
     * @param max_x     画像の横ピクセル
     * @param max_y     画像の縦ピクセル
     * @param count_x   横のコマ数
     * @param count_y   縦のコマ数
     * @param animCount 抽出したい画像の数
     * @return
     */
    public static AnimationBitmap createAnimation(int res_id,int max_x,int max_y,int count_x,int count_y,int animCount){
        Bitmap[] faces = new Bitmap[animCount];
        int _x = 0,_y = 0,counter = 0;
        int delta_x = max_x / count_x;
        int delta_y = max_y / count_y;
        for(int y = 0;y < count_y;y++){
            _y = y * delta_y;
            _x = 0;
            for(int x = 0;x < count_x;x++){
                if(counter >= animCount){
                    return new AnimationBitmap(faces);
                }
                _x = x * delta_x;
                faces[counter] = GLES20Util.loadBitmap(_x,_y,_x+delta_x,_y+delta_y,res_id);
                counter++;
            }
        }
        return new AnimationBitmap(faces);
    }

    public static AnimationBitmap createAnimation(String fileName,int length_x,int length_y,int x_count_max,int y_count_max,int animCount){
        Log.d("AnimationBitmap","create file : "+fileName);
        //画像ファイルを切り抜いて読み込む
        //targetActivity.get
        Bitmap[] array = new Bitmap[animCount];
        int _x = 0,_y = 0,count = 0;
        int delta_x = length_x / x_count_max;
        int delta_y = length_y / y_count_max;
        int width = 1;
        try {
            BitmapRegionDecoder regionDecoder;
            regionDecoder = BitmapRegionDecoder.newInstance(ImageReader.getInputStream(fileName),false);

            for (int y = 0; y < y_count_max; y++) {
                _y = y * delta_y;
                _x = 0;
                for (int x = 0; x < x_count_max; x++) {
                    if(count >= animCount)
                        break;
                    _x = x * delta_x;
                    int endX = _x+delta_x;
                    int endY = _y+delta_y;

                    Rect rect = new Rect(_x, _y,endX,endY);    //切り抜く領域（矩形クラス）の用意
                    Bitmap b = regionDecoder.decodeRegion(rect, null);
                    if (!b.isMutable())
                        b = b.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap result = Bitmap.createBitmap(endX - _x, endY - _y, Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(result);
                    b = Bitmap.createScaledBitmap(b, endX - _x - width - width, endY - _y - width - width, false);
                    c.drawBitmap(b, width, width, (Paint) null);

                    array[count] = result;
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AnimationBitmap(array);
    }

    public static AnimationBitmap createAnimation(int res_id,int max_x,int max_y,int count_x,int count_y){
        Bitmap[] temp = new Bitmap[count_x * count_y];
        int deltaX = max_x / count_x;
        int deltaY = max_y / count_y;
        int startX = 0;
        int startY = 0;
        for(int n = 0;n < count_y;n++){
            startY = deltaY * n;
            startX = 0;
            for(int m = 0;m < count_x;m++){
                int index = count_x * n + m;
                temp[index] = GLES20Util.loadBitmap(startX,startY,startX + deltaX,startY + deltaY,res_id);
                startX += deltaX;
            }
        }
        return new AnimationBitmap(temp);
    }
}
