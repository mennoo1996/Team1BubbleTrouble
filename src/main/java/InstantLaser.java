/**
 * Created by alexandergeenen on 09/09/15.
 */
public class InstantLaser extends Weapon {
    private static final float INSTANT_SPEED = 8000f;
    public InstantLaser(float x, float y, float laserWidth) {
        super(x, y, INSTANT_SPEED, laserWidth);
    }
}
