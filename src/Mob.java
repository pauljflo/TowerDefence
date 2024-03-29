import java.awt.*;

public class Mob extends Rectangle {
	public int xC, yC;
	public int mobSize = 52;
	public int mobWalk = 0;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public static int mobId = Value.mobAir;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;

	public Mob() {

	}

	public void spawnMob(int mobId) {
		for (int y = 0; y < Screen.room.block.length; y++) {
			if (Screen.room.block[y][0].groundId == Value.groundRock) {
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y,
						mobSize, mobSize);
				xC = 0;
				yC = y;
			}
		}

		this.mobId = mobId;
		inGame = true;
	}

	public int walkFrame = 0, walkSpeed = 40;

	public void physic() {
		if (walkFrame >= walkSpeed) {
			if (direction == right) {
				x += 1;
			} else if (direction == upward) {
				y -= 1;
			} else if (direction == downward) {
				y += 1;
			} else if (direction == left) {
				x -= 1;
			}
			mobWalk += 1;

			if (mobWalk == Screen.room.blockSize) {
				if (direction == right) {
					xC += 1;
					hasRight = true;
				} else if (direction == upward) {
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward) {
					yC += 1;
					hasDownward = true;
				} else if (direction == left) {
					xC -= 1;
					hasLeft = true;
				}

				if (!hasUpward) {
					try {
						if (Screen.room.block[yC + 1][xC].groundId == Value.groundRock) {
							direction = downward;
						}
					} catch (Exception e) {
					}
				}
				if (!hasDownward) {
					try {
						if (Screen.room.block[yC - 1][xC].groundId == Value.groundRock) {
							direction = upward;
						}
					} catch (Exception e) {
					}
				}
				if (!hasLeft) {
					try {
						if (Screen.room.block[yC][xC + 1].groundId == Value.groundRock) {
							direction = right;
						}
					} catch (Exception e) {
					}
				}
				if (!hasRight) {
					try {
						if (Screen.room.block[yC][xC - 1].groundId == Value.groundRock) {
							direction = left;
						}
					} catch (Exception e) {
					}
				}
				
				if (Screen.room.block[yC][xC].airId == Value.airBase) {
					deleteMob();
					loseHealth();
				}
				
				hasLeft = false;
				hasRight = false;
				hasUpward = false;
				hasDownward = false;
				mobWalk = 0;
			}
			walkFrame = 0;
		} else {
			walkFrame += 1;
		}
	}
	
	public void deleteMob() {
		inGame = false;
	}
	
	public void loseHealth() {
		Screen.health -= 1;
	}

	public void draw(Graphics g) {
		g.drawImage(Screen.tileset_mob[mobId], x, y, width, height, null);
	}
}
