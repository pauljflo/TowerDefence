import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Store {
	public static int shopWidth = 8;
	public Rectangle[] button = new Rectangle[shopWidth];
	public static int buttonSize = 52;
	public static int cellSpace = 2;
	public static int awayFromRoom = 29;
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	static int iconSize = 20;
	static int iconSpace = 6;
	static int iconText = 14;
	public static int itemIn = 4;
	public static int heldId = -1;
	public boolean holdsItem = false;
	public static int[] buttonId = { 0, 0, 0, 0, 0, 0, 0, 1 };

	public Store() {
		define();
	}

	public void click(int mouseButton) {
		if (mouseButton == 1) {
			for (int i = 0; i < button.length; i++) {
				if (button[i].contains(Screen.mse)) {
					if (heldId == Value.airTrashCan) {
						holdsItem = false;
					} else {
						heldId = buttonId[i];
						holdsItem = true;
					}
				}
			}
		}
	}

	public void define() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle((Screen.myWidth / 2)
					- ((shopWidth * (buttonSize + cellSpace)) / 2)
					+ ((buttonSize + cellSpace) * i),
					(Screen.room.block[Screen.room.worldHeight - 1][0].y)
							+ Screen.room.blockSize + awayFromRoom, buttonSize,
					buttonSize);
		}

		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1,
				button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y
				+ button[0].height - iconSize, iconSize, iconSize);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < button.length; i++) {
			if (button[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(button[i].x, button[i].y, button[i].width,
						button[i].height);
			}

			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y,
					button[i].width, button[i].height, null);
			g.drawImage(Screen.tileset_air[buttonId[i]], button[i].x + itemIn,
					button[i].y + itemIn, button[i].width - (itemIn * 2),
					button[i].height - (itemIn * 2), null);
		}
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y,
				buttonHealth.width, buttonHealth.height, null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y,
				buttonCoins.width, buttonCoins.height, null);
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.setColor(new Color(255, 255, 255));
		g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width
				+ iconSpace, buttonHealth.y + iconText);
		g.drawString("" + Screen.money, buttonCoins.x + buttonCoins.width
				+ iconSpace, buttonCoins.y + iconText);
		
		if (holdsItem) {
			g.drawImage(Screen.tileset_air[heldId], Screen.mse.x - ((button[0].width - (itemIn * 2))/2) + itemIn, Screen.mse.y - ((button[0].width - (itemIn * 2))/2) + itemIn, button[0].width - (itemIn * 2),
					button[0].height - (itemIn * 2), null);
		}
	}
}
