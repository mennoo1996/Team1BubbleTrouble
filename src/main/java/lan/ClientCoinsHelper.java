package lan;

import guigame.GameState;
import guimenu.MainGame;
import logic.Coin;
import logic.FloatingScore;

import java.util.ArrayList;

/**
 * Class to process the LAN multiplayer management of coins.
 * @author alexandergeenen
 */
public class ClientCoinsHelper {

    private Client client;
    private GameState gameState;

    private static final int THREE = 3;

    public ClientCoinsHelper(Client client, GameState gameState) {
        this.client = client;
        this.gameState = gameState;
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
        synchronized (gameState.getItemsHelper().getDroppedCoins()) {
            gameState.getItemsHelper().getDroppedCoins().add(
                    new Coin(Float.parseFloat(stringList[0]),
                            Float.parseFloat(stringList[1]), Boolean.parseBoolean(stringList[2])));
        }
    }

    /**
     * Dictate that a coin goes to the host player.
     * @param stringList description of the coin
     */
    private void dictateCoin(String[] stringList) {
        ArrayList<Coin> coinlist = new ArrayList<Coin>();
        synchronized (gameState.getItemsHelper().getDroppedCoins()) {
            for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
                if (coin.getxId() == Float.parseFloat(stringList[0])
                        && coin.getyId() == Float.parseFloat(stringList[1])) {
                    coinlist.add(coin);
                    synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
                        gameState.getInterfaceHelper().getFloatingScores().
                                add(new FloatingScore(coin));
                    }
                }
            }
            gameState.getItemsHelper().getDroppedCoins().removeAll(coinlist);
        }
    }

    /**
     * Grant a coin to a player.
     * @param stringList the IDs of the coins
     */
    private void grantCoin(String[] stringList) {
        ArrayList<Coin> coinlist = new ArrayList<Coin>();
        synchronized (gameState.getItemsHelper().getDroppedCoins()) {
            for (Coin coin : gameState.getItemsHelper().getDroppedCoins()) {
                if (coin.getxId() == Float.parseFloat(stringList[0])
                        && coin.getyId() == Float.parseFloat(stringList[1])) {
                    coinlist.add(coin);
                    synchronized (gameState.getInterfaceHelper().getFloatingScores()) {
                        gameState.getInterfaceHelper().getFloatingScores().
                                add(new FloatingScore(coin));
                    }
                }
            }
            gameState.getItemsHelper().getDroppedCoins().removeAll(coinlist);
        }
    }
}
