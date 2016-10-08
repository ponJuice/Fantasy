package jp.ac.dendai.c.jtp.Game.Charactor;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class FaceReader {
    public int x_count_max,y_count_max;
    public int length_x,length_y;
    public String fileName;
    public String name;
    public String id;
    public Face createFaces(){
        Bitmap[] faces = new Bitmap[x_count_max*y_count_max];
        cutFaceImage(faces);
        Face f = new Face();
        f.id = id;
        f.face = faces;
        f.name = name;
        f.name_image = GLES20Util.stringToBitmap(name,"メイリオ",25,255,255,255);
        return f;
    }
    private void cutFaceImage(Bitmap[] faces){
        //画像ファイルを切り抜いて読み込む
        //targetActivity.get
        int _x = 0,_y = 0;
        int delta_x = length_x / x_count_max;
        int delta_y = length_y / y_count_max;
        int width = 1;
        try {
            BitmapRegionDecoder regionDecoder;
            regionDecoder = BitmapRegionDecoder.newInstance(ImageReader.getInputStream(Constant.face_directory+fileName),false);

            for (int y = 0; y < y_count_max; y++) {
                _y = y * delta_y;
                _x = 0;
                for (int x = 0; x < x_count_max; x++) {
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

                    faces[y*y_count_max + x] = result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
