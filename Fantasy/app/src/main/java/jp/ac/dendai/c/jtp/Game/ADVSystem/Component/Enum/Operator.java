package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum;

/**
 * Created by テツヤ on 2016/10/09.
 */

public enum Operator {
    add{
        @Override
        public int process(int value1,int value2){
            return value1 + value2;
        }
        @Override
        public String toString(){
            return "+";
        }
    };
    public abstract int process(int value1,int value2);
    public Operator parse(String str){
        if(add.toString().equals(str)){
            return add;
        }
        return add;
    }
}
