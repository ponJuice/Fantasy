package jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PlayerState.State;

import java.util.ArrayList;

import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleAction;
import jp.ac.dendai.c.jtp.Game.BattleSystem.BattleState.State.PiplineState.State.PipelineStatePattern;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.Player;
import jp.ac.dendai.c.jtp.Game.BattleSystem.Skill.Skill;
import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.Item.Item;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.List;
import jp.ac.dendai.c.jtp.Game.UIs.UI.List.PlayerActionList;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by wark on 2016/10/17.
 */

public class PlayerItemSelectState extends APlayerState {
    protected Player player;
    protected static final float padding = 0.1f;
    public PlayerItemSelectState(PlayerStatePattern psp,Player player) {
        super(psp);
        this.player = player;
        list = new PlayerActionList(GLES20Util.getWidth_gl()/2f,0, Constant.battle_list_width,Constant.battle_list_height);
        list.setContentWidth(Constant.battle_list_content_width);
        list.setContentHeight(Constant.battle_list_content_height);
        list.setItemPadding(Constant.battle_list_item_padding);
        list.setTextPaddint(Constant.battle_list_text_padding);
        list.setHorizontal(UIAlign.Align.CENTOR);
        list.setVertical(UIAlign.Align.CENTOR);
        list.setScrollable(true);
        Button btn;
        ArrayList<Item> sks = player.getItemList();
        for(int n = 0;n < sks.size();n++){
            sks.get(n).setHeight(Constant.list_content_height-0.03f);
            sks.get(n).setDrawNumber(true);
            btn = new Button(0,0,1,1,sks.get(n));
            btn.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
            btn.setPadding(padding);
            btn.setCriteria(UI.Criteria.Height);
            btn.setButtonListener(new ItemListItemListener(sks.get(n)));
            list.addItem(btn);
        }
    }

    @Override
    public void clean() {
        list.stateBack();
    }

    @Override
    public void proc() {

    }

    @Override
    public void draw(float offsetX, float offsetY) {

    }

    @Override
    public void init(PlayerActionList list) {
        list.addListEnd(this.list);
        this.list.init();
        ArrayList<Button> temp = this.list.getList();
        for(int n = 0;n < temp.size();n++){
            if(player.getMp() < player.getSkillList().get(n).calcMpValue(player)){
                temp.get(n).setEnabled(false);
            }else{
                temp.get(n).setEnabled(true);
            }
        }
    }

    @Override
    public PlayerActionList getList() {
        return list;
    }
    protected class ItemListItemListener implements ButtonListener {
        protected Item item;
        public ItemListItemListener(Item item){
            this.item = item;
        }
        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            psp.getPlayerState().getBattleState().getBattleManager().getPlayer().getBattleAction().item = this.item;
            psp.getPlayerState().getBattleState().getBattleManager().getPlayer().getBattleAction().type = BattleAction.ActionType.Item;
            psp.getList().setDrawable(false);
            psp.getList().setTouchable(false);
            psp.endState();
            //psp.changeState(psp.getPlayerEnemySelectState());
        }
    }
}
