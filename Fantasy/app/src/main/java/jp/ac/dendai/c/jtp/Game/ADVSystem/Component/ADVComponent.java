package jp.ac.dendai.c.jtp.Game.ADVSystem.Component;

import jp.ac.dendai.c.jtp.Game.ADVSystem.Event.Event;

/**
 * Created by テツヤ on 2016/10/06.
 */
public abstract class ADVComponent {
    protected ADVComponent next;
    protected boolean isInit = false;

    /**
     * 描画を行う
     */
    public abstract void draw(float offset_x,float offset_y);

    /**
     * 処理を行う
     * @param event　呼び出し元のイベントクラス
     * @return　次のコンポーネント
     */
    public abstract ADVComponent proc(Event event);

    /**
     * 初期化
     */
    public abstract void init(Event event);

    public void setNext(ADVComponent comp){
        next = comp;
    }
}
