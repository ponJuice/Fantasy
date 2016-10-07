package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum;

/**
 * Created by テツヤ on 2016/10/06.
 */
public enum Mnemonic {
    eq{
        @Override
        public boolean evaluation(int value1,int value2){
            return value1 == value2;
        }
    },
    neq{
        @Override
        public boolean evaluation(int value1,int value2){
            return value1 != value2;
        }
    };

    public abstract boolean evaluation(int value1,int value2);
}
