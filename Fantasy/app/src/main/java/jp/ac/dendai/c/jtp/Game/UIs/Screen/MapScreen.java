package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.MessageBox;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.EventManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.PlayerState;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.PlayerStatas;
import jp.ac.dendai.c.jtp.Game.GameUI.QuestionBox;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.MapSystem.CursorAnimator;
import jp.ac.dendai.c.jtp.Game.MapSystem.Node;
import jp.ac.dendai.c.jtp.Game.MapSystem.ShopItem;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.BattleScreen.BattleScreen;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.padding;

/**
 * Created by wark on 2016/10/20.
 */

public class MapScreen implements Screenable{
    protected enum Mode{
        moveTownEffect,
        arriveTown,
        arriveEncount,
        returnScreen,
        non,
        menu,
        item_select
    }
    protected static float background_offsetX = -108f/126f;
    protected static float background_offsetY = 102f/126f;
    protected FlagManager.ScreenType screenType = FlagManager.ScreenType.map;
    protected PlayerData playerData;
    protected QuestionBox battleQuestion;
    protected QuestionBox inTownQuestion;
    protected float cursorAnimTime = 0.5f;
    protected Mode mode = Mode.non;
    protected int encount = 0;
    protected float cursorHeight = 0.1f;
    protected Image cursor;
    protected float height = 512f/126f;
    protected Image background;
    protected TownIcon[] townIcons;
    protected ArrayList<Node> nodes;
    protected Button menuButton;
    protected boolean isMenu = false;
    protected Node toTown;
    protected CursorAnimator cursorAnimator;
    protected boolean freeze = true;
    protected Item selectItem;
    protected MenuDialog md;

