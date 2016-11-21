package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import android.graphics.Bitmap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Condition;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.SkillEvolutions;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.Item.ItemTemplate;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.ParserUtil;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class PlayerData {
    protected int init_hp = 1000;
    protected int init_mp = 100;
    protected int init_atk = 100;
    protected int init_def = 30;
    protected int init_agl = 10;
    protected int init_money = 1000;
    protected Town town;
    protected String name = "アラン";
    protected Bitmap name_bitmap;
    protected int baseHp,hp,baseMp,mp,atk,def,agl;
    protected HashMap<String,Item> items;
    protected ArrayList<Skill> skills;
    //protected Image image;
    protected int money;
    public PlayerData(int hp,int atk,int def,int agl){
        name_bitmap = GLES20Util.stringToBitmap(name, Constant.fontName,25,255,255,255);
        items = new HashMap<>();
        skills = new ArrayList<>();

        allReset();
    }

    public void allReset(){
        items.clear();
        items.put("薬草",new Item(5,GameManager.getDataBase().getItem("薬草")));
        skills.clear();
        skills.add(GameManager.getDataBase().getSkill("突き"));
        hp = init_hp;
        baseHp = init_hp;
        mp = init_mp;
        baseMp = init_mp;
        atk = init_atk;
        def = init_def;
        agl = init_agl;
        money = init_money;
        town = GameManager.getDataBase().getTown("オネット");

        FlagManager.allReset(this);

    }

    public void setHp(int value){
        hp = Math.min(value,baseHp);
    }

    public void setMp(int value){
        mp = Math.min(value,baseMp);
    }

    public void setAtk(int value){
        atk = value;
    }

    public void setDef(int value){
        def = value;
    }

    public void setAgl(int value){
        agl = value;
    }

    public void resetHp(){
        hp = baseHp;
    }

    public void resetMp(){
        mp = baseMp;
    }

    public void masterSkill(Skill skill){
        int rank = skill.getRank();
        if(rank == 1){
            baseHp += 10;
            baseMp += 10;
            atk += 1;
            def += 1;
            agl += 1;
        }else if(rank == 2){
            baseHp += 15;
            baseMp += 15;
            atk += 5;
            def += 5;
            agl += 5;
        }else if(rank == 3){
            baseHp += 20;
            baseMp += 20;
            atk += 10;
            def += 10;
            agl += 10;
        }else if(rank == 4){
            baseHp += 25;
            baseMp += 25;
            atk += 15;
            def += 15;
            agl += 15;
        }else if(rank == 5){
            baseHp += 30;
            baseMp += 30;
            atk += 20;
            def += 20;
            agl += 20;
        }
        resetHp();
        resetMp();
        SkillEvolutions se = GameManager.getDataBase().getSkillEvolutions(skill.getSkillName());
        for(int n = 0;n < se.nextSkill.size();n++){
            skills.add(se.nextSkill.get(n));
        }
    }

    public int getHp(){
        return hp;
    }

    public int getMp(){
        return mp;
    }

    public int getAtk(){
        return atk;
    }

    public int getDef(){
        return def;
    }

    public int getAgl(){
        return agl;
    }

    public int getBaseHp(){return baseHp;}

    public int getBaseMp(){return baseMp;}

    public void setData(Player player){
        baseHp = (int)player.getBaseHp();
        hp = (int)player.getHp();
        baseMp = player.getBaseMp();
        mp = player.getMp();
        atk = (int)player.getBaseAtk();
        def = (int)player.getBaseDef();
        agl = (int)player.getBaseAgl();
        for(int n = 0;n < player.getItemList().size();n++){
            if(player.getItemList().get(n).getNumber() <= 0){
                items.remove(player.getItemList().get(n).getName());
            }else if(!items.containsKey(player.getItemList().get(n).getName())){
                items.put(player.getItemList().get(n).getName(),player.getItemList().get(n));
            }
        }
        skills = player.getSkillList();
        money = player.getMoney();
    }

    public void addItem(ItemTemplate itemTemplate,int num){
        if(items.containsKey(itemTemplate.getName())){
            items.get(itemTemplate.getName()).addItem(num);
        }else {
            Item item = new Item(num, itemTemplate);
            items.put(item.getName(), item);
        }
    }

    public void subItem(ItemTemplate itemTemplate,int num){
        if(items.containsKey(itemTemplate.getName())){
            Item item = items.get(itemTemplate.getName());
            item.subItem(num);
            if(item.getNumber() <= 0)
                items.remove(itemTemplate.getName());
        }else{

        }
    }

    public Item getItem(String key){
        if(items.containsKey(key)){
            return items.get(key);
        }
        return null;
    }
    public int getMoney(){
        return money;
    }
    public void setMoney(int moeny){
        this.money = moeny;
    }
    public void addMoney(int money){this.money += money;}
    public void subMoney(int money){
        this.money -= money;
    }
    public Town getTown(){
        return town;
    }
    public void setTown(Town town){
        this.town = town;
    }
    public ArrayList<Skill> getSkill(){
        return skills;
    }

    public Collection<Item> getItemList(){
        return items.values();
    }

    public void initBegin(){

    }

    public void parseLoad(XmlPullParser xpp){
        town = GameManager.getDataBase().getTown(xpp.getAttributeValue(null,"town"));
        baseHp = ParserUtil.convertInt(xpp,"b_hp");
        hp = ParserUtil.convertInt(xpp,"hp");
        baseMp = ParserUtil.convertInt(xpp,"b_mp");
        mp = ParserUtil.convertInt(xpp,"mp");
        atk = ParserUtil.convertInt(xpp,"atk");
        def = ParserUtil.convertInt(xpp,"def");
        agl = ParserUtil.convertInt(xpp,"agl");

        int eventType = XmlPullParser.END_DOCUMENT;
        try{
            eventType = xpp.getEventType();
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }

        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.END_TAG){
                if(xpp.getName().equals("Player"))
                    break;
            }

            if(eventType == XmlPullParser.START_TAG){
                if(xpp.getName().equals("Item")) {
                    int num = ParserUtil.convertInt(xpp,"num");
                    String name = xpp.getAttributeValue(null,"name");
                    Item item = new Item(num,GameManager.getDataBase().getItem(name));
                    items.put(item.getName(),item);
                }else if(xpp.getName().equals("Skill")){
                    int num = ParserUtil.convertInt(xpp,"num");
                    String name = xpp.getAttributeValue(null,"name");
                    Skill skill = GameManager.getDataBase().getSkill(name);
                    skill.setCountBuff(num);
                    boolean al = true;
                    for(int n = 0;n<skills.size();n++) {
                        if(skills.get(n).getSkillName().equals(skill.getSkillName())){
                            al = false;
                        }
                    }
                    if(al)
                        skills.add(skill);
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
