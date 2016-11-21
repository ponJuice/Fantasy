package jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.UserInterface;

import android.graphics.Color;
import android.media.audiofx.LoudnessEnhancer;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameUI.Gage;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/10/13.
 */

public class PlayerUI {
    protected final static float waku_height = 0.3f;
    protected final static float face_height = 0.25f;
    protected final static float face_x = 0.08f;
    protected final static float face_y = 0.025f;
    protected final static float gage_width = 0.4f;
    protected final static float gage_height= 0.025f;
    protected final static float hp_gage_x = 0.33f;
    protected final static float hp_gage_y = 0.15f;
    protected final static float mp_gage_x = 0.33f;
    protected final static float mp_gage_y = hp_gage_y-gage_height-0.05f;
    protected final static int hp_gage_front_color = Color.argb(255,30,71,255);
    protected final static int hp_gage_back_color = Color.argb(255,255,0,0);
    protected final static int mp_gage_front_color = Color.argb(255,46,135,18);
    protected final static int mp_gage_back_color = Color.argb(255,255,0,0);
    protected final static float name_x = 0.33f;
    protected final static float name_y = 0.28f;
    protected final static float name_height = 0.1f;
    protected StaticText hp_base_number;
    protected NumberText hp_number;
    protected StaticText mp_base_number;
    protected NumberText mp_number;
    protected Image waku,name;
    protected Gage hp_gage;
    protected Gage mp_gage;
    protected Face face;
    protected Image faceImage;
    protected FaceType faceType;
    protected float damage;
    protected boolean isDamage = false;
    protected float timeBuffer = 0;
    protected Player player;
    protected float x = 0,y = 0;

    public PlayerUI(Player player){
        this.player = player;
        FaceReader fr = new FaceReader();
        fr.name = "アラン";
        fr.fileName = "hero.png";
        fr.x_count_max = Constant.face_image_x_count_max;
        fr.y_count_max = Constant.face_image_y_count_max;
        fr.length_x = Constant.face_image_length_x;
        fr.length_y = Constant.face_image_length_y;
        face = fr.createFaces();

        waku = new Image(Constant.getBitmap(Constant.BITMAP.system_message_box));
        name = new Image(face.getNameImage());
        hp_gage = new Gage(hp_gage_back_color,hp_gage_front_color,gage_width,gage_height,player.getBaseHp(),0,player.getHp());
        mp_gage = new Gage(mp_gage_back_color,mp_gage_front_color,gage_width,gage_height,player.getBaseMp(),0,player.getMp());

        //設定
        waku.useAspect(true);
        waku.setHorizontal(UIAlign.Align.LEFT);
        waku.setVertical(UIAlign.Align.BOTTOM);
        waku.setHeight(waku_height);
        waku.setX(0);
        waku.setY(0);

        name.useAspect(true);
        name.setHorizontal(UIAlign.Align.LEFT);
        name.setVertical(UIAlign.Align.TOP);
        name.setHeight(name_height);
        name.setX(name_x);
        name.setY(name_y);

        faceType = FaceType.normal;

        faceImage = new Image(face.getFace(faceType));
        faceImage.useAspect(true);
        faceImage.setHorizontal(UIAlign.Align.LEFT);
        faceImage.setVertical(UIAlign.Align.BOTTOM);
        faceImage.setHeight(face_height);
        faceImage.setX(face_x);
        faceImage.setY(face_y);

        hp_gage.setX(hp_gage_x);
        hp_gage.setY(hp_gage_y);

        mp_gage.setX(mp_gage_x);
        mp_gage.setY(mp_gage_y);

        hp_base_number = new StaticText("/"+(int)player.getBaseHp(),null);
        mp_base_number = new StaticText("/"+(int)player.getBaseMp(),null);
        hp_number = new NumberText(Constant.fontName);
        mp_number = new NumberText(Constant.fontName);

        hp_base_number.useAspect(true);
        hp_base_number.setHeight(gage_height*3f);
        hp_base_number.setHorizontal(UIAlign.Align.LEFT);
        mp_base_number.useAspect(true);
        mp_base_number.setHeight(gage_height*3f);
        mp_base_number.setHorizontal(UIAlign.Align.LEFT);
        hp_number.useAspect(true);
        hp_number.setHeight(gage_height*3f);
        hp_number.setHorizontal(UIAlign.Align.RIGHT);
        mp_number.useAspect(true);
        mp_number.setHeight(gage_height*3f);
        mp_number.setHorizontal(UIAlign.Align.RIGHT);
        hp_number.setNumber((int)player.getHp());
        mp_number.setNumber((int)player.getMp());

        hp_number.setX(hp_gage_x + gage_width);
        hp_number.setY(hp_gage_y);
        hp_base_number.setX(hp_gage_x + gage_width);
        hp_base_number.setY(hp_gage_y);

        mp_number.setX(mp_gage_x + gage_width);
        mp_number.setY(mp_gage_y);
        mp_base_number.setX(mp_gage_x + gage_width);
        mp_base_number.setY(mp_gage_y);

    }

    public float getWidth(){
        return waku.getWidth();
    }
    public float getHeight(){
        return waku.getHeight();
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public boolean isEndGageEffect(){
        return !isDamage;
    }

    public void proc(){
    }

    public void refreshData(){
        hp_number.setNumber((int)hp_gage.getValue());
        mp_number.setNumber((int)mp_gage.getValue());
    }

    public Gage getHpGage(){
        return hp_gage;
    }

    public Gage getMpGage(){return mp_gage;}

    public void draw(float offsetX,float offsetY){
        hp_number.setNumber((int)hp_gage.getValue());
        mp_number.setNumber((int)mp_gage.getValue());
        waku.draw(offsetX+x,offsetY+y);
        faceImage.draw(offsetX+x,offsetY+y);
        name.draw(offsetX+x,offsetY+y);
        hp_gage.draw(offsetX+x,offsetY+y);
        mp_gage.draw(offsetX+x,offsetY+y);
        hp_base_number.draw(offsetX+x,offsetY+y);
        mp_base_number.draw(offsetX+x,offsetY+y);
        hp_number.draw(offsetX+x,offsetY+y);
        mp_number.draw(offsetX+x,offsetY+y);
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
}
