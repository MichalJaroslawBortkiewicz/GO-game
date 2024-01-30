package com.mycompany.app.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.mycompany.app.board.GameManager;
import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;
import com.mycompany.app.board.exceptions.NotYourTurnException;

public class TwoPlayerSession implements Session {
    private Socket firstPlayer;
    private Socket secondPlayer;
    private DataOutputStream[] playerStream;
    private int size;

    private boolean gamesON;
    private boolean passed = false;
    private Receiver r1;
    private Receiver r2;

    private int x;
    private int y;
    private int player;

    @Override
    public void run() {
        System.out.println("Session run");
        startGame();
        System.out.println("Game started");
        try {
            r1 = new Receiver(this, firstPlayer, 0);
            Thread thread = new Thread(r1);
            thread.start();
            r2 = new Receiver(this, secondPlayer, 1);
            thread = new Thread(r2);
            thread.start();
            playerStream[0] = new DataOutputStream(firstPlayer.getOutputStream());
            playerStream[1] = new DataOutputStream(secondPlayer.getOutputStream());
            playerStream[0].writeBoolean(true);
            playerStream[1].writeBoolean(false);
            System.out.println("Each player get if he is first");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        while (gamesON) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {}
            System.out.println("Session notified");
            try {
                if (x == -2) {
                    int currentPlayer = GameManager.getInstance().isWhitePlays() ? 1 : 0;
                    if (currentPlayer == player) {
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                playerStream[1-currentPlayer].writeChar('\0');
                            }
                        }
                    } else {
                        playerStream[currentPlayer].writeBoolean(true);
                    }
                    break;
                }
                GameManager.getInstance().addStone(x, y, player);
                if (x == -1 && passed) {
                    playerStream[player].writeBoolean(false);
                    playerStream[player].writeBoolean(false);
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            playerStream[1-player].writeChar('\1');
                            playerStream[player].writeChar('\1');
                        }
                    }
                    continue;
                }
                if (x == -1 && !passed) {
                    passed = true;
                    playerStream[player].writeBoolean(false);
                    playerStream[player].writeBoolean(false);
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            playerStream[1-player].writeChar('\n');
                            playerStream[player].writeChar('\n');
                        }
                    }
                    continue;
                }
                passed = false;
                System.out.println("Stone added");
                playerStream[player].writeBoolean(false);
                playerStream[player].writeBoolean(false);
                char[][] board = GameManager.getInstance().getSimplifiedBoard();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        playerStream[0].writeChar(board[i][j]);
                        playerStream[1].writeChar(board[i][j]);
                    }
                }
            } catch (IncorrectStonePlacementException | NotYourTurnException ex) {
                try {
                    playerStream[player].writeBoolean(true);
                    playerStream[player].writeBoolean(true);
                    playerStream[player].writeInt(ex.getMessage().getBytes().length);
                    playerStream[player].writeBytes(ex.getMessage());
                } catch (IOException ioex) {
                    System.err.println(ioex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public void startGame() {
        GameManager.getInstance().startGame(size);
    }

    @Override
    public synchronized void addStone(int x, int y, int player) {
        System.out.println("Received move: " + x + " " + y + " from player " + player);
        this.x = x;
        this.y = y;
        this.player = player;
    }

    @SuppressWarnings("PMD.EmptyCatchBlock")
    @Override
    public void endGame() {
        r1.gamesOFF();
        r2.gamesOFF();
        gamesON = false;
        try {
            firstPlayer.close();
            secondPlayer.close(); 
        } catch (IOException ex) {}
    }
    
    public TwoPlayerSession(Socket firstPlayer, Socket secondPlayer, int size)
    {
        if (Math.random() < 0.5) {
            this.firstPlayer = firstPlayer;
            this.secondPlayer = secondPlayer;
        } else {
            this.firstPlayer = secondPlayer;
            this.secondPlayer = firstPlayer;
        }
        playerStream = new DataOutputStream[2];
        this.size = size;
        gamesON = true;
        System.out.println("Two player session created");
    }
}
