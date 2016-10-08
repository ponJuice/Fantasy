package jp.ac.dendai.c.jtp.Game.ADVSystem.Parser;

import android.graphics.Bitmap;

import java.util.HashMap;

import jp.ac.dendai.c.jtp.Game.Charactor.Face;

/**
 * Created by テツヤ on 2016/10/07.
 */

public class AssetManager {
    protected HashMap<String,String> texts;
    protected HashMap<String,Bitmap> images;
    protected HashMap<String,Face> faces;
    public AssetManager(){
        texts = new HashMap<>();
        images = new HashMap<>();
        faces = new HashMap<>();
    }
    public Face getFace(String name){
        if(faces.containsKey(name))
            return faces.get(name);
        return null;
    }
    public void setFace(Face face){
        faces.put(face.getId(),face);
    }
    public String getText(String id){
        if(texts.containsKey(id))
            return texts.get(id);
        return null;
    }
    public void setText(String id,String text){
        texts.put(id,text);
    }
    public Bitmap getImage(String id){
        if(images.containsKey(id))
            return images.get(id);
        return null;
    }
    public void setImage(String id,Bitmap bitmap){
        images.put(id,bitmap);
    }
    public void release(){
        texts.clear();
        images.clear();
        faces.clear();
    }
}
