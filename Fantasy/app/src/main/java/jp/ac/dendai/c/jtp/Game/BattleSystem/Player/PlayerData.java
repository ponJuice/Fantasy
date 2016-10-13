package jp.ac.dendai.c.jtp.Game.BattleSystem.Player;

import java.util.LinkedList;

import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/09/16.
 */
public class PlayerData {
    protected int hp,atk,def;
    protected int equipment;
    protected LinkedList<Item> items;
    protected Image image;
    public PlayerData(int hp,int atk,int def){
        this.hp = hp = 100;
        this.atk = atk;
        this.def = def = 80;
    }
}
