package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import android.graphics.Color;
import android.renderscript.Float2;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Trans_Mode;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.Trans_Type;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class Transition extends ADVComponent implements Parseable{
    public final static String tagName = "Trans";
    public final static String fade = "fade";
    public final static String mode = "mode";
    public final static String type = "type";
    public final static String time = "time";
    public final static String color = "color";

    protected int _color;
    protected Trans_Mode _mode;
    protected Trans_Type _type;
    protected float _time;
    protected float timeBuffer;
    protected Image image;
    protected ADVComponent drawBack;
    @Override
    public void draw(float offset_x,float offset_y) {
        drawBack.draw(offset_x,offset_y);
        //トランジョンの表示
        image.draw(offset_x,offset_y);
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(_time < timeBuffer)
            return next;
        else{
            image.setAlpha(_mode.transition(_time,timeBuffer,_type));
            timeBuffer += Time.getDeltaTime();
            return this;
        }
    }

    @Override
    public void init(Event event){
        if(isInit)
            return;
        drawBack = event.getDrawTarget();
        event.setDrawTarget(this);
        image = new Image(GLES20Util.createBitmap(Color.red(_color),Color.green(_color),Color.blue(_color),255));
        image.useAspect(false);
        image.setWidth(GLES20Util.getWidth_gl());
        image.setHeight(GLES20Util.getHeight_gl());
        image.setX(GLES20Util.getWidth_gl()/2f);
        image.setY(GLES20Util.getHeight_gl()/2f);
        timeBuffer = 0;
        isInit = true;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        String a_mode = xpp.getAttributeValue(null,mode);
        String a_type = xpp.getAttributeValue(null,type);
        String a_time = xpp.getAttributeValue(null,time);
        String a_color = xpp.getAttributeValue(null,color);

        _mode = Trans_Mode.valueOf(a_mode);
        _type = Trans_Type.valueOf(a_type);
        _time = Float.valueOf(a_time);
        try {
            _color = Long.decode(a_color).intValue();
        }catch (Exception e){
            Log.d("Error Transition Color",a_color+" is not able to parse");
            _color = 0;
        }
        _color += 0xff000000;

    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
