package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum;

/**
 * Created by テツヤ on 2016/10/06.
 */
public enum Condition_Mode {
    and{
        @Override
        public boolean evaluation(boolean value1,boolean value2){
            return value1 && value2;
        }
    },
    or{
        @Override
        public boolean evaluation(boolean value1,boolean value2){
            return value1 || value2;
        }
    };

    public abstract boolean evaluation(boolean value1,boolean value2);
}
