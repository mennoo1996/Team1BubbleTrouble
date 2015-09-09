/**
 * Created by alexandergeenen on 09/09/15.
 */
public class Spiky extends Weapon {

    public Spiky(float x, float y, float laserSpeed, float laserWidth) {
        super(x, y, laserSpeed, laserWidth);
    }

    @Override
    public void update(GameState gs, float deltaFloat) {
        boolean stayVisible = true;
        if (!this.isVisible()) {
            stayVisible = false;
        }
        super.update(gs, deltaFloat);
        if (this.getY() <= gs.ceiling.getHeight() && stayVisible) {
            this.setY(gs.ceiling.getHeight() - 10);
            this.setHeight(gs.floor.getMinY() - gs.ceiling.getHeight() + 7);
            this.setVisible(true);
        }
    }
}
