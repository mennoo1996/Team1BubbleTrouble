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
    public void update(GameState gs, float deltaFloat) {
        boolean stayVisible = true;
        final int offsetY = 10;
        final int offsetFloor = 7;
        if (!this.isVisible()) {
            stayVisible = false;
        }
        super.update(gs, deltaFloat);
        if (this.getY() <= gs.getCeiling().getHeight() && stayVisible) {
            this.setY(gs.getCeiling().getHeight() - offsetY);
            this.setHeight(gs.getFloor().getMinY() - gs.getCeiling().getHeight() + offsetFloor);
            this.setVisible(true);
        }
    }
}
