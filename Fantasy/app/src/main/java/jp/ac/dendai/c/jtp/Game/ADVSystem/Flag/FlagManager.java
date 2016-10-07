package jp.ac.dendai.c.jtp.Game.ADVSystem.Flag;

/**
 * Created by テツヤ on 2016/10/06.
 */
public class FlagManager {
    protected static String global_flag_tag = "global";
    protected static String system_flag_tag = "system";
    protected static int[] global_flag;
    protected static int[] system_flag;
    public static int getFlagValue(String type,int value){
        if(type.equals(global_flag_tag)){
            //グローバルフラグの取得
            return global_flag[value];
        }else if(type.equals(system_flag_tag)){
            //システムフラグの取得
            return system_flag[value];
        }
        return -1;
    }
}
