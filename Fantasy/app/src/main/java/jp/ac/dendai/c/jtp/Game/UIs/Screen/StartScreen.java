package jp.ac.dendai.c.jtp.Game.UIs.Screen;

import javax.microedition.khronos.opengles.GL;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.Button;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Button.ButtonListener;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UI;
import jp.ac.dendai.c.jtp.Game.UIs.UI.UIAlign;
import jp.ac.dendai.c.jtp.TouchUtil.Input;
import jp.ac.dendai.c.jtp.TouchUtil.Touch;
import jp.ac.dendai.c.jtp.openglesutil.Util.ImageReader;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/10/21.
 */

public class StartScreen implements Screenable {
    protected boolean freeze = true;
    protected String background_file_name = "startScreen.png";
    protected float button_height = 0.15f;
    protected float button_text_padding = 0.05f;
    protected float button_y = GLES20Util.getHeight_gl()/2f - 0.3f;
    protected float button_space = 0.1f;
    protected Image background;
    protected Button continueButton;
    protected Button beginningButton;

    public StartScreen(){
        background = new Image(ImageReader.readImageToAssets(Constant.image_file_directory + background_file_name));
        background.useAspect(true);
        background.setHorizontal(UIAlign.Align.LEFT);
        background.setVertical(UIAlign.Align.BOTTOM);
        background.setWidth(GLES20Util.getWidth_gl());
        background.setHeight(GLES20Util.getHeight_gl());

        continueButton = new Button(0,0,1,1,"続きから");
        continueButton.useAspect(true);
        continueButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        continueButton.setHorizontal(UIAlign.Align.LEFT);
        continueButton.setCriteria(UI.Criteria.Height);
        continueButton.setPadding(button_text_padding);
        continueButton.setHeight(button_height);

        beginningButton = new Button(0,0,1,1,"始めから");
        beginningButton.useAspect(true);
        beginningButton.setBitmap(Constant.getBitmap(Constant.BITMAP.system_button));
        beginningButton.setHorizontal(UIAlign.Align.RIGHT);
        beginningButton.setCriteria(UI.Criteria.Height);
        beginningButton.setPadding(button_text_padding);
        beginningButton.setHeight(button_height);

        continueButton.setY(button_y);
        continueButton.setX(GLES20Util.getWidth_gl()/2f + button_space);

        beginningButton.setY(button_y);
        beginningButton.setX(GLES20Util.getWidth_gl()/2f - button_space);
    }

    @Override
    public void constract(Object[] args) {
    }

    @Override
    public void Proc() {
        if(freeze)
            return;
        continueButton.proc();
        beginningButton.proc();
    }

    @Override
    public void Draw(float offsetX, float offsetY) {
        background.draw(offsetX,offsetY);
        continueButton.draw(offsetX,offsetY);
        beginningButton.draw(offsetX,offsetY);
    }

    @Override
    public void Touch() {
        if(freeze)
            return;
        Touch touch = Input.getTouchArray()[0];
        continueButton.touch(touch);
        beginningButton.touch(touch);
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

    protected class beginButtonListener implements ButtonListener{

        @Override
        public void touchDown(Button button) {

        }

        @Override
        public void touchHover(Button button) {

        }

        @Override
        public void touchUp(Button button) {
            GameManager.getPlayerData().initBegin();
        }
    }
}
