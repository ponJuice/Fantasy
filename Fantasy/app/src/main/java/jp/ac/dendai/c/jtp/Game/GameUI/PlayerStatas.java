package jp.ac.dendai.c.jtp.Game.GameUI;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceManager;
import jp.ac.dendai.c.jtp.Game.Charactor.FaceReader;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.UserInterface.PlayerUI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

import static jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum.FaceType.normal;

/**
 * Created by wark on 2016/10/25.
 */

public class PlayerStatas {
    protected float x,y;
    protected PlayerData pd;
    protected Image background;
    protected Image face;
    protected Image name;
    protected float back_width = 0.4f;
    protected float back_height= 0.8f;
    protected float text_height = 0.15f;
    protected float text_y = 0.6f;
    protected StaticText atk,def,agl;
    protected PlayerUI playerUI;
    protected Player player;
    public PlayerStatas(){
        pd = GameManager.getPlayerData();
        player = new Player(pd);
        playerUI = new PlayerUI(player);
        playerUI.setX(GLES20Util.getWidth_gl() - playerUI.getWidth());
        playerUI.setY(GLES20Util.getHeight_gl() - playerUI.getHeight());
        background = new Image(Constant.getBitmap(Constant.BITMAP.system_message_box));
        background.useAspect(false);
        background.setHorizontal(UIAlign.Align.RIGHT);
        background.setVertical(UIAlign.Align.TOP);
        background.setHeight(GLES20Util.getHeight_gl() - playerUI.getHeight());
        background.setWidth(playerUI.getWidth());
        background.setX(GLES20Util.getWidth_gl());
        background.setY(playerUI.getY());
        atk = new StaticText("ATK : "+pd.getAtk(),null);
        def = new StaticText("DEF : "+pd.getDef(),null);
        agl = new StaticText("AGL : "+pd.getAgl(),null);

        atk.useAspect(true);
        atk.setHorizontal(UIAlign.Align.LEFT);
        atk.setHeight(text_height);
        atk.setX(GLES20Util.getWidth_gl()/2f);
        atk.setY(text_y);
        def.useAspect(true);
        def.setHorizontal(UIAlign.Align.LEFT);
        def.setHeight(text_height);
        def.setX(GLES20Util.getWidth_gl()/2f);
        def.setY(atk.getY() - atk.getHeight());
        agl.useAspect(true);
        agl.setHorizontal(UIAlign.Align.LEFT);
        agl.setHeight(text_height);
        agl.setX(GLES20Util.getWidth_gl()/2f);
        agl.setY(def.getY() - def.getHeight());

    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public void proc()
    {
        playerUI.getHpGage().setValue(pd.getHp());
        playerUI.getMpGage().setValue(pd.getMp());
        playerUI.refreshData();
    }


    public void draw(float offsetX,float offsetY){
        //player.setHp(pd.getHp());
        //player.setMp(pd.getMp());
        background.draw(offsetX,offsetY);
        playerUI.draw(offsetX+x,offsetY+y);
        atk.draw(offsetX,offsetY);
        def.draw(offsetX,offsetY);
        agl.draw(offsetX,offsetY);
    }
}
