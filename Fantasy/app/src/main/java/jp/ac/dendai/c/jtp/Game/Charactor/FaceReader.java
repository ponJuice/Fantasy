package jp.ac.dendai.c.jtp.Game.Charactor;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class FaceReader {
    public int x_count_max,y_count_max;
    public int length_x,length_y;
    public int id;
    public Bitmap[] createFaces(){
        Bitmap[] faces = new Bitmap[x_count_max*y_count_max];
        int _x = 0,_y = 0;
        int delta_x = length_x / x_count_max;
        int delta_y = length_y / y_count_max;
        for(int y = 0;y < y_count_max;y++){
            _y = y * delta_y;
            _x = 0;
            for(int x = 0;x < x_count_max;x++){
                _x = x * delta_x;
                faces[y*y_count_max + x] = GLES20Util.loadBitmap(_x,_y,_x+delta_x,_y+delta_y,1,id);
            }
        }
        return faces;
    }
}
