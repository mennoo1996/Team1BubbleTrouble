package logic;
import gui.GameState;
import gui.MainGame;
import gui.RND;

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
	private MainGame mainGame;
	private GameState gameState;
	
	private static final int LASER_X_DEVIATION = 18;
	private static final int LASER_TIP_Y_DEVIATION = 14;
	private static final int LASER_BEAM_Y_DEVIATION = 13;
	private static final int LASER_BEAM_X2_DEVIATION = 17;
	private static final int LASER_BEAM_SRCX2 = 35;
	private static final int LASER_BEAM_SRCY2 = 300;
	private Image laserbeamimageN;
	private Image laserbeamimageA;
	private Image lasertipimageN;
	private Image lasertipimageA;
	
	private Logger logger = Logger.getInstance();
	 
	/**
	 * The constructor of weaponlist.
	 * @param weapon1 	- the first weapon of the list
	 * @param mainGame		- the maingame
	 * @param gameState		- the gamestate
	 * @param testing	- testing state or not
	 */
	public WeaponList(Weapon weapon1, MainGame mainGame, GameState gameState, boolean testing) {
		this.weaponList = new ArrayList<Weapon>();
		weaponList.add(weapon1);
		this.mainGame = mainGame;
		this.gameState = gameState;
		
		if (!testing) {
			try {
				initImages();
			} catch (Exception e) {
				System.out.println("exception");
			}
		} 
	}
	
	/**
	 * Inserct all weapons with a circle.
	 * @param circle	- the circle to intersect with
	 */
	public void intersectWeaponsWithCircle(BouncingCircle circle) {
		intersectWeaponWithCircle(circle, 0);
		
		if (mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) {
			intersectWeaponWithCircle(circle, 1);
		}
	}
	
	/**
	 * Intersect this weapon with a circle.
	 * @param circle the circle to intersect with
	 * @param weaponNumber the weapon to check
	 */
	private void intersectWeaponWithCircle(BouncingCircle circle, int weaponNumber) {
		Weapon weapon = weaponList.get(weaponNumber);
		Player player = mainGame.getPlayerList().getPlayers().get(weaponNumber);
		
		boolean canProcessLAN = !mainGame.isLanMultiplayer() || (mainGame.isHost() 
				&& weaponNumber == 0) || (mainGame.isClient() && weaponNumber == 1);
		
		if (player.isShot() && weapon.getRectangle().intersects(circle) && canProcessLAN) {
			gameState.getShotList().add(circle);
			logger.log("Circle shot", Logger.PriorityLevels.LOW, "weapon");
			weapon.setVisible(false);
			if (mainGame.isHost() && weaponNumber == 0) {
				mainGame.getHost().laserDone(weaponNumber);
			} else if (mainGame.isClient() && weaponNumber == 1) {
				mainGame.getClient().laserDone(weaponNumber);
			}
		}
	}
	
	/**
	 * Initalize the images for the weapons.
	 * @throws SlickException if something goes wrong / file not found
	 */
	private void initImages() throws SlickException {
		// laser images
		laserbeamimageN = new Image("resources/images_Gameplay/laserBeam_Norm.png");
		laserbeamimageA = new Image("resources/images_Gameplay/laserBeam_Add.png");
		lasertipimageN = new Image("resources/images_Gameplay/laserTip_Norm.png");
		lasertipimageA = new Image("resources/images_Gameplay/laserTip_Add.png");
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
		if (mainGame.getPlayerList().getPlayers().get(0).isShot()) {
			drawWeapon(graphics, 0);
		}
		
		if ((mainGame.isMultiplayer() || mainGame.isLanMultiplayer()) 
				&& mainGame.getPlayerList().getPlayers().get(1).isShot()) {
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
	
	/**
	 * Draw a weapon.
	 * @param graphics the Graphics object to draw things on screen
	 * @param weaponNumber the number of the weapon to draw
	 */
	private void drawWeapon(Graphics graphics, int weaponNumber) {
		Weapon weapon = weaponList.get(weaponNumber);
		RND.getInstance().drawColor(new RenderOptions(graphics, lasertipimageN, lasertipimageA,
				weapon.getX() - LASER_X_DEVIATION, weapon.getY() - LASER_TIP_Y_DEVIATION,
				mainGame.getColor()));

		 RND.getInstance().drawColor(new RenderOptions(graphics, laserbeamimageN, laserbeamimageA,
				weapon.getX() - LASER_X_DEVIATION,
				weapon.getRectangle().getMinY() + LASER_BEAM_Y_DEVIATION, mainGame.getColor()),
				weapon.getX() + LASER_BEAM_X2_DEVIATION, weapon.getRectangle().getMaxY(), 0, 0,
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

	/**
	 * @return the laserbeamimageN
	 */
	public Image getLaserbeamimageN() {
		return laserbeamimageN;
	}
	
	/**
	 * @return the laserbeamimageA
	 */
	public Image getLaserbeamimageA() {
		return laserbeamimageA;
	}

	/**
	 * @param laserbeamimageN the laserbeamimage to set
	 * @param laserBeamImageA second image to set
	 */
	public void setLaserbeamimage(Image laserbeamimageN, Image laserBeamImageA) {
		this.laserbeamimageN = laserbeamimageN;
		this.laserbeamimageA = laserBeamImageA;
	}

	/**
	 * @return the lasertipimageN
	 */
	public Image getLasertipimageN() {
		return lasertipimageN;
	}
	
	/**
	 * @return the lasertipimageA
	 */
	public Image getLasertipimageA() {
		return lasertipimageA;
	}

	/**
	 * @param lasertipimageN the lasertipimageN to set
	 * @param lasertipimageA the lasertipimageN to set
	 */
	public void setLasertipimage(Image lasertipimageN, Image lasertipimageA) {
		this.lasertipimageN = lasertipimageN;
		this.lasertipimageA = lasertipimageA;
	}
	
	
	

}
