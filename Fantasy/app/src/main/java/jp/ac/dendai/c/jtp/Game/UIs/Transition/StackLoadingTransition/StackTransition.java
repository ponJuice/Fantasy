package jp.ac.dendai.c.jtp.Game.UIs.Transition.StackLoadingTransition;

import jp.ac.dendai.c.jtp.Game.GameManager;
import jp.ac.dendai.c.jtp.Game.UIs.Screen.Screenable;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.LoadingTransition.LoadingThread;
import jp.ac.dendai.c.jtp.Game.UIs.Transition.Transitionable;

/**
 * Created by Goto on 2016/10/11.
 */

public class StackTransition implements Transitionable {
    private static StackTransition stackTransition;
    private static Object lock = new Object();
    private LoadingThread l_thread;
    private boolean thread_start = false;
    private StackTransition(){

    }
    public static StackTransition getInstance(){
        if(stackTransition == null)
            stackTransition = new StackTransition();
        return stackTransition;
    }
    public void init(Class<?> nextScreenClass, Screenable screen){
        GameManager.stack.push(screen);
        l_thread = new LoadingThread(lock);
        l_thread.initThread(nextScreenClass);
    }

    @Override
    public boolean Transition() {
        if(!thread_start)
            l_thread.start();
        if(l_thread.isEnd()){
            GameManager.nowScreen = l_thread.getScreen();
            GameManager.nowScreen.unFreeze();
            return false;
        }
        return true;
    }
}
