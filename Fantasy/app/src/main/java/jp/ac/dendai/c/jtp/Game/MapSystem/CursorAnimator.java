package jp.ac.dendai.c.jtp.Game.MapSystem;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Util.Time;

/**
 * Created by Goto on 2016/10/20.
 */

public class CursorAnimator {
    protected float animTime;
    protected float timeBuffer;
    protected Image cursor;
    protected float toX,toY;
    protected float buffX,buffY;
    protected boolean stop;

    public CursorAnimator(Image image){
        this.cursor = image;
    }

    public void reset(){
        timeBuffer = 0;
        stop = true;
    }

    public void stop(){
        stop = true;
    }

    public void start(){
        stop = false;
    }

    public void reset(float x,float y){
        reset();
        buffX = x;
        buffY = y;
        toX = x;
        toY = y;
        cursor.setX(x);
        cursor.setY(y);
    }

    public void setAnimation(float startX,float startY,float toX,float toY,float animTime){
        this.toX = toX;
        this.toY = toY;
        buffX = startX;
        buffY = startY;
        this.animTime = animTime;
    }

    public boolean proc(){
        if(!stop && cursor != null) {
            if (animTime < timeBuffer) {
                cursor.setX(toX);
                cursor.setY(toY);
            } else {
                cursor.setX(buffX + (toX - buffX) * (timeBuffer / animTime));
                cursor.setY(buffY + (toY - buffY) * (timeBuffer / animTime));
                timeBuffer += Time.getDeltaTime();
                return false;
            }
        }
        return true;
    }

    public void draw(float offsetX,float offsetY){
        if(cursor != null)
            cursor.draw(offsetX,offsetY);
    }
}
