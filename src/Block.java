import java.awt.Graphics;
import java.awt.Rectangle;


public class Block extends Rectangle {
	public int groundId;
	public int airId;
	
	public Block(int x, int y, int width, int height, int groundId, int airId) {
		setBounds(x , y, width, height);
		this.groundId = groundId;
		this.airId = airId;
	}
	
	public void draw(Graphics g) {
		g.drawImage(Screen.tileset_ground[groundId], x, y, width, height, null);
		//g.drawRect(x, y, width, height);
		
		if (airId != Value.airAir) {
			g.drawImage(Screen.tileset_air[airId], x, y, width, height, null);
		}
	}
}
