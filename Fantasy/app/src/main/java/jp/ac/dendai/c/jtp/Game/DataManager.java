package jp.ac.dendai.c.jtp.Game;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Condition;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.openglesutil.Util.FileManager;

/**
 * Created by wark on 2016/10/25.
 */

public class DataManager{
    public static void saveData(){
        PlayerData pd = GameManager.getPlayerData();
        StringBuilder sb = new StringBuilder();
        String string = String.format("<Player town=\"%s\" b_hp=\"%s\" hp=\"%s\" b_mp=\"%s\" mp=\"%s\" atk=\"%s\" def=\"%s\" agl=\"%s\" >"
                ,pd.getTown().getName()
                ,pd.getBaseHp()
                ,pd.getHp()
                ,pd.getBaseMp()
                ,pd.getMp()
                ,pd.getAtk()
                ,pd.getDef()
                ,pd.getAgl());

        sb.append(string+"\n");

        Item[] items = pd.getItemList().toArray(new Item[0]);
        for(int n = 0;n < items.length;n++){
            String str = String.format("<Item name=\"%s\" num=\"%d\" />",items[n].getName(),items[n].getNumber());
            sb.append(str+"\n");
        }

        ArrayList<Skill> skill = pd.getSkill();
        for(int n = 0;n < skill.size();n++){
            String str = String.format("<Skill name=\"%s\" num=\"%d\" />",skill.get(n).getSkillName(),skill.get(n).getCount());
            sb.append(str+"\n");
        }
        sb.append("</Player>\n");

        sb.append("<Flag>\n");
        sb.append(FlagManager.getDataText());
        sb.append("</Flag>\n");

        Log.d("DataManager write","data:"+sb.toString());
        FileManager.writeTextFileLocal(Constant.saveDataFile,sb.toString());
    }

    public static void loadData(){
        PlayerData pd = GameManager.getPlayerData();
        String text = FileManager.readTextFileLocal(Constant.saveDataFile);
        Log.d("DataManager read","data:"+text);

        XmlPullParser xpp = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(text));
        }catch (Exception e){
            e.printStackTrace();
            //debugOutputString("error end");
        }

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("Player"))
                    pd.parseLoad(xpp);
                else if(xpp.getName().equals("Flag")){
                    FlagManager.loadData(xpp);
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
}
