package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceManager;
import jp.ac.dendai.c.jtp.Game.Configuration;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Input;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Scene extends ADVComponent{
    protected Face face;
    protected Image image;
    protected StreamText text;
    protected FaceType faceType;
    protected float timeBuffer;

    @Override
    public void draw() {
        image.draw();
        text.draw();
    }

    @Override
    public ADVComponent proc(Event event) {
        if(Configuration.text_speed <= timeBuffer){
            for(int n = 0;n < (timeBuffer / Configuration.text_speed);n++){
                text.nextCharX();
            }
            timeBuffer = 0;
        }
        text.proc();
        if(text.isEnd() && event.getTouch() != null){
            //テキストが全て表示されていて、入力がある場合次へ
            return next;
        }
        timeBuffer += Time.getDeltaTime();
        return this;
    }

    @Override
    public void init(Event event) {
        event.setDrawTarget(this);
        image.setImage(face.getFace(faceType));
    }
    public static Scene create(String face,FaceType faceType,String text){
        Scene s = new Scene();
        s.face = FaceManager.getFace(face);
        s.faceType = faceType;
        s.text = StreamText.createStreamText(text
                , Constant.getBitmap(Constant.BITMAP.stream_text)
                ,Constant.talk_text_size
                ,Constant.talk_text_color);
        s.text.setX(Constant.talk_text_x);
        s.text.setY(Constant.talk_text_y);
        return s;
    }

}
