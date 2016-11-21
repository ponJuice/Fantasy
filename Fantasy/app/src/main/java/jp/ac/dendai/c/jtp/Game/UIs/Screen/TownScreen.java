package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import java.util.Collection;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Component.MessageBox;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.EventManager;
import jp.ac.dendai.c.jtp.Game.ADVSystem.Flag.FlagManager;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.DataManager;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.GameUI.QuestionBox;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.MapSystem.ShopItem;
import jp.ac.dendai.c.jtp.Game.MapSystem.Town;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition.StackLoadingTransition;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.NumberText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.Text;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.TextBox.TextBox;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StaticText;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.Mode.message;
import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.Mode.non;
import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.Mode.shop;
import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.Mode.shop_buy;
import static jp.ac.dendai.c.jtp.Game.UIs.Screen.TownScreen.Mode.shop_sell;

/**
 * Created by テツヤ on 2016/09/13.
 */
public class TownScreen implements Screenable {
    protected enum Mode{
        non,
        shop,
        hotel,
        shop_buy,
        shop_sell,
        message
    }
    protected enum ShopMode{
        select,
        non
    }
    protected static FlagManager.ScreenType screenType = FlagManager.ScreenType.town;
    protected static final float padding = 0.1f;
    protected Mode mode = non;
    protected ShopMode shopMode = ShopMode.non;
    protected NumberText money;
    protected StaticText moneyText;
    protected float money_text_x = 0.02f;
    protected float monety_offset_x = 0.02f;
    protected float money_height = 0.15f;
    protected float shop_list_width = 0.5f;
    protected float button_padding = 0.05f;
    protected float button_height = 0.2f;
    protected float button_width = 0.5f;
    protected float button_top = 0.05f;
    protected float map_button_height = 0.15f;
    protected float town_name_height = 0.1f;
    protected float town_name_frame_padding_horizon = 0.2f;
    protected float town_name_frame_padding_vertical = 0.05f;
    protected float town_name_back_top = 0.01f;
    protected float shop_list_height= GLES20Util.getHeight_gl() - town_name_back_top - town_name_height - button_height;
    protected Town town;
    protected Image townName;
    protected Image townNameBack;
    protected Image background;
    protected Button mapBbutton;
    protected Button hotelButton;
    protected Button shopButton;
    protected Button sellButton;
    protected Button buyButton;
    protected Button saveButton;
    protected QuestionBox saveQuestion;
    protected TextBox textBox;
    protected Image image;
    protected ItemShopDialog isd;
    protected HotelDialog hd;
    protected PlayerData pd;
    protected boolean freeze;
    protected boolean saveQuestionFlag = false;
    public TownScreen(){
        pd = GameManager.getPlayerData();
        mapBbutton = new Button(0,0,1,1,"マップへ");
        mapBbutton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        mapBbutton.useAspect(false);
        mapBbutton.setCriteria(UI.Criteria.Height);
        mapBbutton.setHorizontal(UIAlign.Align.LEFT);
        mapBbutton.setVertical(UIAlign.Align.BOTTOM);
        mapBbutton.setPadding(button_padding);
        mapBbutton.setHeight(button_height);
        mapBbutton.setWidth(button_width);
        mapBbutton.setX(0);
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
                if(mode == non) {
                    /*StackLoadingTransition slt = StackLoadingTransition.getInstance();
                    slt.initStackLoadingTransition();
                    GameManager.transition = slt;
                    GameManager.isTransition = true;*/
                    LoadingTransition lt = LoadingTransition.getInstance();
                    lt.initTransition(MapScreen.class);
                    GameManager.transition = lt;
                    GameManager.isTransition = true;
                    GameManager.args = new Object[1];
                    GameManager.args[0] = ImageReader.readImageToAssets(Constant.mapImageFile);
                    GameManager.getPlayerData().setTown(town);

                }else if(mode == Mode.shop_buy) {
                    mode = shop;
                }else if(mode == Mode.shop_sell){
                    mode = shop;
                }else{
                    mode = non;
                    mapBbutton.setText("マップへ");
                }

            }
        });

        shopButton = new Button(0,0,1,1,"商店");
        shopButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        shopButton.useAspect(false);
        shopButton.setCriteria(UI.Criteria.Height);
        shopButton.setPadding(button_padding);
        shopButton.setHorizontal(UIAlign.Align.LEFT);
        shopButton.setVertical(UIAlign.Align.TOP);
        shopButton.setHeight(button_height);
        shopButton.setWidth(button_width);
        shopButton.setX(0);
        shopButton.setY(GLES20Util.getHeight_gl() - button_top);
        shopButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = shop;
                mapBbutton.setText("戻る");
            }
        });

        hotelButton = new Button(0,0,1,1,"宿屋");
        hotelButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        hotelButton.useAspect(false);
        hotelButton.setCriteria(UI.Criteria.Height);
        hotelButton.setHorizontal(UIAlign.Align.LEFT);
        hotelButton.setVertical(UIAlign.Align.TOP);
        hotelButton.setPadding(button_padding);
        hotelButton.setHeight(button_height);
        hotelButton.setWidth(button_width);
        hotelButton.setX(0);
        hotelButton.setY(shopButton.getY() - shopButton.getHeight());
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

        saveButton = new Button(0,0,1,1,"セーブ");
        saveButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        saveButton.useAspect(false);
        saveButton.setCriteria(UI.Criteria.Height);
        saveButton.setHorizontal(UIAlign.Align.LEFT);
        saveButton.setVertical(UIAlign.Align.TOP);
        saveButton.setPadding(button_padding);
        saveButton.setHeight(button_height);
        saveButton.setWidth(button_width);
        saveButton.setX(0);
        saveButton.setY(hotelButton.getY() - hotelButton.getHeight());
        saveButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                saveQuestion.startFadeInAnimation();
                saveQuestionFlag = true;
            }
        });

        saveQuestion = new QuestionBox(Constant.getBitmap(Constant.BITMAP.system_button),"セーブしますか？","はい","いいえ");
        saveQuestion.setYesButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                DataManager.saveData();
                saveQuestionFlag = false;
                mode = message;
            }
        });
        saveQuestion.setNoButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                saveQuestion.startFadeOutAnimation();
                saveQuestionFlag = false;
            }
        });
        saveQuestion.setX(GLES20Util.getWidth_gl()/2f);
        saveQuestion.setY(GLES20Util.getHeight_gl()/2f);
        textBox = new TextBox(GLES20Util.getWidth_gl()/2f,GLES20Util.getHeight_gl()/2f
        ,0.6f,0.4f,"セーブしました");
        textBox.setBitmap(Constant.getBitmap(Constant.BITMAP.system_message_box));

        moneyText = new StaticText("残金",null);
        moneyText.setHeight(money_height /2f);
        moneyText.setHorizontal(UIAlign.Align.LEFT);
        moneyText.setVertical(UIAlign.Align.TOP);
        moneyText.setY(GLES20Util.getHeight_gl());
        moneyText.setX(money_text_x);

        money = new NumberText(Constant.fontName);
        money.useAspect(true);
        money.setHeight(money_height);
        money.setNumber(GameManager.getPlayerData().getMoney());
        money.setHorizontal(UIAlign.Align.LEFT);
        money.setVertical(UIAlign.Align.TOP);
        money.setY(GLES20Util.getHeight_gl());
        money.setX(moneyText.getWidth()+ monety_offset_x);

        sellButton = new Button(0,0,1,1,"売る");
        sellButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        sellButton.useAspect(false);
        sellButton.setCriteria(UI.Criteria.Height);
        sellButton.setHorizontal(UIAlign.Align.LEFT);
        sellButton.setVertical(UIAlign.Align.TOP);
        sellButton.setPadding(button_padding);
        sellButton.setHeight(button_height);
        sellButton.setWidth(button_width);
        sellButton.setX(0);
        sellButton.setY(shopButton.getY());
        sellButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.shop_sell;
            }
        });

        buyButton = new Button(0,0,1,1,"買う");
        buyButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        buyButton.useAspect(false);
        buyButton.setCriteria(UI.Criteria.Height);
        buyButton.setHorizontal(UIAlign.Align.LEFT);
        buyButton.setVertical(UIAlign.Align.TOP);
        buyButton.setPadding(button_padding);
        buyButton.setHeight(button_height);
        buyButton.setWidth(button_width);
        buyButton.setX(0);
        buyButton.setY(sellButton.getY() - sellButton.getHeight());
        buyButton.setButtonListener(new ButtonListener() {
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                mode = Mode.shop_buy;
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
        townNameBack.setVertical(UIAlign.Align.TOP);
        townNameBack.setY(GLES20Util.getHeight_gl()- town_name_back_top);
        townNameBack.setX(GLES20Util.getWidth_gl()/2f);

        townName.setX(townNameBack.getX());
        townName.setY(townNameBack.getY() - townNameBack.getHeight()/2f);

        isd = new ItemShopDialog(town);
        hd = new HotelDialog(town);
        hd.setX(GLES20Util.getWidth_gl()/2f);
        hd.setY(GLES20Util.getHeight_gl()/2f);
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        if(saveQuestionFlag){
            if(saveQuestion.proc()){
            }
            return;
        }
        if(mode == non){
            mapBbutton.proc();
            shopButton.proc();
            hotelButton.proc();
            saveButton.proc();
        }else if(mode == shop){
            mapBbutton.proc();
            buyButton.proc();
            sellButton.proc();
            //isd.proc();
        }else if(mode == Mode.hotel){
            hd.proc();
        }else if(mode == Mode.shop_buy){
            if(shopMode == ShopMode.non) {
                mapBbutton.proc();
            }
            isd.proc();
        }else if(mode == Mode.shop_sell){
            if(shopMode == ShopMode.non) {
                mapBbutton.proc();
            }
            isd.proc();
        }
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        townNameBack.draw(offsetX,offsetY);
        townName.draw(offsetX,offsetY);
        if(mode == non){
            mapBbutton.draw(offsetX,offsetY);
            shopButton.draw(offsetX,offsetY);
            hotelButton.draw(offsetX,offsetY);
            saveButton.draw(offsetX,offsetY);
        }else if(mode == shop){
            mapBbutton.draw(offsetX,offsetY);
            buyButton.draw(offsetX,offsetY);
            sellButton.draw(offsetX,offsetY);
            //isd.draw(offsetX,offsetY);
        }else if(mode == Mode.hotel){
            hd.draw(offsetX,offsetY);
        }else if(mode == Mode.shop_buy){
            if(shopMode == ShopMode.non) {
                mapBbutton.draw(offsetX, offsetY);
            }
            isd.draw(offsetX,offsetY);
        }else if(mode == Mode.shop_sell){
            if(shopMode == ShopMode.non) {
                mapBbutton.draw(offsetX, offsetY);
            }
            isd.draw(offsetX,offsetY);
        }
        if(saveQuestionFlag){
            saveQuestion.draw(offsetX,offsetY);
        }
        if(mode == message){
            textBox.draw(offsetX,offsetY);
        }
    }

    @Override
    public void init() {
        FlagManager.setFlagValue(FlagManager.FlagType.global,1,screenType.getInt());
        EventManager eventManager = GameManager.getDataBase().getEventManager();
        String str = eventManager.checkEvent();
        if(str != null){
            eventManager.startEvent(this,str);
            LoadingTransition.getInstance().useFadeIn(false);
        }
        GameManager.startBGM(R.raw.mati,true);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        Touch touch = Input.getTouchArray()[0];
        if(saveQuestionFlag){
            saveQuestion.touch(touch);
            return;
        }
        if(mode == message){
            if(touch.getTouchID() != -1) {
                mode = non;
                saveQuestionFlag = false;
            }
        }
        if(mode == non){
            mapBbutton.touch(touch);
            shopButton.touch(touch);
            hotelButton.touch(touch);
            saveButton.touch(touch);
        }else if(mode == shop){
            mapBbutton.touch(touch);
            sellButton.touch(touch);
            buyButton.touch(touch);
            //isd.touch(touch);
        }else if(mode == Mode.hotel) {
            hd.touch(touch);
        }else if(mode == Mode.shop_buy){
            if(shopMode == ShopMode.non) {
                mapBbutton.touch(touch);
            }
            isd.touch(touch);
        }else if(mode == Mode.shop_sell){
            if(shopMode == ShopMode.non) {
                mapBbutton.touch(touch);
            }
            isd.touch(touch);
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

    public void refreshRemainingMoney(){
        money.setNumber(pd.getMoney());
    }

    public class ItemShopDialog{
        protected NumberText number;
        protected float cursor_height = 0.1f;
        protected float number_height = 0.3f;
        protected float itemList_y = 0;
        protected float background_x = GLES20Util.getWidth_gl()/3f * 2f - 0.05f;
        protected float background_y = GLES20Util.getHeight_gl()/2f - 0.1f;
        protected float background_height = 0.8f;
        protected float background_width = 1.2f;
        protected ShopItem selectShopItem;
        protected Image background;
        protected NumberText priceText;
        protected float priceText_height = 0.1f;
        protected float priceText_x = GLES20Util.getWidth_gl()/2f;
        protected float priceText_y = GLES20Util.getHeight_gl()/2f;
        protected StaticText priceStaticText;
        protected List itemList;
        protected List sellItemList;
        protected Button okButton,cancelButton;
        protected Button ue,sita;
        protected CursorUpDownBuyButtonListener buyListenrUp,buyListenrDown;
        protected CursorUpDownSellButtonListener sellListenerUp,sellListenerDown;
        public ItemShopDialog(Town town){
            this.itemList = new List(0,button_height,shop_list_width,shop_list_height);
            this.itemList.setContentHeight(0.1f);
            this.itemList.setTextPaddint(0.03f);
            this.itemList.setX(itemList_y);
            for(int n = 0;n < town.getShopItem().size();n++){
                ShopItem si = town.getShopItem().get(n);
                si.setHeight(itemList.getContentHeight() - itemList.getTextPadding());
                si.setLeft(itemList.getContentLeft());
                si.setRight(itemList.getContentRight());
                Button btn = new Button(0,0,1,1,si);
                btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                btn.setPadding(padding);
                btn.setCriteria(UI.Criteria.Height);
                btn.setButtonListener(new ShopItemBuyListener(si));
                si.setIdButton(btn);
                itemList.addItem(btn);
            }
            this.sellItemList = new List(0,button_height,shop_list_width,shop_list_height);
            this.sellItemList.setContentHeight(0.1f);
            this.sellItemList.setTextPaddint(0.03f);
            this.sellItemList.setX(itemList_y);
            Collection<Item> cit = pd.getItemList();
            Item[] sellItems = cit.toArray(new Item[0]);
            for(int n = 0;n < sellItems.length;n++){
                if(!sellItems[n].isSellable())
                    continue;
                ShopItem si = new ShopItem(sellItems[n].getItemTemplate(),1,false); //town.getShopItem().get(n);
                si.setHeight(sellItemList.getContentHeight() - sellItemList.getTextPadding());
                si.setLeft(sellItemList.getContentLeft());
                si.setRight(sellItemList.getContentRight());
                Button btn = new Button(0,0,1,1,si);
                btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
                btn.setPadding(padding);
                btn.setCriteria(UI.Criteria.Height);
                btn.setButtonListener(new ShopItemSellListener(si));
                si.setIdButton(btn);
                sellItemList.addItem(btn);
            }

            okButton = new Button(0,0,1,1,"決定");
            okButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            okButton.useAspect(true);
            okButton.setHorizontal(UIAlign.Align.LEFT);
            okButton.setVertical(UIAlign.Align.BOTTOM);
            okButton.setCriteria(UI.Criteria.Height);
            okButton.setPadding(button_padding);
            okButton.setHeight(map_button_height);
            okButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    if(mode == shop_buy) {
                        pd.addItem(selectShopItem.getItemTemplate(), number.getNumber());
                        pd.subMoney(selectShopItem.getPrice() * number.getNumber());
                        shopMode = ShopMode.non;
                    }else if(mode == shop_sell){
                        pd.subItem(selectShopItem.getItemTemplate(),number.getNumber());
                        pd.addMoney(selectShopItem.getPrice() * number.getNumber());
                        Item it = pd.getItem(selectShopItem.getName());
                        if(it == null)
                            sellItemList.removeItem(selectShopItem.getIdButton());
                        shopMode = ShopMode.non;
                    }
                    refreshRemainingMoney();
                }
            });
            //okButton.setX();
            cancelButton = new Button(0,0,1,1,"キャンセル");
            cancelButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            cancelButton.useAspect(true);
            cancelButton.setHorizontal(UIAlign.Align.LEFT);
            cancelButton.setVertical(UIAlign.Align.BOTTOM);
            cancelButton.setCriteria(UI.Criteria.Height);
            cancelButton.setPadding(button_padding);
            cancelButton.setHeight(map_button_height);
            cancelButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    shopMode = ShopMode.non;
                }
            });

            okButton.setX(itemList.getContentWidth());
            cancelButton.setX(okButton.getX() + okButton.getWidth());

            number = new NumberText(Constant.fontName);
            number.setHeight(number_height);
            number.setX(cancelButton.getX() + cancelButton.getWidth()/2f);//okButton.getX() + (okButton.getWidth() + cancelButton.getWidth())/2f);
            number.setY(GLES20Util.getHeight_gl()/2f);
            number.setNumber(1);

            ue = new Button(0,0,1,1);
            ue.setBitmap(Constant.getBitmap(Constant.BITMAP.system_ue));
            ue.useAspect(true);
            ue.setVertical(UIAlign.Align.BOTTOM);
            ue.setHeight(cursor_height);
            ue.setX(number.getX());
            ue.setY(number.getY()+number.getHeight()/2f);
            //ue.setButtonListener();

            sita = new Button(0,0,1,1);
            sita.setBitmap(Constant.getBitmap(Constant.BITMAP.system_sita));
            sita.useAspect(true);
            sita.setVertical(UIAlign.Align.TOP);
            sita.setHeight(cursor_height);
            sita.setX(number.getX());
            sita.setY(number.getY() - number.getHeight()/2f);
            sita.setButtonListener(new CursorUpDownBuyButtonListener(false,1));

            buyListenrUp = new CursorUpDownBuyButtonListener(true,1);
            buyListenrDown = new CursorUpDownBuyButtonListener(false,1);

            sellListenerUp = new CursorUpDownSellButtonListener(true,1);
            sellListenerDown = new CursorUpDownSellButtonListener(false,1);

            background = new Image(Constant.getBitmap(Constant.BITMAP.system_message_box));
            background.useAspect(false);
            background.setHeight(background_height);
            background.setWidth(background_width);
            background.setX(background_x);
            background.setY(background_y);

            priceText = new NumberText(Constant.fontName);
            priceText.useAspect(true);
            priceText.setHorizontal(UIAlign.Align.LEFT);
            priceText.setHeight(priceText_height);
            priceText.setX(priceText_x);
            priceText.setY(priceText_y);

            priceStaticText = new StaticText("金額：",null);
            priceStaticText.useAspect(true);
            priceStaticText.setHorizontal(UIAlign.Align.RIGHT);
            priceStaticText.setHeight(priceText_height);
            priceStaticText.setX(priceText_x);
            priceStaticText.setY(priceText_y);

        }

        public boolean touch(Touch touch){
            if(shopMode == ShopMode.non) {
                if(mode == Mode.shop_buy) {
                    itemList.touch(touch);
                }else if(mode == Mode.shop_sell){
                    sellItemList.touch(touch);
                }
            }else{
                if (mode == Mode.shop_buy || mode == Mode.shop_sell) {
                    ue.touch(touch);
                    sita.touch(touch);
                    cancelButton.touch(touch);
                    okButton.touch(touch);
                }
            }
            return false;
        }
        public void proc(){
            if(shopMode == ShopMode.non) {
                if(mode == Mode.shop_buy) {
                    itemList.proc();
                }else if(mode == Mode.shop_sell){
                    sellItemList.proc();
                }
            }else{
                ue.proc();
                sita.proc();
                cancelButton.proc();
                okButton.proc();
            }
        }
        public void draw(float offsetX,float offsetY){
            if(mode == Mode.shop_buy) {
                itemList.draw(offsetX, offsetY);
            }else if(mode == Mode.shop_sell){
                sellItemList.draw(offsetX,offsetY);
            }
            moneyText.draw(offsetX,offsetY);
            money.draw(offsetX,offsetY);
            if(shopMode == ShopMode.select){
                background.draw(offsetX,offsetY);
                priceStaticText.draw(offsetX,offsetY);
                priceText.draw(offsetX,offsetY);
                ue.draw(offsetX,offsetY);
                sita.draw(offsetX,offsetY);
                number.draw(offsetX,offsetY);
                okButton.draw(offsetX,offsetY);
                cancelButton.draw(offsetX,offsetY);
            }
        }
        public class CursorUpDownBuyButtonListener implements ButtonListener{
            protected boolean up;
            protected float buffer;
            protected float holdTime;
            public CursorUpDownBuyButtonListener(boolean up, float holdTime){
                this.up = up;
                this.buffer = 0;
                this.holdTime = holdTime;
            }
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {
                if(buffer >= holdTime){
                    if(up)
                        countUp();
                    else
                        countDown();
                }else{
                    buffer += Time.getDeltaTime();
                }
                priceText.setNumber(selectShopItem.getPrice() * number.getNumber());
            }

            @Override
            public void touchUp(Button button) {
                int n = number.getNumber();
                if(up) {
                    countUp();
                }else{
                    countDown();
                }
                priceText.setNumber(selectShopItem.getPrice() * number.getNumber());
                buffer = 0;
            }
            public void countUp(){
                int n = number.getNumber();
                n++;
                n = Math.min(Math.max(1,n),65536);
                number.setNumber(clamp(n));
            }
            public void countDown(){
                int n = number.getNumber();
                n--;
                n = Math.min(Math.max(1,n),65536);
                number.setNumber(clamp(n));
            }
            public int clamp(int n){
                if((pd.getMoney() - selectShopItem.getPrice() * n )< 0){
                    //残金不足
                    return pd.getMoney() / selectShopItem.getPrice();
                }
                return  n;
            }
        }
        public class CursorUpDownSellButtonListener extends CursorUpDownBuyButtonListener{

            public CursorUpDownSellButtonListener(boolean up, float holdTime) {
                super(up, holdTime);
            }
            @Override
            public void touchUp(Button button) {
                int n = number.getNumber();
                if(up) {
                    countUp();
                }else{
                    countDown();
                }
                priceText.setNumber(selectShopItem.getItemTemplate().getSell() * number.getNumber());
                buffer = 0;
            }
            @Override
            public void touchHover(Button button) {
                if(buffer >= holdTime){
                    if(up)
                        countUp();
                    else
                        countDown();
                }else{
                    buffer += Time.getDeltaTime();
                }
                priceText.setNumber(selectShopItem.getPrice() * number.getNumber());
            }
            @Override
            public int clamp(int n){
                Item _item = pd.getItem(selectShopItem.getName());
                if((_item.getNumber() - n) < 0){
                    //所持数不足
                    return _item.getNumber();
                }
                return n;
            }
        }
        public class ShopItemBuyListener implements ButtonListener{
            protected ShopItem shopItem;
            public ShopItemBuyListener(ShopItem shopItem){
                this.shopItem = shopItem;
            }
            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                shopMode = ShopMode.select;
                number.setNumber(0);
                if(pd.getMoney() <= 0){
                    okButton.setEnabled(false);
                }else{
                    okButton.setEnabled(true);
                }
                selectShopItem = shopItem;
                ue.setButtonListener(buyListenrUp);
                sita.setButtonListener(buyListenrDown);
                priceText.setNumber(0);
            }
        }
        public class ShopItemSellListener extends ShopItemBuyListener{
            public ShopItemSellListener(ShopItem shopItem){super(shopItem);}

            @Override
            public void touchDown(Button button) {

            }

            @Override
            public void touchHover(Button button) {

            }

            @Override
            public void touchUp(Button button) {
                shopMode = ShopMode.select;
                number.setNumber(0);
                Item _item = pd.getItem(shopItem.getName());
                if(pd.getItem(shopItem.getName()).getNumber() <= 0){
                    okButton.setEnabled(false);
                }else{
                    okButton.setEnabled(true);
                }
                selectShopItem = shopItem;
                ue.setButtonListener(sellListenerUp);
                sita.setButtonListener(sellListenerDown);
                priceText.setNumber(0);
            }
        }
    }


    public class HotelDialog{
        protected float fade_time = 0.5f;
        protected boolean fadeIn = true;
        protected float timeBuffer;
        protected boolean yes = false;
        protected Town town;
        protected float x,y;
        protected Image image;
        protected Image background;
        protected NumberText hotelValue;
        protected StaticText confirmation;
        protected StaticText hiyou;
        protected float back_offset_y;
        protected float back_height;
        protected float back_width;
        protected float back_top_bottom_padding = 0.05f;
        protected float back_left_right_paddint = 0.1f;
        protected float conf_height = 0.1f;
        protected float conf_x = 0;
        protected float conf_y = 0;
        protected float button_height = 0.1f;
        protected float button_space = 0.05f;
        protected float button_y = -0.2f;
        protected Button okButton,cancelButton;
        public HotelDialog(Town town){
            this.town = town;

            confirmation = new StaticText("泊まりますか?",null);
            confirmation.useAspect(true);
            confirmation.setHeight(conf_height);
            confirmation.setX(conf_x);
            confirmation.setY(conf_y);

            hotelValue = new NumberText(Constant.fontName);
            hotelValue.useAspect(true);
            hotelValue.setHorizontal(UIAlign.Align.LEFT);
            hotelValue.setNumber(town.getHotelPrice());
            hotelValue.setHeight(conf_height);
            hotelValue.setX(conf_x);
            hotelValue.setY(conf_y - confirmation.getHeight()/2f);

            hiyou = new StaticText("費用：",null);
            hiyou.useAspect(true);
            hiyou.setHeight(conf_height);
            hiyou.setHorizontal(UIAlign.Align.RIGHT);
            hiyou.setX(conf_x);
            hiyou.setY(hotelValue.getX());

            image = new Image(Constant.getBitmap(Constant.BITMAP.white));
            image.useAspect(false);
            image.setHeight(GLES20Util.getHeight_gl());
            image.setWidth(GLES20Util.getWidth_gl());
            image.setHorizontal(UIAlign.Align.LEFT);
            image.setVertical(UIAlign.Align.BOTTOM);
            image.setR(0);
            image.setG(255);
            image.setB(0);
            image.setAlpha(0);

            okButton = new Button(0,0,1,1,Constant.getBitmap(Constant.BITMAP.system_yes),"はい");
            okButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            okButton.useAspect(true);
            okButton.setCriteria(UI.Criteria.Height);
            okButton.setHorizontal(UIAlign.Align.RIGHT);
            okButton.setHeight(button_height);
            okButton.setX(-button_space);
            okButton.setY(button_y);
            okButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    yes = true;
                    GameManager.getPlayerData().resetHp();
                    GameManager.getPlayerData().resetMp();
                }
            });

            cancelButton = new Button(0,0,1,1,Constant.getBitmap(Constant.BITMAP.system_no),"いいえ");
            cancelButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            cancelButton.useAspect(true);
            cancelButton.setCriteria(UI.Criteria.Height);
            cancelButton.setHorizontal(UIAlign.Align.LEFT);
            cancelButton.setHeight(button_height);
            cancelButton.setX(button_space);
            cancelButton.setY(button_y);
            cancelButton.setButtonListener(new ButtonListener() {
                @Override
                public void touchDown(Button button) {

                }

                @Override
                public void touchHover(Button button) {

                }

                @Override
                public void touchUp(Button button) {
                    mode = non;
                    mapBbutton.setText("マップへ");
                }
            });

            back_width = cancelButton.getWidth() + okButton.getWidth() + button_space;
            back_width = Math.max(back_width,confirmation.getWidth());
            back_width += back_left_right_paddint * 2f;

            back_height = confirmation.getY() + confirmation.getHeight()/2f + Math.abs(okButton.getY() - okButton.getHeight()/2f);
            back_height += back_top_bottom_padding * 2f;

            back_offset_y = (confirmation.getY() + okButton.getY())/2f;

            background = new Image(Constant.getBitmap(Constant.BITMAP.system_message_box));
            background.useAspect(false);
            background.setHeight(back_height);
            background.setWidth(back_width);
            background.setY(back_offset_y);
        }
        public void setX(float x){
            this.x = x;
            background.setX(this.x);
            confirmation.setX(this.x + conf_x);
            hotelValue.setX(confirmation.getX());
            hiyou.setX(this.x + conf_x);
            okButton.setX(this.x - button_space);
            cancelButton.setX(this.x + button_space);
        }
        public void setY(float y){
            this.y = y;
            background.setY(this.y + back_offset_y);
            confirmation.setY(this.y + conf_y);
            hotelValue.setY(confirmation.getY() - confirmation.getHeight()/2f - hotelValue.getHeight()/2f);
            hiyou.setY(hotelValue.getY());
            okButton.setY(this.y + button_y);
            cancelButton.setY(this.y + button_y);
        }
        public void draw(float offsetX,float offsetY){
            moneyText.draw(offsetX,offsetY);
            money.draw(offsetX,offsetY);
            background.draw(offsetX,offsetY);
            confirmation.draw(offsetX,offsetY);
            hotelValue.draw(offsetX,offsetY);
            hiyou.draw(offsetX,offsetY);
            okButton.draw(offsetX,offsetY);
            cancelButton.draw(offsetX,offsetY);
            if(yes){
                image.draw(offsetX,offsetY);
            }
        }
        public void proc(){
            if(!yes) {
                if(money.getNumber() - town.getHotelPrice() < 0){
                    okButton.setEnabled(false);
                }else{
                    okButton.setEnabled(true);
                }
                okButton.proc();
                cancelButton.proc();
            }else{
                if(timeBuffer >= fade_time){
                    if(fadeIn){
                        fadeIn = false;
                        pd.subMoney(town.getHotelPrice());
                        refreshRemainingMoney();
                    }else{
                        fadeIn = true;
                        yes = false;
                        image.setAlpha(0);
                    }
                    timeBuffer = 0;
                }else{
                    if(fadeIn){
                        image.setAlpha(timeBuffer/fade_time);
                    }else{
                        image.setAlpha(1f - timeBuffer/fade_time);
                    }
                    timeBuffer += Time.getDeltaTime();
                }
            }
        }
        public boolean touch(Touch touch){
            if(!yes) {
                okButton.touch(touch);
                cancelButton.touch(touch);
            }else{

            }
            return true;
        }
    }
}
