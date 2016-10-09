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

    public final static String a_eq = "==";
    public final static String a_neq = "!=";
    public abstract boolean evaluation(int value1,int value2);
    public static Mnemonic parse(String str){
        if(str.equals(a_eq)){
            return eq;
        }else if(str.equals(a_neq)){
            return neq;
        }
        return null;
    }
}
