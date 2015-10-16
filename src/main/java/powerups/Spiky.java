package powerups;
import logic.Weapon;

import org.newdawn.slick.geom.Rectangle;

/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Spiky extends Weapon {

    /**
     * Create a new Spiky weapon.
     * @param x the x coord
     * @param y the y coord
     * @param laserSpeed the speed of the spiky
     * @param laserWidth the width of the spiky
     */
    public Spiky(float x, float y, float laserSpeed, float laserWidth) {
        super(x, y, laserSpeed, laserWidth);
    }

    @Override
    public void update(Rectangle ceiling, Rectangle floor, float deltaFloat) {
    	boolean stayVisible = true;
    	if (!this.isVisible()) {
    		stayVisible = false; // DO NOT REFACTOR THIS AWAY AGAIN. EVER.
    	}						 // NO MATTER HOW DUMB IT LOOKS
        final int offsetY = 10;
        final int offsetFloor = 7;
        super.update(ceiling, floor, deltaFloat);
        if (this.getY() <= ceiling.getHeight() && stayVisible) {
            this.setY(ceiling.getHeight() - offsetY);
            this.setHeight(floor.getMinY() - ceiling.getHeight() + offsetFloor);
            this.setVisible(true);
        }
    }
}