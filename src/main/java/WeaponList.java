import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * A list of weapons.
 * @author Bart
 *
 */
public class WeaponList {
	
	private ArrayList<Weapon> weaponList;
	private MainGame mg;
	private GameState gs;
	
	private static final int LASER_X_DEVIATION = 18;
	private static final int LASER_TIP_Y_DEVIATION = 14;
	private static final int LASER_BEAM_Y_DEVIATION = 13;
	private static final int LASER_BEAM_X2_DEVIATION = 17;
	private static final int LASER_BEAM_SRCX2 = 35;
	private static final int LASER_BEAM_SRCY2 = 300;
	private Image laserbeamimage;
	private Image lasertipimage;
	
	/**
	 * The constructor of weaponlist.
	 * @param weapon1 	- the first weapon of the list
	 * @param mg		- the maingame 
	 * @param gs		- the gamestate
	 */
	public WeaponList(Weapon weapon1, MainGame mg, GameState gs) {
		super();
		this.weaponList = new ArrayList<Weapon>();
		weaponList.add(weapon1);
		this.mg = mg;
		this.gs = gs;
		
		try {
			initImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserct all weapons with a circle.
	 * @param circle	- the circle to intersect with
	 */
	public void intersectWeaponsWithCircle(BouncingCircle circle) {
		intersectWeaponWithCircle(circle, 0);
		
		if (mg.isMultiplayer()) {
			intersectWeaponWithCircle(circle, 1);
		}
	}
	
	private void intersectWeaponWithCircle(BouncingCircle circle, int weaponNumber) {
		Weapon weapon = weaponList.get(weaponNumber);
		Player player = mg.getPlayerList().getPlayers().get(weaponNumber);
		
		if (player.isShot() && weapon.getRectangle().intersects(circle)) {
			gs.getShotList().add(circle);
			weapon.setVisible(false);
		}
	}
	
	private void initImages() throws SlickException {
		// laser images
		laserbeamimage = new Image("resources/laser/laser_beam_blue.png");
		lasertipimage = new Image("resources/laser/laser_tip_blue.png");
	}
	
	/**
	 * Add a weapon to the list.
	 * @param weapon 	- the weapon to add
	 */
	public void add(Weapon weapon) {
		if (weaponList.size() < 2) {
			weaponList.add(weapon);
		}
	}
	
	/**
	 * Draw all weapons.
	 * @param graphics	- the graphics to draw with
	 */
	public void drawWeapons(Graphics graphics) {
		if (mg.getPlayerList().getPlayers().get(0).isShot()) {
			drawWeapon(graphics, 0);
		}
		
		if (mg.isMultiplayer() && mg.getPlayerList().getPlayers().get(1).isShot()) {
			drawWeapon(graphics, 1);
		}
	}
	
	/**
	 * Set a weapon.
	 * @param weaponNumber	- the number of the weapon to set
	 * @param weapon		- the weapon to set
	 */
	public void setWeapon(int weaponNumber, Weapon weapon) {
		weaponList.set(weaponNumber, weapon);
	}
	
	private void drawWeapon(Graphics graphics, int weaponNumber) {
		Weapon weapon = weaponList.get(weaponNumber);
		
		graphics.drawImage(lasertipimage, weapon.getX() - LASER_X_DEVIATION,
				weapon.getY() - LASER_TIP_Y_DEVIATION);
		graphics.drawImage(laserbeamimage, weapon.getX() - LASER_X_DEVIATION,
				weapon.getRectangle().getMinY() + LASER_BEAM_Y_DEVIATION, weapon.getX()
				+ LASER_BEAM_X2_DEVIATION, weapon.getRectangle().getMaxY(), 0, 0,
				LASER_BEAM_SRCX2, LASER_BEAM_SRCY2);
	}

	/**
	 * @return the weaponList
	 */
	public ArrayList<Weapon> getWeaponList() {
		return weaponList;
	}

	/**
	 * @param weaponList the weaponList to set
	 */
	public void setWeaponList(ArrayList<Weapon> weaponList) {
		this.weaponList = weaponList;
	}
	
	
	

}
