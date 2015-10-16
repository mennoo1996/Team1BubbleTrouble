package lan;

import guigame.GameState;
import guimenu.MainGame;
import logic.FloatingScore;
import powerups.Powerup;
import powerups.Powerup.PowerupType;

import commands.AddDroppedPowerupCommand;
import commands.AddFloatingScoreCommand;
import commands.AddPowerupToPlayerCommand;
import commands.CommandQueue;
import commands.RemoveDroppedPowerupCommand;

/**
 * Created by alexandergeenen on 15/10/15.
 */
public class ClientPowerupsHelper {

	private GameState gameState;
    private MainGame mainGame;
    private CommandQueue commandQueue;

    private static final int THREE = 3;

    public ClientPowerupsHelper(GameState gameState, MainGame mainGame) {
        this.gameState = gameState;
        this.mainGame = mainGame;
        commandQueue = CommandQueue.getInstance();
    }

    /**
     * Process an incoming message about a powerup.
     * @param message the message to process
     */
    public void powerupMessage(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        if (stringList[THREE].equals("ADD")) {
            addPowerup(stringList);
        } else if (stringList[THREE].equals("DICTATE")) {
            dictatePowerup(stringList);
        } else if (stringList[THREE].equals("GRANT")) {
            grantPowerup(stringList);
        }
    }

    /**
     * Add a powerup to the level on the client side.
     * @param stringList information on powerup
     */
    private void addPowerup(String[] stringList) {
        synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
            Powerup.PowerupType type = Powerup.PowerupType.SHIELD;
            type = getPowerupType(stringList, type);
            if (type != null) {
            	commandQueue.addCommand(new AddDroppedPowerupCommand(
    					gameState.getItemsHelper().getDroppedPowerups(), 
    					new Powerup(Float.parseFloat(stringList[0]),
    					Float.parseFloat(stringList[1]), type)));
            }
        }
    }

    /**
     * Remove a powerup from the level, and give it to the host player.
     * @param stringList information on powerup
     */
    private void dictatePowerup(String[] stringList) {
        Powerup.PowerupType type = Powerup.PowerupType.SHIELD;
        type = getPowerupType(stringList, type);
        for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
            if (powerup.getxId() == Float.parseFloat(stringList[0])
                    && powerup.getyId() == Float.parseFloat(stringList[1])) {
            	commandQueue.addCommand(new RemoveDroppedPowerupCommand(
						gameState.getItemsHelper().getDroppedPowerups(), powerup));
				commandQueue.addCommand(new AddFloatingScoreCommand(
						gameState.getInterfaceHelper().getFloatingScores(), 
						new FloatingScore(powerup)));
				commandQueue.addCommand(new AddPowerupToPlayerCommand(
						mainGame.getPlayerList().getPlayers().get(0), type));
            }
        }
    }

	private Powerup.PowerupType getPowerupType(String[] stringList, Powerup.PowerupType type) {
		switch (stringList[2]) {
            case "SHIELD":
                type = Powerup.PowerupType.SHIELD;
                break;
            case "SPIKY":
                type = Powerup.PowerupType.SPIKY;
                break;
            case "INSTANT":
                type = Powerup.PowerupType.INSTANT;
                break;
            case "HEALTH":
                type = Powerup.PowerupType.HEALTH;
                break;
            case "FREEZE":
                type = Powerup.PowerupType.FREEZE;
                break;
            case "SLOW":
                type = Powerup.PowerupType.SLOW;
                break;
            case "FAST":
                type = Powerup.PowerupType.FAST;
                break;
            case "RANDOM":
                type = Powerup.PowerupType.RANDOM;
                break;
        }
		return type;
	}

    /**
     * Grant a powerup to the client's player.
     * @param stringList the IDs of the powerups
     */
    private void grantPowerup(String[] stringList) {
        switch (stringList[2]) {
            case "SHIELD":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.SHIELD));
                break;
            case "SPIKY":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.SPIKY));
                break;
            case "INSTANT":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.INSTANT));
                break;
            case "HEALTH":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.HEALTH));
                break;
            case "FREEZE":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.FREEZE));
                break;
            case "SLOW":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.SLOW));
                break;
            case "FAST":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.FAST));
                break;
            case "RANDOM":
    			commandQueue.addCommand(new AddPowerupToPlayerCommand(
    					mainGame.getPlayerList().getPlayers().get(1), PowerupType.RANDOM));
                break;
        }
        synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
            for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
                if (powerup.getxId() == Float.parseFloat(stringList[0])
                        && powerup.getyId() == Float.parseFloat(stringList[1])) {
                	commandQueue.addCommand(new RemoveDroppedPowerupCommand(
    						gameState.getItemsHelper().getDroppedPowerups(), powerup));
    				commandQueue.addCommand(new AddFloatingScoreCommand(
    						gameState.getInterfaceHelper().getFloatingScores(), 
    						new FloatingScore(powerup)));
                }
            }
        }
    }
}