    public MapScreen(){
        playerData = GameManager.getPlayerData();

        background = new Image();
        menuButton = new Button(0,0,1f,1f,"アイテム");
        menuButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        menuButton.useAspect(true);
        menuButton.setCriteria(UI.Criteria.Height);
        menuButton.setPadding(0.05f);
        menuButton.setHeight(0.1f);
        menuButton.setHorizontal(UIAlign.Align.LEFT);
        menuButton.setVertical(UIAlign.Align.TOP);
        menuButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.menu;
            }
        });

        cursor = new Image();
        cursor.setImage(Constant.getBitmap(Constant.BITMAP.system_cursor));
        cursor.useAspect(true);
        cursor.setVertical(UIAlign.Align.BOTTOM);
        cursor.setHeight(cursorHeight);
        cursor.setX(GameManager.getPlayerData().getTown().getX());
        cursor.setY(GameManager.getPlayerData().getTown().getY());

        cursorAnimator = new CursorAnimator(cursor);

        battleQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"進みますか？","進む","戻る");
        battleQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
        battleQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
        battleQuestion.setYesButtonListener(new BattleYesButtonListener());
        battleQuestion.setNoButtonListener(new BattleNoButtonListener());
        inTownQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_message_box),"入りますか？","はい","いいえ");
        inTownQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
        inTownQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
        inTownQuestion.setNoButtonListener(new InTownNoListener());
        inTownQuestion.setYesButtonListener(new InTownYesListener());

        md = new MenuDialog();
    }

    @Override
    public void constract(Object[] args) {
        background.setImage((Bitmap)args[0]);
        background.useAspect(true);
        background.setHeight(height);
        background.setHorizontal(UIAlign.Align.LEFT);
        background.setVertical(UIAlign.Align.TOP);
        background.setX(background_offsetX);
        background.setY(background_offsetY + GLES20Util.getHeight_gl());
        townIcons = createTownIconArray(GameManager.getDataBase().getTownList().toArray(new Town[0]));
        nodes = GameManager.getDataBase().getNodeList();
        cursorAnimator.reset(GameManager.getPlayerData().getTown().getX(),GameManager.getPlayerData().getTown().getY());
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        if(mode == Mode.moveTownEffect){
            if(cursorAnimator.proc()){
                encount++;
                if(encount <= 0){
                    mode = Mode.arriveTown;
                    inTownQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
                    inTownQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
                    inTownQuestion.startFadeInAnimation();
                    encount = 0;
                }else if(encount >= (toTown.getEncount()+1)){
                    //街に着いた
                    playerData.setTown(toTown.getOtherTown(playerData.getTown()));
                    mode = Mode.arriveTown;
                    inTownQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
                    inTownQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
                    inTownQuestion.startFadeInAnimation();
                    encount = 0;
                }else{
                    //エンカウント
                    mode = Mode.arriveEncount;
                    battleQuestion.setX(GLES20Util.getWidth_gl()/2f + GLES20Util.getCameraPosX());
                    battleQuestion.setY(GLES20Util.getHeight_gl()/2f + GLES20Util.getCameraPosY());
                    battleQuestion.startFadeInAnimation();
                    GameManager.stack.push(this);
                    GameManager.args = new Object[3];
                    GameManager.args[0] = toTown.getBackFile();
                    GameManager.args[1] = 0;
                    GameManager.args[2] = toTown.getRank();
                    LoadingTransition lt = LoadingTransition.getInstance();
                    lt.initTransition(BattleScreen.class);
                    GameManager.transition = lt;
                    GameManager.isTransition = true;

                    mode = Mode.returnScreen;
                }
            }
        }else {
            if(mode == Mode.arriveTown){
                if(inTownQuestion.proc()){
                    if(!inTownQuestion.getEnabled())
                        mode = Mode.non;
                }
            }else if(mode == Mode.arriveEncount){
                //トランジョン表示
            }else if(mode ==Mode.returnScreen){
                battleQuestion.proc();
            }else if(mode == Mode.menu){
                md.proc();
            }else if(mode == Mode.non) {
                menuButton.setX(GLES20Util.getCameraPosX());
                menuButton.setY(GLES20Util.getCameraPosY() + GLES20Util.getHeight_gl());
                menuButton.proc();
            }else if(mode == Mode.item_select){
                selectItem.influence(playerData);
                mode = Mode.menu;
            }
            if (isMenu)
                return;
        }
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        for(int n = 0;n < nodes.size();n++){
            nodes.get(n).drawNode(offsetX,offsetY,1);
        }
        for(int n = 0;n < townIcons.length;n++){
            townIcons[n].draw(offsetX,offsetY);
        }
        //cursor.draw(offsetX,offsetY);
        cursorAnimator.draw(offsetX,offsetY);
        if(mode == Mode.non) {
            menuButton.draw(offsetX, offsetY);
        }
        if(mode == Mode.arriveTown){
            inTownQuestion.draw(offsetX,offsetY);
        }else if(mode == Mode.returnScreen){
            battleQuestion.draw(offsetX,offsetY);
        }else if(mode == Mode.menu){
            md.draw(offsetX,offsetY);
        }
    }

    @Override
    public void init() {
        md.init();
        FlagManager.setFlagValue(FlagManager.FlagType.global,1,screenType.getInt());
        EventManager eventManager = GameManager.getDataBase().getEventManager();
        String str = eventManager.checkEvent();
        if(str != null){
            eventManager.startEvent(this,str);
            LoadingTransition.getInstance().useFadeIn(false);
        }else{
            GameManager.startBGM(R.raw.map,true);
        }
    }

    @Override
    public void Touch() {
        if(freeze)
            return;

        if(mode == Mode.moveTownEffect)
            return;
        if(mode == Mode.non) {
            menuButton.touch(Input.getTouchArray()[0]);
            for (int n = 0; n < townIcons.length; n++) {
                townIcons[n].touch(Input.getTouchArray()[0]);
                townIcons[n].proc();
            }
        }

        if(mode == Mode.menu) {
            md.touch();
            return;
        }

        if(mode == Mode.arriveTown){
            inTownQuestion.touch(Input.getTouchArray()[0]);
        }else if(mode == Mode.arriveEncount) {

        }else if(mode == Mode.returnScreen){
            battleQuestion.touch(Input.getTouchArray()[0]);
        }else {
/*
            float camX = GLES20Util.getCameraPosX() + Input.getTouchArray()[0].getDelta(Touch.Pos_Flag.X) / 1000f;
            float camY = GLES20Util.getCameraPosY() - Input.getTouchArray()[0].getDelta(Touch.Pos_Flag.Y) / 1000f;
            if (camX < 0)
                camX = 0;
            else if (camX + GLES20Util.getAspect() > background.getWidth())
                camX = background.getWidth() - GLES20Util.getAspect();
            if (camY < 0)
                camY = 0;
            else if (camY + 1f > background.getHeight())
                camY = background.getHeight() - 1f;

            GLES20Util.setCameraPos(camX, camY);*/
        }

        Input.getTouchArray()[0].resetDelta();
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

    public TownIcon[] createTownIconArray(Town[] towns){
        TownIcon[] townIcon = new TownIcon[towns.length];
        for(int n = 0;n < townIcon.length;n++){
            townIcon[n] = new TownIcon(towns[n]);
        }
        return townIcon;
    }

    protected class InTownYesListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {mode = Mode.non;
            if(GameManager.getPlayerData().getTown().isDungeon()){
                GameManager.stack.push(GameManager.nowScreen);
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(DungeonScreen.class);
                GameManager.args = new Object[2];
                GameManager.args[0] = GameManager.getPlayerData().getTown().getBackground();
                GameManager.args[1] = GameManager.getPlayerData().getTown().getName();
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }else {
                GameManager.stack.push(GameManager.nowScreen);
                GameManager.args = new Object[1];
                GameManager.args[0] = GameManager.getPlayerData().getTown();
                LoadingTransition lt = LoadingTransition.getInstance();
                lt.initTransition(TownScreen.class);
                GameManager.transition = lt;
                GameManager.isTransition = true;
            }

        }
    }

    protected class InTownNoListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = Mode.non;
            inTownQuestion.startFadeOutAnimation();
        }
    }

    protected class BattleNoButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = Mode.moveTownEffect;
            cursorAnimator.reset();
            cursorAnimator.start();
            cursorAnimator.setAnimation(
                    toTown.getEncountPosX(GameManager.getPlayerData().getTown(),encount),
                    toTown.getEncountPosY(GameManager.getPlayerData().getTown(),encount),
                    GameManager.getPlayerData().getTown().getX(),
                    GameManager.getPlayerData().getTown().getY(),
                    cursorAnimTime);
            encount = -1;
        }
    }

    protected class BattleYesButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            mode = Mode.moveTownEffect;
            cursorAnimator.reset();
            cursorAnimator.start();
            cursorAnimator.setAnimation(
                    toTown.getEncountPosX(GameManager.getPlayerData().getTown(),encount),
                    toTown.getEncountPosY(GameManager.getPlayerData().getTown(),encount),
                    toTown.getEncountPosX(GameManager.getPlayerData().getTown(),encount+1),
                    toTown.getEncountPosY(GameManager.getPlayerData().getTown(),encount+1),
                    cursorAnimTime);
        }
    }

    protected class TownIcon implements ButtonListener,UI{
        public Town town;
        public TownIcon(Town town){
            this.town = town;
            this.town.setButtonListener(this);
        }

        @Override
        public boolean touch(Touch touch) {
            return town.touch(touch);
        }

        @Override
        public void proc() {
            town.proc();
        }

        @Override
        public void draw(float offsetX, float offsetY){
            town.drawIcon(offsetX,offsetY);
        }

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {
        }

        @Override
        public void touchUp(Button button) {
            if(playerData.getTown() == town){
                //同じ場所をクリックしたら町に入る
                encount = -1;
                mode = Mode.arriveTown;
                inTownQuestion.startFadeInAnimation();
                return;
            }
            Node node = GameManager.getPlayerData().getTown().searchNode(town);
            if(node == null){
                toTown = null;
                Log.d("TownIcon",playerData.getTown().getName()+"から"+town.getName()+"へは行けません");
            }else{
                toTown = node;
                encount = 0;
                mode = Mode.moveTownEffect;

                cursorAnimator.reset();
                cursorAnimator.start();
                cursorAnimator.setAnimation(
                        GameManager.getPlayerData().getTown().getX(),
                        GameManager.getPlayerData().getTown().getY(),
                        toTown.getEncountPosX(GameManager.getPlayerData().getTown(),encount+1),
                        toTown.getEncountPosY(GameManager.getPlayerData().getTown(),encount+1),
                        cursorAnimTime);
                Log.d("TownIcon","発見しました");
            }
        }
    }

    protected class MenuDialog{
        protected float button_height = 0.2f;
        protected float shop_list_width = 0.5f;
        protected float shop_list_height = GLES20Util.getHeight_gl();
        protected float itemList_y = 0;
        protected float itemList_x = 0;
        protected Button backButton;
        protected List itemList;
        protected PlayerStatas playerStatas;
        public MenuDialog(){
            backButton = new Button(0,0,1,1,"戻る");
            init();
        }
        public void init(){
            playerStatas = new PlayerStatas();
            this.itemList = new List(0,button_height,shop_list_width,shop_list_height);
            this.itemList.setContentHeight(0.1f);
            this.itemList.setTextPaddint(0.03f);
            this.itemList.setHorizontal(UIAlign.Align.LEFT);
            this.itemList.setVertical(UIAlign.Align.BOTTOM);
            this.itemList.setX(itemList_x);
            this.itemList.setY(itemList_y);
            PlayerData pd = GameManager.getPlayerData();
            for(Item item : pd.getItemList()){
                if(!item.isUseable())
                    continue;
                item.setPadding(0.05f);
                item.setHeight(itemList.getContentHeight() - 0.01f);
                item.setWidth(itemList_x,itemList.getContentWidth());
                Button btn = new Button(0,0,1,1,item);
                btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                btn.setPadding(0.01f);
                btn.setCriteria(UI.Criteria.Height);
                btn.setButtonListener(new ItemListListener(item,btn));
                itemList.addItem(btn);
            }
            backButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            backButton.useAspect(true);
            backButton.setPadding(0.05f);
            backButton.setCriteria(UI.Criteria.Height);
            backButton.setHorizontal(UIAlign.Align.RIGHT);
            backButton.setVertical(UIAlign.Align.BOTTOM);
            backButton.setHeight(button_height);
            backButton.setX(GLES20Util.getWidth_gl());
            backButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    mode = Mode.non;
                }
            });
        }
        public void touch(){
            Touch touch = Input.getTouchArray()[0];
            itemList.touch(touch);
            backButton.touch(touch);
        }
        public void proc(){
            itemList.proc();
            backButton.proc();
            playerStatas.proc();
        }
        public void draw(float offsetX,float offsetY){
            itemList.draw(offsetX,offsetY);
            playerStatas.draw(offsetX,offsetY);
            backButton.draw(offsetX,offsetY);
        }
        public class ItemListListener implements ButtonListener{
            protected Item item;
            protected Button btn;
            public ItemListListener(Item _item,Button btn){
                item = _item;
                this.btn = btn;
            }
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                selectItem = item;
                if(selectItem.getNumber() - 1 <= 0)
                    itemList.removeItem(btn);
                    mode = Mode.item_select;
            }
        }
    }
}
