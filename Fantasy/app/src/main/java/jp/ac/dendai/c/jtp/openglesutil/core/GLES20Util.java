package jp.ac.dendai.c.jtp.openglesutil.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import jp.ac.dendai.c.jtp.Game.UIs.UI.Image.Image;
import jp.ac.dendai.c.jtp.Game.UIs.UI.Text.StringBitmap;
import jp.ac.dendai.c.jtp.openglesutil.graphic.blending_mode.GLES20COMPOSITIONMODE;


public class GLES20Util extends abstractGLES20Util {
	private static Paint paint;
	private static Canvas canvas;
	private static Rect rect = new Rect(0,0,0,0);
	protected static float cx = 0,cy = 0;	//カメラ位置
	public enum GLES20UTIL_MODE{
		POSX,
		POSY
	}
	public GLES20Util(){
		Log.d("GLES20Util","Constract");
	}

	public static float screenToInnerPosition(float value,GLES20UTIL_MODE mode){
		if(value == 0)
			return 0;
		if(mode == GLES20UTIL_MODE.POSX){
			return  GLES20Util.getWidth_gl()/GLES20Util.getWidth()*value + cx;
		}
		else if(mode == GLES20UTIL_MODE.POSY){
			return GLES20Util.getHeight_gl()/GLES20Util.getHight()*(GLES20Util.getHight()-value) + cy;
		}
		return 0;
	}
	//文字列描画
	/*public static Bitmap stringToBitmap(String text,String fontName,float size,int r,int g,int b){
		//描画するテキスト
		paint = new Paint();

		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(r, g, b));
		paint.setTextSize(size);
		paint.getTextBounds(text, 0, text.length(), new Rect());
		//Typeface type = Typeface.createFromAsset(GameManager.act.getAssets(), fontName);
		//paint.setTypeface(type);
		FontMetrics fm = paint.getFontMetrics();
		//テキストの表示範囲を設定

		int textWidth = (int) paint.measureText(text);
		int textHeight = (int) (Math.abs(fm.top) + fm.bottom);
		Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);

		//キャンバスからビットマップを取得
		canvas = new Canvas(bitmap);
		canvas.drawText(text, 0, Math.abs(fm.top), paint);

		return bitmap;
	}*/
	public static Bitmap stringToBitmap(String text,String fontName,float size,int r,int g,int b){
		String[] line = text.split("\n");

		//描画するテキスト
		paint = new Paint();

		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(r, g, b));
		paint.setTextSize(size);
		int textWidth = 0;
		int textHeight = 0;
		for(int n = 0;n < line.length;n++){
			paint.getTextBounds(line[n], 0, line[n].length(), new Rect());
			//Typeface type = Typeface.createFromAsset(GameManager.act.getAssets(), fontName);
			//paint.setTypeface(type);
			FontMetrics fm = paint.getFontMetrics();
			//テキストの表示範囲を設定

			textWidth = Math.max((int) paint.measureText(line[n]),textWidth);
			textHeight += (int) (Math.abs(fm.top) + fm.bottom);
		}
		Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		for(int n = 0;n < line.length;n++) {
			paint.getTextBounds(line[n], 0, line[n].length(), new Rect());
			//Typeface type = Typeface.createFromAsset(GameManager.act.getAssets(), fontName);
			//paint.setTypeface(type);
			FontMetrics fm = paint.getFontMetrics();
			//キャンバスからビットマップを取得
			canvas.drawText(line[n], 0, Math.abs(fm.top)+textHeight/line.length*n, paint);
		}

