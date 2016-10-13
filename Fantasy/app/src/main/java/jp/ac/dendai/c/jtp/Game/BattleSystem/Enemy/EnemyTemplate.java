package jp.ac.dendai.c.jtp.Game.BattleSystem.Enemy;

import android.graphics.Bitmap;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;

/**
 * Created by Goto on 2016/09/16.
 */
public class EnemyTemplate {
    public String name;
    public int hp,atk,def;
    public Bitmap image;
    public Skill[] magics;
    public EnemyTemplate(String name,int hp,int atk,int def,Bitmap image,Skill[] magics){
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.image = image;
        this.magics = magics;
    }
}
