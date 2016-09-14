package jp.ac.dendai.c.jtp.Game.Charactor;

import android.graphics.Bitmap;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class Hero {
    protected Bitmap[] faces;
    public Hero(FaceReader fr){
        faces = fr.createFaces();
    }
    public Bitmap getFace(int n){
        return faces[n % faces.length];
    }
}
