package jp.ac.dendai.c.jtp.Game;

import android.graphics.Bitmap;

import java.util.Random;

import jp.ac.dendai.c.jtp.Game.BattleSystem.Player.PlayerData;
import jp.ac.dendai.c.jtp.fantasy.R;
import jp.ac.dendai.c.jtp.openglesutil.core.GLES20Util;

/**
 * Created by Goto on 2016/07/08.
 */
public class Constant {
    public enum BITMAP{
        white,
        black,
        bilinear,
        stream_text,
        system_button,
        system_talk_back,
        system_message_box,
        system_selector,
        system_cursor
    }
    private static float sens = 1.0f;
    public static void setSens(float n){ sens = n;}
    public static float getSens(){return sens;}
    public static final String fontName = "custom_font.ttf";
    protected static Bitmap text_effect_white,text_effect_mask,system_button,black,stream_text,system_talk_back,system_message_box;
    protected static Bitmap system_selector,system_cursor;
    protected static PlayerData playerData;
    public final static int talk_text_size = 25;
    public final static int talk_text_color = 0xFFFFFFFF;  //a,r,g,b
    public final static float talk_textbox_x = 0;
    public final static float talk_textbox_y = 0;
    public final static int talk_text_max_length_x = 20;
    public final static int talk_text_max_length_y = 3;
    public final static String talk_text_font_name = "メイリオ";
    public final static int talk_text_height_offset = 0;
    public final static float talk_text_width = 1.1f;
    public final static float talk_text_height = 0.25f;
    public final static int face_image_length_x = 384;
    public final static int face_image_length_y = 192;
    public final static int face_image_x_count_max = 4;
    public final static int face_image_y_count_max = 2;
    public final static float message_box_line_height = 0.05f;
    public final static int message_box_max_length_x = 10;
    public final static String EVENT_DIRECTORY = "Event/";
    public final static String face_id = "id";
    public final static String face_fileName = "file";
    public final static String face_name = "name";
    public final static String face_directory = "Face/";
    public final static String text_id = "id";
    public final static String image_id = "id";
    public final static String image_fileName = "file";
    public final static String image_file_directory = "Image/";
    public final static String sound_id = "name";
    public final static String sound_fileName = "file";
    public final static String sound_file_directory = "Sound/";
    public final static String enemy_image_file_directory = "Battle/Enemy/Images/";
    public final static String animation_image_file_directory = "Battle/Animations/Images/";
    protected final static String animationFile = "Battle/Animations/Animation.dat";
    protected final static String skillFile = "Battle/Skills/Skills.dat";
    protected final static String enemyFile = "Battle/Enemy/Enemys.dat";
    protected final static String itemFile = "Battle/Items/Items.dat";
    protected final static String townFile = "Town/town.dat";
    //protected final static String system_image_directory = "Image/System/";
    //protected final static String selector_image = "selector.png";

    /* -----------MapScreen関連----------------*/
    public final static String mapImageFile = "Image/map.png";

    public final static int player_init_hp = 100;
    public final static int player_init_atk = 100;
    public final static int player_init_def = 100;

    /*------------BattleScreen関連--------------*/
    public final static float enemy_size_x = 0.4f;  //敵グラフィックスの横長さ
    public final static float enemy_size_y = 0.4f;  //敵グラフィックスの縦長さ
    public final static float enemy_damage_size_x = 0.3f;
    public final static float enemy_damage_size_y = 0.3f;
    public final static float friend_damage_size_x = 1.2f;
    public final static float friend_damage_size_y = 1.2f;
    public final static float damage_lowest = 1f;   //ダメージの最小限度
    public final static float skill_effect_time = 0.5f;    //ダメージエフェクト時間
    public final static float damage_number_time = 0.5f;    //ダメージ値の表示時間
    public final static float damage_number_height_enemy = 0.2f;
    public final static float damage_number_height_player = 0.5f;
    public final static float hp_decrease_time = 0.5f;
    public final static float dead_effect_time = 1f;
    public final static float damage_gage_time = 1f;
    public final static float action_textbox_y_offset = 0.1f;
    public final static float battle_state_interval = 0.5f;
    public final static String normal_attack_name = "normalAttack";
    public final static float battle_list_width = 0.3f;
    public final static float battle_list_height = 0.5f;
    public final static float battle_list_content_width = 0.3f;
    public final static float battle_list_content_height = 0.1f;
    public final static float battle_list_text_padding = 0.02f;
    public final static float battle_list_item_padding = 0.0f;

    /* -----------その他-------------*/
    protected final static Random random = new Random(System.currentTimeMillis());
    public final static float list_content_height = 0.1f;
    //public final static float enemy_dead_effect_time = 1f;
    //public final static float


    public static void init(){
        if(text_effect_white == null)
            text_effect_white = GLES20Util.loadBitmap(R.mipmap.text_effect_white);
        if(text_effect_mask == null)
            text_effect_mask = GLES20Util.loadBitmap(R.mipmap.text_effect_mask);
        if(system_button == null)
            system_button = GLES20Util.loadBitmap(R.mipmap.button);
        if(black == null)
            black = GLES20Util.createBitmap(255,0,0,0);
        if(stream_text == null)
            stream_text = GLES20Util.loadBitmap(R.mipmap.text_mask);
        if(system_talk_back == null)
            system_talk_back = GLES20Util.loadBitmap(R.mipmap.serihu_waku);
        if(system_message_box == null)
            system_message_box = GLES20Util.loadBitmap(R.mipmap.massagebox);
        if(system_selector == null)
            system_selector = GLES20Util.loadBitmap(R.mipmap.selector);
        if(system_cursor == null)
            system_cursor = GLES20Util.loadBitmap(R.mipmap.yajirushi);

    }

    public static Random getRandom(){
        return random;
    }

    public static Bitmap getBitmap(BITMAP f){
        if(f == BITMAP.white)
            return text_effect_white;
        else if(f == BITMAP.bilinear)
            return text_effect_mask;
        else if(f == BITMAP.system_button)
            return system_button;
        else if(f == BITMAP.black){
            return black;
        }else if(f == BITMAP.stream_text){
            return stream_text;
        }else if(f == BITMAP.system_talk_back){
            return system_talk_back;
        }else if(f == BITMAP.system_message_box){
            return system_message_box;
        }else if(f == BITMAP.system_selector){
            return system_selector;
        }else if(f == BITMAP.system_cursor){
            return system_cursor;
        }
        return text_effect_white;
    }

}
