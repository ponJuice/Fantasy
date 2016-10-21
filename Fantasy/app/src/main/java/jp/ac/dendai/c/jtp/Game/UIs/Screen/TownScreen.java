package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.graphics.drawable.LevelListDrawable;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State.PlayerItemSelectState;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.MapSystem.ShopItem;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.AnimationBitmap;
import jp.ac.dendai.c.jtp.Game.UIs.Effects.Bitmap.Animator;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    protected enum Mode{
        non,
        shop,
        hotel
    }
    protected static final float padding = 0.1f;
    protected Mode mode = Mode.non;
    protected float shop_list_width = 0.5f;
    protected float shop_list_height= 1f;
    protected float button_height = 0.1f;
    protected float town_name_height = 0.1f;
    protected float town_name_frame_padding_horizon = 0.2f;
    protected float town_name_frame_padding_vertical = 0.05f;
    protected Town town;
    protected Image townName;
    protected Image townNameBack;
    protected Image background;
    protected Button mapBbutton;
    protected Button hotelButton;
    protected Button shopButton;
    protected Image image;
    protected List shopList;
    protected boolean freeze;
    public TownScreen(){
        mapBbutton = new Button(0,0,1,1,"マップへ");
        mapBbutton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        mapBbutton.useAspect(true);
        mapBbutton.setCriteria(UI.Criteria.Height);
        mapBbutton.setHorizontal(UIAlign.Align.RIGHT);
        mapBbutton.setVertical(UIAlign.Align.BOTTOM);
        mapBbutton.setHeight(button_height);
        mapBbutton.setX(GLES20Util.getWidth_gl());
        mapBbutton.setY(0);
        mapBbutton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                if(mode == Mode.non) {
                    StackLoadingTransition slt = StackLoadingTransition.getInstance();
                    slt.initStackLoadingTransition();
                    GameManager.transition = slt;
                    GameManager.isTransition = true;
                }else{
                    mode = Mode.non;
                    mapBbutton.setText("マップへ");
                }

            }
        });

        shopList = new List(0,0,shop_list_width,shop_list_height);

        shopButton = new Button(0,0,1,1,"商店");
        shopButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        shopButton.useAspect(true);
        shopButton.setCriteria(UI.Criteria.Height);
        shopButton.setHorizontal(UIAlign.Align.LEFT);
        shopButton.setVertical(UIAlign.Align.TOP);
        shopButton.setHeight(button_height);
        shopButton.setX(0);
        shopButton.setY(GLES20Util.getHeight_gl());
        shopButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.shop;
                mapBbutton.setText("戻る");
            }
        });

        hotelButton = new Button(0,0,1,1,"宿屋");
        hotelButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        hotelButton.useAspect(true);
        hotelButton.setCriteria(UI.Criteria.Height);
        hotelButton.setHorizontal(UIAlign.Align.LEFT);
        hotelButton.setVertical(UIAlign.Align.TOP);
        hotelButton.setHeight(button_height);
        hotelButton.setX(0);
        hotelButton.setY(GLES20Util.getHeight_gl() - shopButton.getHeight());
        hotelButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.hotel;
                mapBbutton.setText("戻る");
            }
        });



    }

    @Override
    public void constract(Object[] args) {
        town = (Town)args[0];
        background = town.getBackground();
        background.useAspect(true);
        background.setHeight(GLES20Util.getHeight_gl());
        background.setX(GLES20Util.getWidth_gl()/2f);
        background.setY(GLES20Util.getHeight_gl()/2f);
        townName = new Image(town.getNameImage());
        townName.useAspect(true);
        townName.setHeight(town_name_height);
        townNameBack = new Image(Constant.getBitmap(Constant.BITMAP.system_message_box));
        townNameBack.useAspect(false);
        townNameBack.setHeight(town_name_height+town_name_frame_padding_vertical);
        townNameBack.setWidth(townName.getWidth() + town_name_frame_padding_horizon);
        townNameBack.setHorizontal(UIAlign.Align.LEFT);
        townNameBack.setVertical(UIAlign.Align.TOP);
        townNameBack.setY(GLES20Util.getHeight_gl());

        townName.setX(townNameBack.getX() + townNameBack.getWidth()/2f);
        townName.setY(townNameBack.getY() - townNameBack.getHeight()/2f);

        for(int n = 0;n < town.getShopItem().size();n++){
            ShopItem si = town.getShopItem().get(n);
            si.setHeight(shopList.getContentHeight() - shopList.getTextPadding());
            si.setLeft(shopList.getContentLeft());
            si.setRight(shopList.getContentRight());
            Button btn = new Button(0,0,1,1,si);
            btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            btn.setPadding(padding);
            btn.setCriteria(UI.Criteria.Height);
            shopList.addItem(btn);
        }
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        mapBbutton.proc();
        if(mode == Mode.non){
            shopButton.proc();
            hotelButton.proc();
        }else if(mode == Mode.shop){
            shopList.proc();
        }
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        townNameBack.draw(offsetX,offsetY);
        townName.draw(offsetX,offsetY);
        mapBbutton.draw(offsetX,offsetY);
        if(mode == Mode.non){
            shopButton.draw(offsetX,offsetY);
            hotelButton.draw(offsetX,offsetY);
        }else if(mode == Mode.shop){
            shopList.draw(offsetX,offsetY);
        }
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        Touch touch = Input.getTouchArray()[0];
        mapBbutton.touch(touch);
        if(mode == Mode.non){
            shopButton.touch(touch);
            hotelButton.touch(touch);
        }else if(mode == Mode.shop){
            shopList.touch(touch);
        }
    }

    @Override
    public void death() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void freeze() {
        freeze = true;
    }

    @Override
    public void unFreeze() {
        freeze = false;
    }
}
