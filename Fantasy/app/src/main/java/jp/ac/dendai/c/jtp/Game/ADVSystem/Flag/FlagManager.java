package jp.ac.dendai.c.jtp.Game.ADVSystem.Flag;

import android.util.Log;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class FlagManager {
    public final static int town = 0;
    public final static int hp = 1;
    public final static int atk = 2;
    public final static int def = 3;
    public final static int agl = 4;
    public final static int mp = 5;

    protected static int global_flag_num = 128;
    protected static int system_flag_num = 32;
    protected static String global_flag_tag = "global";
    protected static String system_flag_tag = "system";
    protected static int[] global_flag = new int[global_flag_num];
    protected static int[] system_flag = new int[system_flag_num];
    public static int getFlagValue(String type,int value){
        if(type.equals(global_flag_tag)){
            //グローバルフラグの取得
            return global_flag[value];
        }else if(type.equals(system_flag_tag)){
            //システムフラグの取得
            return system_flag[value];
        }
        return value;
    }
    public static void setFlagValue(String type,int index,int value){
        if(type.equals(global_flag_tag)){
            //グローバルフラグの設定
            global_flag[index] = value;
        }else if(type.equals(system_flag_tag)){
            //システムフラグの設定
            system_flag[index] = value;
        }
    }
    public static String toInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Global Flag = ");
        for(int n = 0;n < global_flag.length;n++){
            sb.append("["+n+" : "+global_flag[n]+"]");
        }
        sb.append("\n");
        sb.append("System Flag = ");
        for(int n = 0;n < system_flag.length;n++){
            sb.append("["+n+" : "+system_flag[n]+"]");
        }
        return sb.toString();
    }
    public static void outputDebugInfo(){
        Log.d("Flag Manager Info",toInfo());
    }
}
