import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static boolean isFirst = true;
	public static int myWidth, myHeight;
	public static int money = 10, health = 100;
	public static Room room;
	public static Save save;
	public static Store store;
	public static Image[] tileset_mob = new Image[100];//100 of each mob
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];//air tiles appear on top of ground tiles
	
	public static Point mse = new Point(0, 0);//Holds co-ords.
	public static Mob mobs[] = new Mob[100];//This can be static.. not the mob values themselves gaawhd..

	public Screen(Frame frame){
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		System.out.println("got thread and Screen");
		
		thread.start();
	}
	
	public void define(){
		room = new Room();
		save = new Save();
		store = new Store();
		
		//These 'for' loops pull the right Image from the spriteboard.. each image is 26*26px and represented by a number in the array [i].
		for (int i = 0;i<tileset_ground.length;i++){
			tileset_ground[i] = new ImageIcon("Res/tileset_Grass.png").getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		for (int i = 0;i<tileset_air.length;i++){
			tileset_air[i] = new ImageIcon("Res/tileset_Air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}
		
		//I made these images as seperate files, so no for loop needed.
		tileset_res[0] = new ImageIcon("Res/Shop.png").getImage();
		tileset_res[1] = new ImageIcon("Res/Heart.png").getImage();
		tileset_res[2] = new ImageIcon("Res/Coin.png").getImage();
		
		tileset_mob[0] = new ImageIcon("Res/Mob.png").getImage();
		save.loadSave(new File("Save/mission1.sav"));//See save class...
		
		for (int i = 0;i<mobs.length;i++){
			mobs[i] = new Mob();
			//Creates 100 instances of the mob object and assigns an int [i] to each
		}
		
	}
	

	public void paintComponent(Graphics g){
		if (isFirst) {
			myWidth = getWidth();//This is from the JPanel class
			myHeight = getHeight();//ditto.
			define();
			
			isFirst = false;
		}
		
		g.setColor(new Color(70, 70, 70));
		g.fillRect(0, 0, getWidth(), getHeight());//they're very handy :)
		g.setColor(new Color(0, 0, 0));
		//Creates the grid based on the specification in the room and block classes.
		g.drawLine(room.block[0][0].x -1, 0, room.block[0][0].x -1, room.block[room.worldHeight -1][0].y + room.blockSize +1);
		g.drawLine(room.block[0][room.worldWidth -1].x + room.blockSize, 0, room.block[0][room.worldWidth -1].x + room.blockSize, room.block[room.worldHeight -1][0].y + room.blockSize +1);
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight -1][0].y + room.blockSize, room.block[0][room.worldWidth -1].x + room.blockSize , room.block[room.worldHeight -1][0].y + room.blockSize);
			room.draw(g);//draws the room
			
			for (int i = 0;i<mobs.length;i++){
				if (mobs[i].inGame){//draws the mobs as they are generated.
					mobs[i].draw(g);
				}
			}
			
			store.draw(g);//draws the Store
	
	}
	
	public int spawnTime = 3400, spawnFrame = 0;//makes a separate game clock for mobSpawner
	public void mobSpawner() {
		if (spawnFrame >= spawnTime) {
			for (int i = 0; i<mobs.length; i++){
				if (!mobs[i].inGame) {//if it hasn't already been created..
					mobs[i].spawnMob(Value.mobGreen);
					System.out.println("Spawned");
					break;
				}
			}
			spawnFrame = 0;
		} else {
			spawnFrame += 1;
		}
	}
	
	public void run(){
		while(true){
			if(!isFirst){
				room.physics();
				mobSpawner();
				for (int i= 0; i < mobs.length; i ++){
					if (mobs[i].inGame){
						mobs[i].physic();//start moving each mob
					}
				}
			}
			repaint();
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
