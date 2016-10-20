package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedList;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Condition.Condition;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/09/16.
 */
public class PlayerData {
    protected Town town;
    protected String name = "アラン";
    protected Bitmap name_bitmap;
    protected int hp,mp,atk,def,agl;
    protected int equipment;
    protected ArrayList<Item> items;
    protected ArrayList<Skill> skills;
    protected Image image;
    public PlayerData(int hp,int atk,int def,int agl){
        this.hp = hp = 100;
        this.atk = atk;
        this.def = def = 10;
        this.agl = agl = 25;
        this.mp = 100;
        name_bitmap = GLES20Util.stringToBitmap(name, Constant.fontName,25,255,255,255);
        skills = new ArrayList<>();
        skills.add(GameManager.getDataBase().getSkill("二段突き"));
        skills.add(GameManager.getDataBase().getSkill("超大技"));

        items = new ArrayList<>();
        items.add(new Item(5,GameManager.getDataBase().getItem("薬草")));

        town = GameManager.getDataBase().getTown("オネット");
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
}
