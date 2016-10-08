package jp.ac.dendai.c.jtp.Game.ADVSystem.Event;

import android.opengl.GLES20;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Scene;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceManager;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/07.
 */

public class EventDebuger {

    public static Event createEvent(){
        Event event = new Event();
        FaceReader fr = new FaceReader();
        fr.name = "田中太郎";
        fr.fileName = "hero.png";
        fr.length_x = 384;
        fr.length_y = 192;
        fr.x_count_max = 4;
        fr.y_count_max = 2;
        Face f = fr.createFaces();
        FaceManager.setFace(f);
        //event.component = Scene.create(fr.name, FaceType.normal,"背中から腋の下へ斜はすに、渋段々染");
        event.drawTarget = event.component;
        event.talkBox = new TalkBox(Constant.talk_textbox_x,Constant.talk_textbox_y, GLES20Util.getHeight_gl(),0.3f);
        return event;
    }
}
