package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.Select;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.ADVEventParser;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class UserSelect extends Select {
    /*------------ Parseable関連 -------------*/
    public final static String tagName = "UserSelect";
    public final static String select_text = "text";
    private final static float button_width = 0.3f;
    private final static float button_height = 0.1f;

    /*------------ UserSelect関連 ------------*/
    protected String text;
    protected Button button;
    public void draw(int index,int max){
        if(max == 1) {
            button.setX(GLES20Util.getWidth_gl() / 2f);
            button.setY(GLES20Util.getHeight_gl() / 2f);
        }else if(max == 2){
            button.setX(GLES20Util.getWidth_gl() / 4f * (float)index);
            button.setY(GLES20Util.getHight() / 2f);
        }else if(max == 3){
            if(index < 3) {
                button.setX(GLES20Util.getWidth_gl() / 4f * (float) index);
                button.setY(GLES20Util.getHight() / 4f);
            }else{
                button.setX(GLES20Util.getWidth_gl() /2f);
                button.setY(GLES20Util.getHeight_gl() / 4f * 3f);
            }
        }else if(max == 4){
            if(index == 0){
                button.setX(GLES20Util.getWidth_gl()/4f);
                button.setY(GLES20Util.getHeight_gl() / 2f);
            }else if(index == 1){
                button.setX(GLES20Util.getWidth_gl()/2f);
                button.setY(GLES20Util.getHeight_gl() / 4f);
            }else if(index == 2){
                button.setX(GLES20Util.getWidth_gl() /4f * 3f);
                button.setY(GLES20Util.getHeight_gl() / 2f);
            }else if(index == 3){
                button.setX(GLES20Util.getWidth_gl()/2f);
                button.setY(GLES20Util.getHeight_gl() / 4f * 3f);
            }
        }
    }

    public void proc(){
        button.proc();
    }

    public void touch(Touch touch){
        button.touch(touch);
    }

    public void setButtonListener(ButtonListener l){
        button.setButtonListener(l);
    }

    @Override
    public void parseCreate(AssetManager am, XmlPullParser xpp) {
        String text = xpp.getAttributeValue(null,select_text);
        button = new Button(0,0,button_width,-button_height,am.getText(text));
        button.setBackground(Constant.getBitmap(Constant.BITMAP.system_button));
        button.setCriteria(Button.CRITERIA.HEIGHT);
        button.setPadding(0.05f);

        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_TAG && !xpp.getName().equals(tagName)){
            if(eventType == XmlPullParser.START_TAG){
                next = ADVEventParser._parser(am,xpp);
            }

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getTagName() {
        return tagName;
    }
}
