package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import android.graphics.Bitmap;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Texts.TextManager;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceManager;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StreamText.OneSentenceStreamText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TalkBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Scene extends ADVComponent implements Parseable{
    /*------- Parseable関連 -----------*/
    public final  static String tagName = "Scene";
    public final  static String attrib_face = "face";
    public final  static String attrib_type = "type";
    public final  static String attrib_text = "text";
    public final  static String attrib_file = "file";
    public final  static String attrib_name = "name";
    public final  static String attrib_scroll = "autoscroll";

    /*------- ADVComponent関連 ---------*/
    protected Face face;
    protected FaceType faceType;
    protected Bitmap textbox_background;
    protected OneSentenceStreamText streamText;
    protected float timeBuffer;
    protected TalkBox talkBox;
    protected Touch touch;
    protected boolean isTouch = false;
    protected boolean isInit = false;
    protected boolean autoScroll = false;

    @Override
    public void draw() {
        talkBox.draw();
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        talkBox.proc();
        if(talkBox.isTextEnd()){
            if((isTouch || autoScroll) &&  (event.getTouch() == null || event.getTouch().getTouchID() == -1))
                //テキストが全て表示されていて、入力がある場合次へ
                return next;
        }
        if(event.getTouch() != null && event.getTouch().getTouchID() != -1){
            isTouch = true;
        }
        timeBuffer += Time.getDeltaTime();
        return this;
    }

    @Override
    public void init(Event event) {
        if(!isInit) {
            event.setDrawTarget(this);
            talkBox = event.getTalkBox();
            talkBox.setFace(face);
            talkBox.setFaceType(faceType);
            talkBox.setBackground(textbox_background);
            talkBox.setText(streamText);
            isInit = true;
        }
    }
    public static Scene create(Face face,FaceType faceType,String text){
        Scene s = new Scene();
        create(s,face,faceType,text);
        return s;
    }

    private static void create(Scene s,Face face,FaceType faceType,String text){
        s.face = face;
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
    }

    @Override
    public void parseCreate(AssetManager am,XmlPullParser xpp) {
        Log.d(tagName+" Parse","");
        //顔の所得
        String face = xpp.getAttributeValue(null,attrib_face);
        //表情の所得
        String value = xpp.getAttributeValue(null,attrib_type);
        FaceType faceType = FaceType.valueOf(value);
        //テキストの取得
        String text_id = xpp.getAttributeValue(null,attrib_text);
        //スクロール
        String scroll = xpp.getAttributeValue(null,attrib_scroll);
        if(scroll != null)
            autoScroll = Boolean.parseBoolean(scroll);

        create(this,am.getFace(face),faceType,am.getText(text_id));
    }

    @Override
    public String getTagName() {
        return null;
    }
}
