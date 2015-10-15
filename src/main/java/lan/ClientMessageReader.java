package lan;

import guimenu.MainGame;
import guimenu.MenuMultiplayerState;
import logic.BouncingCircle;
import logic.CircleList;
import logic.FloatingScore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alexandergeenen on 15/10/15.
 */
public class ClientMessageReader {

    private Client client;
    private ClientPowerupsHelper powerupsHelper;
    private ClientCoinsHelper coinsHelper;
    private MainGame mainGame;

    public ClientMessageReader(Client client, ClientPowerupsHelper powerupsHelper, ClientCoinsHelper coinsHelper, MainGame mainGame) {
        this.client = client;
        this.powerupsHelper = powerupsHelper;
        this.coinsHelper = coinsHelper;
        this.mainGame = mainGame;
    }

    /**
     * Process the commands given by the server.
     */
    public void readServerCommands(BufferedReader reader) {
        try {
            while (reader.ready()) {
                String message = reader.readLine(), message2 = "";
                if (message != null) {
                    message2 = message.trim();
                }
                if (message2.startsWith("NEW")) {
                    newMessage(message2.replaceFirst("NEW", ""));
                } else if (message2.startsWith("SYSTEM")) {
                    systemMessage(message2.replaceFirst("SYSTEM", ""));
                } else if (message2.startsWith("UPDATE")) {
                    updateMessage(message2.replaceFirst("UPDATE", ""));
                } else if (message2.startsWith("CIRCLE")) {
                    circleMessage(message2.replaceFirst("CIRCLE", ""));
                } else if (message2.startsWith("POWERUP")) {
                    this.powerupsHelper.powerupMessage(message2.replaceFirst("POWERUP", ""));
                } else if (message2.startsWith("COIN")) {
                    this.coinsHelper.coinMessage(message2.replaceFirst("COIN", ""));
                } else if (message2.startsWith("PLAYER")) {
                    playerMessage(message2.replaceFirst("PLAYER", ""));
                }
                readServerCommands2(message2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * Continue processing the commands given by the server.
     * @param message2	the message to process
     */
    private void readServerCommands2(String message2) {
        if (message2.startsWith("HEARTBEAT_CHECK")) {
            client.sendMessage("HEARTBEAT_ALIVE");
        } else if (message2.startsWith("LASER")) {
            laserMessage(message2.replaceFirst("LASER", ""));
        } else if (message2.startsWith("FLOATINGSCORE")) {
            floatingMessage(message2.replaceFirst("FLOATINGSCORE", ""));
        } else if (message2.startsWith("SPLIT")) {
            splitMessage(message2.replaceFirst("SPLIT", ""));
        } else if (message2.startsWith("SHUTDOWN")) {
            MenuMultiplayerState multiplayerState = (MenuMultiplayerState)
                    this.mainGame.getState(mainGame.getMultiplayerState());
            multiplayerState.addMessage("Host Quit.");
            mainGame.setSwitchState(mainGame.getMultiplayerState());
            mainGame.killMultiplayer();
        }
        // heartBeat reset
        heartBeatCheck = false;
        timeLastInput = System.currentTimeMillis();
    }

    /**
     * Add a FloatingScore to the list.
     * @param message String containing the FloatingScore to add
     */
    private void floatingMessage(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        gameState.getInterfaceHelper().getFloatingScores().add(new FloatingScore(stringList[2],
                Float.parseFloat(stringList[0]), Float.parseFloat(stringList[1])));
    }


    /**
     * Process a message about a circle.
     * @param message the message to process
     */
    private void circleMessage(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        synchronized (this.circleList) {
            BouncingCircle circle = new BouncingCircle(Float.parseFloat(stringList[0]),
                    Float.parseFloat(stringList[1]), Float.parseFloat(stringList[2]),
                    Float.parseFloat(stringList[THREE]), Float.parseFloat(stringList[FOUR]),
                    Float.parseFloat(stringList[FIVE]), Integer.parseInt(stringList[SEVEN]));
            circle.setMultiplier(Float.parseFloat(stringList[SIX]));
            this.circleList.add(circle);
        }
    }

    /**
     * Process a message about an update.
     * @param message the message to process
     */
    private void updateMessage(String message) {
        String message2 = message.trim();
        if (message2.startsWith("CIRCLELIST")) {
            circleListMessage(message2.replaceFirst("CIRCLELIST", ""));
        } else if (message2.startsWith("REQUIREDLIST")) {
            requiredListMessage(message2.replaceFirst("REQUIREDLIST", ""));
        }

    }

    /**
     * Process a message about the circleList.
     * @param message	the message to process
     */
    private void requiredListMessage(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        int gateNumber = Integer.parseInt(stringList[1]);

        if (stringList[0].equals("START") && !this.editingCircleList) {
            this.requiredList = new ArrayList<BouncingCircle>();
        } else if (stringList[0].equals("END") && this.editingCircleList) {
            gameState.getCirclesHelper().getGateList().get(gateNumber).setRequired(requiredList);
        }
    }

    /**
     * Process a message about the circleList.
     * @param message	the message to process
     */
    private void circleListMessage(String message) {
        String message2 = message.trim();

        if (message2.equals("START") && !this.editingCircleList) {
            this.circleList = new ArrayList<BouncingCircle>();
            this.editingCircleList = true;
        } else if (message2.equals("END") && this.editingCircleList) {
            this.editingCircleList = false;
            synchronized (gameState.getCirclesHelper().getCircleList()) {
                gameState.getCirclesHelper().setCircleList(new CircleList(circleList));
            }
        }
    }

    /**
     * Process a new message.
     * @param message the message to process
     */
    private void newMessage(String message) {
        String message2 = message.trim();
        if (message2.startsWith("PLAYERLOCATION")) {
            playerLocation(message2.replaceFirst("PLAYERLOCATION", ""));
        } else if (message2.startsWith(LASER)) {
            newLaserMessage(message2.replaceFirst(LASER, ""));
        }
    }

    /**
     * Process a message about the location of a player.
     * @param message the message to process
     */
    private void playerLocation(String message) {
        String message2 = message.trim();
        String[] stringList = message2.split(" ");
        int id = Integer.parseInt(stringList[0]);
        float x = Float.parseFloat(stringList[1]);
        float y = Float.parseFloat(stringList[2]);

        mainGame.getPlayerList().getPlayers().get(id).setX(x);
        mainGame.getPlayerList().getPlayers().get(id).setY(y);
    }

    /**
     * Process a message about the system.
     * @param message the message to process
     */
    private void systemMessage(String message) {
        String message2 = message.trim();
        if (message2.startsWith("LEVEL")) {
            levelMessage(message2.replaceFirst("LEVEL", ""));
        } else if (message2.startsWith("COUNTIN")) {
            countinMessage(message2.replaceFirst("COUNTIN", ""));
        } else if (message2.startsWith("PAUSE")) {
            pauseMessage(message2.replaceFirst("PAUSE", ""));
        } else if (message2.startsWith("LIVES")) {
            livesMessage(message2.replaceFirst("LIVES", ""));
        }
    }

    /**
     * Process message about lives.
     * @param message	the message to process
     */
    private void livesMessage(String message) {
        String message2 = message.trim();

        int lives = Integer.parseInt(message2);
        mainGame.setLifeCount(lives);
        mainGame.getPlayerList().setDied(false);
    }

    /**
     * Process a message about the coutnin.
     * @param message	the message to process
     */
    private void countinMessage(String message) {
        String message2 = message.trim();
        if (message2.equals("STARTED")) {
            gameState.getLogicHelper().setCountinStarted(true);
        }
    }


    /**
     * Process a message about the level.
     * @param message the message to process
     */
    private void levelMessage(String message) {
        String message2 = message.trim();
        if (message2.equals("STARTED")) {
            gameState.getLogicHelper().setLevelStarted(true);
            gameState.getLogicHelper().setCountinStarted(false);
        } else if (message2.equals("RESTART")) {
            // force override life, level, score etc. Just. In. Case. someone forgets.
            mainGame.resetLifeCount();
            mainGame.resetLevelCount();
            mainGame.setScore(0);
            mainGame.setSwitchState(mainGame.getGameState());
        }
    }
}