		return bitmap;
	}

	//文字列描画
	public static StringBitmap stringToStringBitmap(String text,String fontName,float size, int r, int g, int b){
		//描画するテキスト
		paint = new Paint();

		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(r, g, b));
		paint.setTextSize(size);
		paint.getTextBounds(text, 0, text.length(), new Rect());
		//Typeface type = Typeface.createFromAsset(GameManager.act.getAssets(),fontName);
		//paint.setTypeface(type);
		FontMetrics fm = paint.getFontMetrics();
		//テキストの表示範囲を設定

		int textWidth = (int) paint.measureText(text);
		int textHeight = (int) (Math.abs(fm.top) + fm.bottom);
		Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);

		//キャンバスからビットマップを取得
		canvas = new Canvas(bitmap);
		//canvas.drawColor(Color.BLUE);
		canvas.drawText(text, 0, Math.abs(fm.top), paint);

		return new StringBitmap(bitmap,paint.getFontMetrics(),textWidth);
	}

	//文字列描画
	public static Bitmap stringToBitmap(String text,String fontName,float size,int r,int g,int b,int br,int bg,int bb){
		//描画するテキスト
		paint = new Paint();

		paint.setAntiAlias(true);
		paint.setColor(Color.rgb(r, g, b));
		paint.setTextSize(size);
		paint.getTextBounds(text, 0, text.length(), new Rect());
		//Typeface type = Typeface.createFromAsset(GameManager.act.getAssets(), fontName);
		//paint.setTypeface(type);
		FontMetrics fm = paint.getFontMetrics();
		//テキストの表示範囲を設定

		int textWidth = (int) paint.measureText(text);
		int textHeight = (int) (Math.abs(fm.top) + fm.bottom);
		Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);

		//キャンバスからビットマップを取得
		canvas = new Canvas(bitmap);
		canvas.drawColor(Color.argb(255,br,bg,bb));
		canvas.drawText(text, 0, Math.abs(fm.top), paint);

		return bitmap;
	}

	public static void DrawString(String string,String fontname,int size,int r,int g,int b,float alpha,float x,float y,GLES20COMPOSITIONMODE mode){
		Bitmap bitmap = stringToBitmap(string,"メイリオ",size,r,g,b);
		//Log.d("DrawString",String.valueOf(bitmap.getWidth()));
		DrawGraph(x, y, bitmap.getWidth() / 1000f, bitmap.getHeight() / 1000f, bitmap, alpha, mode);
	}

	/**
	 * 画像表示
	 */
	//画像表示
	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,Bitmap image,float alpha,GLES20COMPOSITIONMODE mode){
		DrawGraph(startX,startY,lengthX,lengthY,0,0,1,1,0f,image,alpha,mode);
	}
	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,float r,float g,float b,Bitmap image,float alpha,GLES20COMPOSITIONMODE mode){
		DrawGraph(startX,startY,lengthX,lengthY,0,0,1,1,0,0,1,1,0,r,g,b,image,GLES20Util.mask,alpha,mode);
	}
	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,float uox,float uoy,float usx,float usy,float degree,Bitmap image,float alpha,GLES20COMPOSITIONMODE mode){
		DrawGraph(startX,startY,lengthX,lengthY,uox,uoy,usx,usy,0,0,1,1,degree,0.5f,0.5f,0.5f,image,GLES20Util.mask,alpha,mode);
	}

	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,float uox,float uoy,float usx,float usy,float mask_x,float mask_y,float mask_sx,float mask_sy,float degree,float r,float g,float b,Bitmap image,Bitmap mask,float alpha,GLES20COMPOSITIONMODE mode){
		float scaleX = lengthX;
		float scaleY = lengthY;

		//float[] modelMatrix = new float[16];
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, startX, startY, 0.0f);
		Matrix.rotateM(modelMatrix, 0, degree, 0, 0, 1);
		Matrix.scaleM(modelMatrix, 0, scaleX, scaleY, 1.0f);
		setShaderModelMatrix(modelMatrix);

		//GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		//GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		setOnTexture(image,alpha,GLES20.GL_LINEAR);
		GLES20.glUniform3f(u_color,r,g,b);
		setOnMask(mask,mask_x,mask_y,mask_sx,mask_sy,GLES20.GL_LINEAR);

		GLES20.glUniform4f(u_texPos,uox,uoy,usx,usy);

		mode.setBlendMode();
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);	//描画
	}

	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,float uox,float uoy,float usx,float usy,float mask_x,float mask_y,float degree,Bitmap image,Bitmap mask,float alpha,GLES20COMPOSITIONMODE mode){
		DrawGraph(startX,startY,lengthX,lengthY,uox,uoy,usx,usy,mask_x,mask_y,1,1,degree,0.5f,0.5f,0.5f,image,mask,alpha,mode);
	}

	public static void DrawString(float startX,float startY,float lengthX,float lengthY,float uox,float uoy,float usx,float usy,float mask_x,float mask_y,float line,float degree,Bitmap image,Bitmap mask,float alpha,GLES20COMPOSITIONMODE mode){
		float scaleX = lengthX;
		float scaleY = lengthY;

		//float[] modelMatrix = new float[16];
		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, startX, startY, 0.0f);
		Matrix.scaleM(modelMatrix, 0, scaleX, scaleY, 1.0f);
		Matrix.rotateM(modelMatrix, 0, degree, 0, 0, 1);
		setShaderModelMatrix(modelMatrix);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		setOnTexture(image,alpha,GLES20.GL_NEAREST);
		setOnMask(mask,mask_x,mask_y,1,line,GLES20.GL_NEAREST);

		GLES20.glUniform4f(u_texPos,uox,uoy,usx,usy);

		mode.setBlendMode();
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);	//描画
	}

	public static void DrawGraph(float startX,float startY,float lengthX,float lengthY,Image img){
		DrawGraph(startX,startY,lengthX,lengthY,0,0,1,1,0,img.getImage(),1f,GLES20COMPOSITIONMODE.ALPHA);
	}

	public static void DrawString(String text,int textSize,Color color,float x,float y){

	}

	public static void setCameraPos(float x,float y){
		cx = x;
		cy = y;
		Matrix.orthoM(viewProjMatrix,0,cx,aspect+cx,cy,1f+cy,mNear/100,mFar/100);
		setShaderProjMatrix();
	}

	public static float getCameraPosX(){
		return cx;
	}
	public static float getCameraPosY(){
		return cy;
	}

	/**
	 * FPS表示。三桁。FPSに限らず数値であれば表示できる
	 */
	//FPS表示	三桁表示
	public static void DrawFPS(float x,float y,int FPS,Bitmap[] digitBitmap,float alpha){
		int place100,place10,place1;
		FPS %= 1000;
		place100 = FPS/100;
		place10 = (FPS-place100*100)/10;
		place1 = (FPS - place100*100-place10*10);
		DrawGraph(x,y,62.0f/1000.0f,110.0f/1000.0f,digitBitmap[place100],alpha, GLES20COMPOSITIONMODE.ALPHA);
		DrawGraph(x+62.0f/1000.0f,y,62.0f/1000.0f,110.0f/1000.0f,digitBitmap[place10],alpha,GLES20COMPOSITIONMODE.ALPHA);
		DrawGraph(x+62.0f/1000.0f*2.0f,y,62.0f/1000.0f,110.0f/1000.0f,digitBitmap[place1],alpha,GLES20COMPOSITIONMODE.ALPHA);

	}

	/**
	 * FPS用の十進数画像の作成。現在は用意されたdegital2.pngのみを対象。
	 */
	//flagはtrueで黒を透過色、falseで白を透過色
	public static void initFpsBitmap(Bitmap[] bitmap,boolean flag,int resource){
		int count=0;
		int rgb = 0;
		for(int n=0;n<2;n++){
			for(int m=0;m<5;m++){
				bitmap[count] = loadBitmap(62*m,110*n,62*(m+1),110*(n+1),resource);
				if(flag){
					for(int a =0;a<62;a++){
						for(int b=0;b<110;b++){
							rgb = bitmap[count].getPixel(a, b);
							bitmap[count].setPixel(a, b,Color.argb(
									(Color.red(rgb)+Color.red(rgb)+Color.blue(rgb))/3
									,Color.red(rgb)
									,Color.red(rgb)
									,Color.blue(rgb)
							));
						}
					}
				}
				else{
					for(int a =0;a<62;a++){
						for(int b=0;b<110;b++){
							rgb = bitmap[count].getPixel(a, b);

							bitmap[count].setPixel(a, b,Color.argb(
									(255-Color.red(rgb)+255-Color.red(rgb)+255-Color.blue(rgb))/3
									,Color.red(rgb)
									,Color.red(rgb)
									,Color.blue(rgb)
							));
						}
					}
				}
				count++;
			}
		}
	}
	public static float convertTouchPosToGLPosX(float dispPosX){
		return dispPosX / GLES20Util.getHight() + cx;
	}
	public static float convertTouchPosToGLPosY(float dispPosY){
		return GLES20Util.getHeight_gl() - dispPosY / GLES20Util.getHight() + cy;
	}
}

