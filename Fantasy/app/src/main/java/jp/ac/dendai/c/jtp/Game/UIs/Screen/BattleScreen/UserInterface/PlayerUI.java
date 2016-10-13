package jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.UserInterface;

import android.graphics.Color;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameUI.Gage;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

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
    protected final static float gage_x = 0.33f;
    protected final static float gage_y = 0.15f;
    protected final static float name_x = 0.33f;
    protected final static float name_y = 0.28f;
    protected final static float name_height = 0.1f;
    protected Player player;
    protected Image waku,name;
    protected Gage hp_gage;
    protected Face face;
    protected Image faceImage;
    protected FaceType faceType;
    protected float damage;
    protected boolean isDamage = false;
    protected float timeBuffer = 0;

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
        hp_gage = new Gage(Color.argb(255,255,0,0),Color.argb(255,64,64,255),gage_width,gage_height,player.getBaseHp(),0,50);

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

        hp_gage.setX(gage_x);
        hp_gage.setY(gage_y);
    }

    public boolean isEndGageEffect(){
        return !isDamage;
    }

    public void proc(){
        if(isDamage && timeBuffer <= Constant.hp_decrease_time){
            float c = (Constant.hp_decrease_time - timeBuffer) / Constant.hp_decrease_time;
            hp_gage.setValue(player.getHp() + damage*c);
            timeBuffer += Time.getDeltaTime();
        }else if(timeBuffer > Constant.hp_decrease_time){
            hp_gage.setValue(player.getHp());
            isDamage = false;
            timeBuffer = 0;
            return;
        }
    }

    public void draw(float offsetX,float offsetY){
        waku.draw(offsetX,offsetY);
        faceImage.draw(offsetX,offsetY);
        name.draw(offsetX,offsetY);
        hp_gage.draw(offsetX,offsetY);
    }
    public void damage(BattleAction battleAction){
        damage = battleAction.getDamage();
        isDamage = true;
    }
}
