package jp.ac.dendai.c.jtp.openglesutil.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.res.AssetManager;

import jp.ac.dendai.c.jtp.Game.Constant;
import jp.ac.dendai.c.jtp.Game.GameManager;

import static android.content.Context.MODE_PRIVATE;
import static android.os.ParcelFileDescriptor.MODE_APPEND;

/**
 * ファイル読み込み用クラス
 *　作り掛け
 * @author
 *
 */
public class FileManager {
    public static Activity _act;
    public static void initFileManager(Activity act){
        _act = act;
    }
    public static String readTextFile(String fileName){
        return readShaderFile(_act,fileName);
    }
	/**
	 * assetsフォルダに入っているシェーダファイルを読み込みます
	 * 正直シェーダファイルでなくても読み込める
	 * 読み込み文字コードはUTF-8
	 * @return　成功:読み込んだ文字列_失敗:”読み込み失敗”
	 */
	public static String readShaderFile(Activity activity,String fileName){
		//ファイル読み込みなど操作するときはtry{}catch{}で囲む
		try{
            //元からあるassetsフォルダに入れたtextファイルを読み込む方法
            AssetManager assets = activity.getResources().getAssets();
            InputStream in = assets.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String str;
            StringBuilder strs = new StringBuilder();
            while ((str = br.readLine()) != null) {
                strs.append(str);
                strs.append('\n');
            }
            br.close();
            return strs.toString();
        }catch (Exception e){
            return "読み込み失敗";
        }
	}
    public static void writeTextFileLocal(String fileName,String data){
        OutputStream out;
        try {
            out = GameManager.act.openFileOutput(fileName,MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));

            //追記する
            writer.append(data);
            writer.close();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public static String readTextFileLocal(String fileName){
        InputStream in;
        String lineBuffer;
        StringBuilder sb = new StringBuilder();
        try {
            in = GameManager.act.openFileInput(fileName); //LOCAL_FILE = "log.txt";

            BufferedReader reader= new BufferedReader(new InputStreamReader(in,"UTF-8"));
            while( (lineBuffer = reader.readLine()) != null ){
                sb.append(lineBuffer);
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return sb.toString();
    }

}
