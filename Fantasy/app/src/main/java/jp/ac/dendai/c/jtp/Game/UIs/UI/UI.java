package jp.ac.dendai.c.jtp.Game.UIs.UI;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;

/**
 * Created by Goto on 2016/09/06.
 */
public interface UI {
    public enum Criteria{
        Height,
        Width,
        NON

    }
    public final static int UI_LEFT     = 0;
    public final static int UI_CENTOR   = 1;
    public final static int UI_RIGHT    = 2;
    public final static int UI_TOP      = 3;
    public final static int UI_BOTTOM   = 4;
    boolean touch(Touch touch);
    void proc();
    void draw(float offset_x,float offset_y);
}
