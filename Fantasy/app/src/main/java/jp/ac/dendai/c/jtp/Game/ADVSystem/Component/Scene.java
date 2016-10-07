package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceManager;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Scene extends ADVComponent{
    protected Face face;
    protected FaceType faceType;
    protected Bitmap textbox_background;
    protected OneSentenceStreamText streamText;
    protected float timeBuffer;
    protected TalkBox talkBox;

    @Override
    public void draw() {
        talkBox.draw();
    }

    @Override
    public ADVComponent proc(Event event) {
        talkBox.proc();
        if(talkBox.isTextEnd() && event.getTouch() != null){
            //テキストが全て表示されていて、入力がある場合次へ
            return next;
        }
        timeBuffer += Time.getDeltaTime();
        return this;
    }

    @Override
    public void init(Event event) {
        event.setDrawTarget(this);
        talkBox = event.getTalkBox();
        talkBox.setFace(face);
        talkBox.setFaceType(faceType);
        talkBox.setBackground(textbox_background);
        talkBox.setText(streamText);
    }
    public static Scene create(String face,FaceType faceType,String text){
        Scene s = new Scene();
        s.face = FaceManager.getFace(face);
        s.faceType = faceType;
        s.streamText = OneSentenceStreamText.createStreamText(text
                , Constant.getBitmap(Constant.BITMAP.stream_text)
                ,Constant.talk_text_size
                ,Constant.talk_text_max_length_x
                ,Constant.talk_text_max_length_y
                ,Constant.talk_text_font_name
                ,Constant.talk_text_height_offset
                ,Constant.talk_text_color);
        s.streamText.setHeight(Constant.talk_text_height);
        s.streamText.setWidth(Constant.talk_text_width);
        s.textbox_background = GLES20Util.loadBitmap(R.mipmap.serihu_waku);
        return s;
    }

}
