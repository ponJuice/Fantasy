package jp.ac.dendai.c.jtp.Game.UIs.UI.List;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;

/**
 * Created by wark on 2016/10/17.
 */

public abstract class ListItemButtonListener implements ButtonListener {
    protected Button btn;
    public ListItemButtonListener(Button btn){
        this.btn = btn;
    }
}
