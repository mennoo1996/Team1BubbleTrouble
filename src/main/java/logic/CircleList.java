package logic;

import guimenu.RND;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Class containing a list of bouncingcircles.
 * @author Bart
 *
 */
public class CircleList {
	
	private ArrayList<BouncingCircle> circles;
	private int highestID;
	
	private static Image[] ballsImagesN;
	private static Image[] ballsImagesA;

	private static final int BALL_IMAGE_THREE = 3;
	private static final int BALL_IMAGE_FOUR = 4;
	private static final int BALL_IMAGE_FIVE = 5;
	private static final int AMOUNT_OF_BALLS = 6;
	private static final int CIRCLE_DRAW_OFFSET = 13;
	private static final int MINIMUM_RADIUS = 10;
	private static final int RADIUS_2 = 20;
	private static final int RADIUS_3 = 30;
	private static final int RADIUS_4 = 45;
	private static final int RADIUS_5 = 65;
	private static final int RADIUS_6 = 90;
	
	/**
	 * @param circles the circles of this circlelist.
	 */
	public CircleList(ArrayList<BouncingCircle> circles) {
		this.circles = circles;
		highestID = 0;
		
		for (BouncingCircle circle : circles) {
			higherID(circle);
		}
	}
	
	/**
	 * Load all circle images before rendering.
	 */
	public static void loadImages() {
		ballsImagesN = new Image[AMOUNT_OF_BALLS];
		try {
			ballsImagesN[0] = new Image("resources/images_Balls/Ball_90_Norm.png");
			ballsImagesN[1] = new Image("resources/images_Balls/Ball_65_Norm.png");
			ballsImagesN[2] = new Image("resources/images_Balls/Ball_45_Norm.png");
			ballsImagesN[BALL_IMAGE_THREE] = new Image("resources/images_Balls/Ball_30_Norm.png");
			ballsImagesN[BALL_IMAGE_FOUR] = new Image("resources/images_Balls/Ball_20_Norm.png");
			ballsImagesN[BALL_IMAGE_FIVE] = new Image("resources/images_Balls/Ball_10_Norm.png");
			ballsImagesA = new Image[AMOUNT_OF_BALLS];
			ballsImagesA[0] = new Image("resources/images_Balls/Ball_90_Add.png");
			ballsImagesA[1] = new Image("resources/images_Balls/Ball_65_Add.png");
			ballsImagesA[2] = new Image("resources/images_Balls/Ball_45_Add.png");
			ballsImagesA[BALL_IMAGE_THREE] = new Image("resources/images_Balls/Ball_30_Add.png");
			ballsImagesA[BALL_IMAGE_FOUR] = new Image("resources/images_Balls/Ball_20_Add.png");
			ballsImagesA[BALL_IMAGE_FIVE] = new Image("resources/images_Balls/Ball_10_Add.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the ID of the given circle is higher as the highest ID in the CircleList,
	 * if so it changes the highestID.
	 * @param circle the circle of which the ID to check
	 */
	public void higherID(BouncingCircle circle) {
		if (circle.getId() > highestID) {
			highestID = circle.getId();
		}
	}
	
	/**
	 * Set the multiplier of all circles.
	 * @param multiplier - the multiplier to set
	 */
	public void setAllMultipliers(float multiplier) {
		for (BouncingCircle circle : circles) {
			circle.setMultiplier(multiplier);
		}
	}
	
	/**
	 * Method to add a new circle to the list.
	 * @param circle	the circle to add
	 */
	public void add(BouncingCircle circle) {
		higherID(circle);
		circles.add(circle);
	}
	
	/**
	 * method that returns the circle with the given id, if exists and null otherwise.
	 * @param id	the id to match
	 * @return		the matched circle
	 */
	public BouncingCircle getCircleForID(int id) {
		for (BouncingCircle circle : circles) {
			if (circle.getId() == id) {
				return circle;
			}
		}
		
		return null;
	}
	
	/**
	 * Return index of a circle with the given id.
	 * @param id	the id to match
	 * @return		the index
	 */
	public int getIndexForCircleWithID(int id) {
		BouncingCircle circle = getCircleForID(id);
		
		return circles.indexOf(circle);
	}
	
	/**
	 * Method that returns a new unused id.
	 * @return	the new id
	 */
	public int getNewID() {
		highestID++;
		return highestID;
	}
	
	/**
	 * @return the circles
	 */
	public ArrayList<BouncingCircle> getCircles() {
		return circles;
	}
	
	/**
	 * @param circles the circles to set
	 */
	public void setCircles(ArrayList<BouncingCircle> circles) {
		this.circles = circles;
	}
	
	/**
	 * Render all the circles in the circlelist to the screen.
	 * @param graphics context to draw in.
	 * @param color to draw circles with.
	 */
	public void drawCircles(Graphics graphics, Color color) {
		for (BouncingCircle circle : circles) { 
			int r = (int) circle.getRadius(), offset = CIRCLE_DRAW_OFFSET;
			final float xPosition = circle.getMinX() - offset;
			final float yPosition = circle.getMinY() - offset;
			switch (r) {
			case(RADIUS_6) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[0], ballsImagesA[0], xPosition, yPosition, color)); break;
			case(RADIUS_5) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[1], ballsImagesA[1],
					xPosition, yPosition, color)); break;
			case(RADIUS_4) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[2], ballsImagesA[2], xPosition, yPosition, color)); break;
			case(RADIUS_3) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[BALL_IMAGE_THREE], ballsImagesA[BALL_IMAGE_THREE],
					xPosition, yPosition, color)); break;
			case(RADIUS_2) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[BALL_IMAGE_FOUR], ballsImagesA[BALL_IMAGE_FOUR],
					xPosition, yPosition, color)); break;
			case(MINIMUM_RADIUS) : RND.getInstance().drawColor(new RenderOptions(graphics, 
					ballsImagesN[BALL_IMAGE_FIVE], ballsImagesA[BALL_IMAGE_FIVE],
					xPosition, yPosition, color)); break;
			default:
				try {
					throw new SlickException("Radius was not one of the supported");
				} catch (SlickException e) {
					e.printStackTrace(); }
			}
		}
	}

}
