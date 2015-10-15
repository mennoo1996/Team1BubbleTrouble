package lan;

import guigame.GameState;
import guimenu.MainGame;
import logic.FloatingScore;
import powerups.Powerup;

import java.util.ArrayList;

/**
 * Created by alexandergeenen on 15/10/15.
 */
public class ClientPowerupsHelper {

    private Client client;
    private GameState gameState;
    private MainGame mainGame;

    private static final int THREE = 3;

    public ClientPowerupsHelper(Client client, GameState gameState, MainGame mainGame) {
        this.client = client;
        this.gameState = gameState;
        this.mainGame = mainGame;
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
            switch (stringList[2]) {
                case "SHIELD":
                    type = Powerup.PowerupType.SHIELD; // shield added

                    break;
                case "SPIKY":
                    type = Powerup.PowerupType.SPIKY; // spiky added

                    break;
                case "INSTANT":
                    type = Powerup.PowerupType.INSTANT; // inst added

                    break;
                case "HEALTH":
                    type = Powerup.PowerupType.HEALTH; // health added

                    break;
                case "FREEZE":
                    type = Powerup.PowerupType.FREEZE; // freeze added

                    break;
                case "SLOW":
                    type = Powerup.PowerupType.SLOW; // slow added

                    break;
                case "FAST":
                    type = Powerup.PowerupType.FAST; // fast added

                    break;
                case "RANDOM":
                    type = Powerup.PowerupType.RANDOM; // random added

                    break;
            }
            if (type != null) {
                gameState.getItemsHelper().getDroppedPowerups().add(
                        new Powerup(Float.parseFloat(stringList[0]),
                                Float.parseFloat(stringList[1]), type));
            }
        }
    }

    /**
     * Remove a powerup from the level, and give it to the host player.
     * @param stringList information on powerup
     */
    private void dictatePowerup(String[] stringList) {
        Powerup.PowerupType type = Powerup.PowerupType.SHIELD;
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
        ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
        for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
            if (powerup.getxId() == Float.parseFloat(stringList[0])
                    && powerup.getyId() == Float.parseFloat(stringList[1])) {
                poweruplist.add(powerup);
                synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
                    gameState.getInterfaceHelper().getFloatingScores().
                            add(new FloatingScore(powerup));
                }
                mainGame.getPlayerList().getPlayers().get(0).addPowerup(type);
            }
        } gameState.getItemsHelper().getDroppedPowerups().removeAll(poweruplist);
    }

    /**
     * Grant a powerup to the client's player.
     * @param stringList the IDs of the powerups
     */
    private void grantPowerup(String[] stringList) {
        switch (stringList[2]) {
            case "SHIELD":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.SHIELD);
                break;
            case "SPIKY":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.SPIKY);
                break;
            case "INSTANT":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.INSTANT);
                break;
            case "HEALTH":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.HEALTH);
                break;
            case "FREEZE":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.FREEZE);
                break;
            case "SLOW":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.SLOW);
                break;
            case "FAST":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.FAST);
                break;
            case "RANDOM":
                mainGame.getPlayerList().getPlayers().get(1).addPowerup(Powerup.PowerupType.RANDOM);
                break;
        }
        synchronized (gameState.getItemsHelper().getDroppedPowerups()) {
            ArrayList<Powerup> poweruplist = new ArrayList<Powerup>();
            for (Powerup powerup : gameState.getItemsHelper().getDroppedPowerups()) {
                if (powerup.getxId() == Float.parseFloat(stringList[0])
                        && powerup.getyId() == Float.parseFloat(stringList[1])) {
                    poweruplist.add(powerup);
                    synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
                        gameState.getInterfaceHelper().getFloatingScores().
                                add(new FloatingScore(powerup));
                    } }
            }
            gameState.getItemsHelper().getDroppedPowerups().removeAll(poweruplist); }
    }
}
