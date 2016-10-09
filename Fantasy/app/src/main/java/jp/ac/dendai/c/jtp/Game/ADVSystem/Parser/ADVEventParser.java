package jp.ac.dendai.c.jtp.Game.ADVSystem.Parser;

import android.graphics.Bitmap;
import android.text.method.BaseKeyListener;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.logging.XMLFormatter;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.ADVComponent;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Background;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.FlagBranch;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Branch.UserBranch;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Flag;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Scene;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Transition;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Parser.AssetManager;
import jp.ac.dendai.c.jtp.Game.Charactor.Face;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;

/**
 * Created by テツヤ on 2016/10/07.
 */
/*----------------------------
1.イベント内で使うテキストの読み込み
2.イベント内で使うサウンドの読み込み
3.イベント内で使う画像の読み込み
4.イベント内で登場する人物の顔グラ読み込み
5.イベントの内容を読み込み
----------------------------*/
public class ADVEventParser {
    public final static String FACE_TAG = "Face";
    public final static String TEXT_TAG = "Text";
    public final static String IMAGE_TAG ="Image";
    public final static String SOUND_TAG ="Sound";
    public static Event createEvent(String fileName){
        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(FileManager.readTextFile(Constant.EVENT_DIRECTORY + fileName)));
        }catch (Exception e){
            e.printStackTrace();
            debugOutputString("error end");
            return null;
        }
        AssetManager assetManager = new AssetManager();
        //顔グラの読み込み
        loadFace(assetManager,xpp);
        //テキストの読み込み
        loadText(assetManager,xpp);
        //画像の読み込み
        loadImage(assetManager,xpp);
        //サウンドの読み込み
        loadSound(assetManager,xpp);
        //コンテンツの読み込み
        debugOutputString("");
        debugOutputString("Content Load");
        ADVComponent first = loadContent(assetManager,xpp);

        Event e = new Event();
        e.setComponent(first);
        e.preparation();
        e.setAssetManager(assetManager);

        return e;
    }

    private static ADVComponent setComponent(ADVComponent parent,ADVComponent children){
        if(parent == null)
            return children;
        parent.setNext(children);
        return children;
    }

    public static ADVComponent _parser(AssetManager am,XmlPullParser xpp,String endTag){
        ADVComponentPack pack = new ADVComponentPack();
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            debugOutputString(xpp.getPositionDescription());
            if(eventType == XmlPullParser.END_TAG && xpp.getName().equals(endTag)){
                debugOutputString("Break Loop");
                break;
            }
            if(eventType == XmlPullParser.START_TAG){
                String str = xpp.getName();
                if(str.equals(Scene.tagName)){
                    Scene s = new Scene();
                    s.parseCreate(am,xpp);
                    pack.setComponent(s);
                }else if(str.equals(UserBranch.tagName)){
                    UserBranch ub = new UserBranch();
                    ub.parseCreate(am,xpp);
                    pack.setComponent(ub);
                }else if(str.equals(Background.tagName)){
                    Background b = new Background();
                    b.parseCreate(am,xpp);
                    pack.setComponent(b);
                }else if(str.equals(Transition.tagName)){
                    Transition t= new Transition();
                    t.parseCreate(am,xpp);
                    pack.setComponent(t);
                }else if(str.equals(FlagBranch.tagName)){
                    FlagBranch fb = new FlagBranch();
                    fb.parseCreate(am,xpp);
                    pack.setComponent(fb);
                }else if(str.equals(Flag.tagName)){
                    Flag g = new Flag();
                    g.parseCreate(am,xpp);
                    pack.setComponent(g);
                }
            }

            try{
                eventType = xpp.next();
            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
        }
        return pack.getFirst();
    }

    private static ADVComponent loadContent(AssetManager am,XmlPullParser xpp){
        ADVComponentPack pack = new ADVComponentPack();
        debugOutputString("Load Content Start");
        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        ADVComponent first = _parser(am,xpp,"Content");

        return first;
    }

    private static void loadFace(AssetManager am,XmlPullParser xpp){
        debugOutputString("Load Face Started");
        FaceReader fr = new FaceReader();
        fr.length_x = Constant.face_image_length_x;
        fr.length_y = Constant.face_image_length_y;
        fr.x_count_max = Constant.face_image_x_count_max;
        fr.y_count_max = Constant.face_image_y_count_max;
        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(eventType != XmlPullParser.END_DOCUMENT){
            debugOutputString("line : "+xpp.getLineNumber()+" eventType : "+eventType);
            if(eventType == XmlPullParser.START_TAG && xpp.getName().equals(FACE_TAG)) {
                debugOutputString(FACE_TAG+" find");
                try {
                    eventType = xpp.next();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if(eventType == XmlPullParser.END_TAG  && xpp.getName().equals(FACE_TAG)){
                        break;
                    }
                    debugOutputString(xpp.getPositionDescription());
                    //Faceタグの開始
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(FACE_TAG.toLowerCase())) {
                        fr.fileName = xpp.getAttributeValue(null, Constant.face_fileName);
                        fr.name = xpp.getAttributeValue(null, Constant.face_name);
                        fr.id = xpp.getAttributeValue(null,Constant.face_id);
                        debugOutputString("[HIT] fileName : "+fr.fileName + "  name : "+fr.name + "  id : "+fr.id);
                        Face f = fr.createFaces();
                        am.setFace(f);
                    }
                    try {
                        eventType = xpp.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            //次の行へ
            try {
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void loadText(AssetManager am,XmlPullParser xpp){
        debugOutputString("Load Text Start");
        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(eventType != XmlPullParser.END_DOCUMENT){
            debugOutputString("line : "+xpp.getLineNumber()+" eventType : "+eventType);
            if(eventType == XmlPullParser.START_TAG && xpp.getName().equals(TEXT_TAG)) {
                debugOutputString(TEXT_TAG+" find");
                try {
                    eventType = xpp.next();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if(eventType == XmlPullParser.END_TAG  && xpp.getName().equals(TEXT_TAG)){
                        break;
                    }
                    debugOutputString(xpp.getPositionDescription());
                    //Faceタグの開始
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(TEXT_TAG.toLowerCase())) {
                        String id = xpp.getAttributeValue(null,Constant.text_id);
                        try {
                            eventType = xpp.next();
                        }catch (IOException e){
                            e.printStackTrace();
                        }catch (XmlPullParserException e){
                            e.printStackTrace();
                        }
                        String text = xpp.getText();
                        debugOutputString("[HIT] id : "+id + "  text : "+text);
                        am.setText(id,text);
                    }
                    try {
                        eventType = xpp.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            //次の行へ
            try {
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void loadImage(AssetManager am,XmlPullParser xpp){
        debugOutputString("Load Image Start");
        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(eventType != XmlPullParser.END_DOCUMENT){
            debugOutputString("line : "+xpp.getLineNumber()+" eventType : "+eventType);
            if(eventType == XmlPullParser.START_TAG && xpp.getName().equals(IMAGE_TAG)) {
                debugOutputString(IMAGE_TAG+" find");
                try {
                    eventType = xpp.next();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if(eventType == XmlPullParser.END_TAG  && xpp.getName().equals(IMAGE_TAG)){
                        break;
                    }
                    debugOutputString(xpp.getPositionDescription());
                    //Faceタグの開始
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(IMAGE_TAG.toLowerCase())) {
                        String id = xpp.getAttributeValue(null,Constant.image_id);
                        String fileName = xpp.getAttributeValue(null,Constant.image_fileName);
                        Bitmap image = ImageReader.readImageToAssets(Constant.image_file_directory + fileName);
                        debugOutputString("[HIT] id : "+id + "  fileName : "+fileName);
                        am.setImage(id,image);
                    }
                    try {
                        eventType = xpp.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            //次の行へ
            try {
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void loadSound(AssetManager am,XmlPullParser xpp){
        debugOutputString("Load Sound Start");
        int eventType = XmlPullParser.END_DOCUMENT;
        try {
            eventType = xpp.getEventType();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
        while(eventType != XmlPullParser.END_DOCUMENT){
            debugOutputString("line : "+xpp.getLineNumber()+" eventType : "+eventType);
            if(eventType == XmlPullParser.START_TAG && xpp.getName().equals(SOUND_TAG)) {
                debugOutputString(SOUND_TAG+" find");
                try {
                    eventType = xpp.next();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if(eventType == XmlPullParser.END_TAG  && xpp.getName().equals(SOUND_TAG)){
                        break;
                    }
                    debugOutputString(xpp.getPositionDescription());
                    //Faceタグの開始
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(SOUND_TAG.toLowerCase())) {
                        String id = xpp.getAttributeValue(null,Constant.sound_id);
                        String fileName = xpp.getAttributeValue(null,Constant.sound_fileName);
                        debugOutputString("[HIT] id : "+id + "  fileName : "+fileName);
                        //サウンドファイルを作成
                    }
                    try {
                        eventType = xpp.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            //次の行へ
            try {
                eventType = xpp.next();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static ADVComponent parse(XmlPullParser xpp){
        int eventType = XmlPullParser.END_DOCUMENT;
        do{
            //タイプを取得
            try {
                eventType = xpp.getEventType();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }

            //データ解析
            if(eventType == XmlPullParser.START_TAG){
                //タグの開始
                //if(xpp.getName().equals())
            }


            //次にシーク
            try {
                xpp.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while(eventType != XmlPullParser.END_DOCUMENT);
        return null;
    }
    public static void debugOutputString(String str){
        Log.d("ADVEventParser",str);
    }
}
