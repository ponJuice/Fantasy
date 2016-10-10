package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.Parseable;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.TextBox.TextBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/09.
 */

public class MessageBox extends ADVComponent implements Parseable {
    public final static String tagName = "Message";
    public final static String attrib_text = "text";
    public final static String attrib_time = "time";

    protected enum STATE{
        MOVE_START,
        MOVE_END,
        STOP
    }
    protected static TextBox textBox = new TextBox(0,0,0.3f,0.2f);
    protected STATE state;
    protected StaticText text;
    protected float time;
    protected float timeBuffer = 0;
    protected boolean isTouch= false;
    protected ADVComponent drawTarget;
    protected boolean isInit = false;
    @Override
    public void draw(float offset_x,float offset_y) {
        if(drawTarget != null)
            drawTarget.draw(offset_x,offset_y);
        textBox.draw(offset_x,offset_y);
    }

    @Override
    public ADVComponent proc(Event event) {
        init(event);
        if(state == STATE.MOVE_START) {
            if (time <= timeBuffer) {
                state = STATE.STOP;
                textBox.setY(GLES20Util.getHeight_gl() / 2f);
                timeBuffer = 0;
            } else {
                float temp = GLES20Util.getHeight_gl() / 2f + textBox.getHeight() / 2f;
                float temp2 = temp / time * timeBuffer - textBox.getHeight() / 2f;
                textBox.setY(temp2);
            }
        }else if(state == STATE.MOVE_END){
            if (time <= timeBuffer) {
                state = STATE.STOP;
                textBox.setY(GLES20Util.getHeight_gl() + textBox.getHeight()/2f);
                return next;
            } else {
                float temp = GLES20Util.getHeight_gl() / 2f + textBox.getHeight() / 2f;
                float temp2 = temp / time * timeBuffer + GLES20Util.getHeight_gl()/2f;
                textBox.setY(temp2);
            }
        }else{
            if(isTouch &&  (event.getTouch() == null || event.getTouch().getTouchID() == -1)){
                state = STATE.MOVE_END;
                timeBuffer = 0;
            }
        }
        timeBuffer += Time.getDeltaTime();

        if(event.getTouch() != null && event.getTouch().getTouchID() != -1){
            isTouch = true;
        }

        return this;
    }

    @Override
    public void init(Event event) {
        if(isInit)
            return;
        drawTarget = event.getDrawTarget();
        event.setDrawTarget(this);
        textBox.setText(text);
        textBox.useAspect(true);
        textBox.setWidth(text.getWidth());
        state = STATE.MOVE_START;
        textBox.setX(GLES20Util.getWidth_gl()/2f);
        textBox.setY(0f - textBox.getHeight()/2f);

        isInit = true;
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        time = 1;
        String text_id = xpp.getAttributeValue(null,attrib_text);
        text = new StaticText(am.getText(text_id), Constant.getBitmap(Constant.BITMAP.white));
        textBox.setBackground(Constant.getBitmap(Constant.BITMAP.system_message_box));

        String _time = xpp.getAttributeValue(null,attrib_time);
        if(_time != null)
            time = Float.parseFloat(_time);
    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
