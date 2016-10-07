package jp.ac.dendai.c.jtp.Game.UIs.Screen;

public interface Screenable {
	public void Proc();
	public void Draw(float offsetX,float offsetY);
	public void Touch();
	public void death();
	public void pause();
	public void resume();
	public void freeze();
	public void unFreeze();
}
