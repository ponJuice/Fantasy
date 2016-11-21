package jp.ac.dendai.c.jtp.Game.ADVSystem.Flag;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StringBitmap;
import jp.ac.dendai.c.jtp.ParserUtil;

import static java.util.logging.Logger.global;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class FlagManager {
    public enum FlagType{
        system,
        global,
        local,
        constant;

        public static FlagType parse(String str){
            if(str.equals("system"))
                return system;
            if(str.equals("global"))
                return global;
            if(str.equals("local"))
                return local;
            return constant;
        }
    }
    public enum ScreenType{
        start(0),
        map(1),
        talk(2),
        town(3),
        battle(4),
        dungeon(5);

        private final int id;
        private ScreenType(final int id){
            this.id = id;
        }
        public int getInt(){
            return this.id;
        }

    }
    /*
    ----- Global Flag-----
    添字：説明
    0 : メインイベント進行状況
    1 : スクリーンタイプ
    　　0：スタート
        1：マップ
    　　2：トーク
    　　3：タウン
    　　4：バトル
    */
    public final static int town = 0;
    public final static int hp = 1;
    public final static int atk = 2;
    public final static int def = 3;
    public final static int agl = 4;
    public final static int mp = 5;


    protected static int global_flag_num = 128;
    protected static int system_flag_num = 32;
    protected static String global_flag_tag = "global";
    protected static String system_flag_tag = "system";
    protected static int[] global_flag = new int[global_flag_num];
    protected static PlayerData playerData;
    public static void allReset(PlayerData pd){
        playerData = pd;
        for(int n = 0;n < global_flag.length;n++){
            global_flag[n] = 0;
        }
    }
    public static void loadFlag(PlayerData pd){

    }
    public static int getFlagValue(FlagType type,int value){
        if(type == FlagType.global){
            //グローバルフラグの取得
            return global_flag[value];
        }else if(type == FlagType.system){
            //システムフラグの取得
            //return system_flag[value];
            if(value == town){
                return GameManager.getPlayerData().getTown().getId();
            }else if(value == hp){
                return GameManager.getPlayerData().getHp();
            }else if(value == atk){
                return GameManager.getPlayerData().getAtk();
            }else if(value == def){
                return GameManager.getPlayerData().getDef();
            }else if(value == agl){
                return GameManager.getPlayerData().getAgl();
            }else if(value == mp){
                return GameManager.getPlayerData().getMp();
            }
        }
        return value;
    }
    public static void setFlagValue(FlagType type,int index,int value){
        if(type == FlagType.global){
            //グローバルフラグの設定
            global_flag[index] = value;
        }else if(type == FlagType.system){
            //システムフラグの設定
            if(index == town){
                GameManager.getPlayerData().setTown(GameManager.getDataBase().getTown(value));
            }else if(index == hp){
                GameManager.getPlayerData().setHp(value);
            }else if(index == atk){
                GameManager.getPlayerData().setAtk(value);
            }else if(index == def){
                GameManager.getPlayerData().setDef(value);
            }else if(index == agl){
                GameManager.getPlayerData().setAgl(value);
            }else if(index == mp){
                GameManager.getPlayerData().setMp(value);
            }
        }
    }
    public static String toInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Global Flag = ");
        for(int n = 0;n < global_flag.length;n++){
            sb.append("["+n+" : "+global_flag[n]+"]");
        }
        sb.append("\n");
        sb.append("System Flag = ");
        for(int n = 0;n < 5;n++){
            sb.append("["+n+" : "+getFlagValue(FlagType.system,n)+"]");
        }
        return sb.toString();
    }
    public static void outputDebugInfo(){
        Log.d("Flag Manager Info",toInfo());
    }
    public static String getDataText(){
        StringBuilder sb = new StringBuilder();
        sb.append("<Global>");
        for(int n = 0;n < global_flag.length;n++){
            sb.append(global_flag[n]);
            sb.append(",");
        }
        sb.append("</Global>");

        return sb.toString();
    }
    public static void loadData(XmlPullParser xpp){
        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals("Flag"))
                    break;
            }

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("Global")) {
                    loadGlobalFlag(xpp);
                }
            }


            try{
                eventType = xpp.next();
            }catch(XmlPullParserException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    protected static void loadGlobalFlag(XmlPullParser xpp){
        String text = "";

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals("Global"))
                    break;
            }

            if(eventType == XmlPullParser.TEXT){
                text = xpp.getText();
                break;
            }


            try{
                eventType = xpp.next();
            }catch(XmlPullParserException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        String[] split = text.split(",");
        for(int n = 0;n < Math.min(split.length,global_flag.length);n++){
            global_flag[n] = Integer.parseInt(split[n]);
        }
    }
}
