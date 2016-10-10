package jp.ac.dendai.c.jtp.Game.UIs.UI.Button;

import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;

/**
 * Created by Goto on 2016/09/16.
 */
public class CircleButton implements UI {
    protected float radius;
    @Override
    public boolean touch(Touch touch) {
        return false;
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offset_x,float offset_y) {

    }
}
