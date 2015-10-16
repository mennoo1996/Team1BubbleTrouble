package lan;

import guigame.GameState;
import logic.Coin;
import logic.FloatingScore;

import commands.AddDroppedCoinCommand;
import commands.AddFloatingScoreCommand;
import commands.CommandQueue;
import commands.RemoveDroppedCoinCommand;

/**
 * Class to process the LAN multiplayer management of coins.
 * @author alexandergeenen
 */
public class ClientCoinsHelper {

    private GameState gameState;
    private CommandQueue commandQueue;

    private static final int THREE = 3;

    /**
     * Initialize an instance of the coins message processor.
     * @param gameState Game state to alter based on messages.
     */
    public ClientCoinsHelper(GameState gameState) {
        this.gameState = gameState;
        commandQueue = CommandQueue.getInstance();
    }

    /**
     * Process an incoming message about a coin.
     * @param message the message to process
     */
    public void coinMessage(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        if (stringList[THREE].equals("ADD")) {
            addCoin(stringList);
        } else if (stringList[THREE].equals("DICTATE")) {
            dictateCoin(stringList);
        } else if (stringList[THREE].equals("GRANT")) {
            grantCoin(stringList);
        }
    }

    /**
     * Add a coin to the level, client-sided.
     * @param stringList description of the coin
     */
    private void addCoin(String[] stringList) {
		commandQueue.addCommand(new AddDroppedCoinCommand(
				gameState.getItemsHelper().getDroppedCoins(), 
				new Coin(Float.parseFloat(stringList[0]),
				Float.parseFloat(stringList[1]), Boolean.parseBoolean(stringList[2]))));
    }

    /**
     * Dictate that a coin goes to the host player.
     * @param stringList description of the coin
     */
    private void dictateCoin(String[] stringList) {
        synchronized (gameState.getItemsHelper().getDroppedCoins()) {
            for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
                if (coin.getxId() == Float.parseFloat(stringList[0])
                        && coin.getyId() == Float.parseFloat(stringList[1])) {
    				commandQueue.addCommand(new RemoveDroppedCoinCommand(
    						gameState.getItemsHelper().getDroppedCoins(), coin));
    				commandQueue.addCommand(new AddFloatingScoreCommand(
    						gameState.getInterfaceHelper().getFloatingScores(), 
    						new FloatingScore(coin)));
                }
            }
        }
    }

    /**
     * Grant a coin to a player.
     * @param stringList the IDs of the coins
     */
    private void grantCoin(String[] stringList) {
        synchronized (gameState.getItemsHelper().getDroppedCoins()) {
            for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
                if (coin.getxId() == Float.parseFloat(stringList[0])
                        && coin.getyId() == Float.parseFloat(stringList[1])) {
    				commandQueue.addCommand(new RemoveDroppedCoinCommand(
    						gameState.getItemsHelper().getDroppedCoins(), coin));
    				commandQueue.addCommand(new AddFloatingScoreCommand(
    						gameState.getInterfaceHelper().getFloatingScores(), 
    						new FloatingScore(coin)));
                }
            }
        }
    }
}
