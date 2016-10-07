package jp.ac.dendai.c.jtp.Game.Charactor;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;

/**
 * Created by テツヤ on 2016/10/07.
 */

public class Face {
    protected String name;
    protected Bitmap[] face;
    public String getName(){
        return name;
    }
    public Bitmap getFace(FaceType faceType){
        if(faceType == FaceType.normal){
            return face[0];
        }
        return null;
    }
}
