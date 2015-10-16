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
 * Class used to process powerup messages received by a client.
 * @author alexandergeenen
 */
public class ClientPowerupsHelper {

	private GameState gameState;
    private MainGame mainGame;
    private CommandQueue commandQueue;

    private static final int THREE = 3;

    /**
     * Initialize an instance of the powerups message processor.
     * @param gameState Game state to alter
     * @param mainGame Main Game to alter
     */
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
            type = getPowerupType(stringList[2], type);
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
        type = getPowerupType(stringList[2], type);
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

    /**
     * Get the proper powerup type.
     * @param s String to parse
     * @param type Initial type
     * @return Correct powerup type
     */
	private Powerup.PowerupType getPowerupType(String s, Powerup.PowerupType type) {
		switch (s) {
            case "SHIELD":
                type = Powerup.PowerupType.SHIELD; break;
            case "SPIKY":
                type = Powerup.PowerupType.SPIKY; break;
            case "INSTANT":
                type = Powerup.PowerupType.INSTANT; break;
            case "HEALTH":
                type = Powerup.PowerupType.HEALTH; break;
            case "FREEZE":
                type = Powerup.PowerupType.FREEZE; break;
            case "SLOW":
                type = Powerup.PowerupType.SLOW; break;
            case "FAST":
                type = Powerup.PowerupType.FAST; break;
            case "RANDOM":
                type = Powerup.PowerupType.RANDOM; break;
            default:
                break;
        }
		return type;
	}

    /**
     * Grant a powerup to the client's player.
     * @param stringList the IDs of the powerups
     */
    private void grantPowerup(String[] stringList) {
        parsePowerupCommand(stringList[2]);
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

    /**
     * Adds correct powerup to player.
     * @param s Powerup string to parse
     */
    private void parsePowerupCommand(String s) {
        PowerupType powerupType = PowerupType.SHIELD;
        powerupType = getPowerupType(s, powerupType);
        commandQueue.addCommand(new AddPowerupToPlayerCommand(
                mainGame.getPlayerList().getPlayers().get(1), powerupType
                ));
    }
}
