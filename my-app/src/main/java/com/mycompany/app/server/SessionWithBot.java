package com.mycompany.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.mycompany.app.board.GameManager;
import com.mycompany.app.board.StoneColor;
import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;
import com.mycompany.app.board.exceptions.NotYourTurnException;
import com.mycompany.app.database.IDataBaseManager;

public class SessionWithBot implements Session {
    private Socket player;
    private DataInputStream input;
    private DataOutputStream output;
    private IDataBaseManager dataBaseManager;
    private int size;
    private boolean isPlayerWhite;
    private int playerNr;
    private StoneColor botColor;
    private char[][] passBoard;

    private boolean gamesON;
    private boolean passed = false;

    @Override
    public void run() {
        System.out.println("Session run");
        startGame();
        System.out.println("Game started");
        try {
            output = new DataOutputStream(player.getOutputStream());
            input = new DataInputStream(player.getInputStream());
            output.writeBoolean(!isPlayerWhite);
            input.readBoolean();
            
            if (isPlayerWhite) {
                char[][] board;
                if (GameManager.getInstance().addStoneAsBot(botColor)) {
                    board = GameManager.getInstance().getSimplifiedBoard();
                } else {
                    board = passBoard;
                }
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        output.writeChar(board[i][j]);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        while (gamesON) {
            try {
                output.writeBoolean(false);
                int x = input.readInt();
                int y = input.readInt();
                if (x == -2) {
                    dataBaseManager.saveGame(size, -1, isPlayerWhite);
                    dataBaseManager.saveMove("FF");

                    break;
                }
                GameManager.getInstance().addStone(x, y, playerNr);
                if (x == -1 && passed) {
                    dataBaseManager.saveMove("PASS");
                    output.writeBoolean(false);
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            output.writeChar('\1');
                        }
                    }
                    break;
                }
                if (x == -1 && !passed) {
                    dataBaseManager.saveMove("PASS");
                    passed = true;
                    output.writeBoolean(false);
                    char[][] board;
                    board = GameManager.getInstance().getSimplifiedBoard();
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            output.writeChar(board[i][j]);
                        }
                    }

                    if (GameManager.getInstance().addStoneAsBot(botColor)) {
                        board = GameManager.getInstance().getSimplifiedBoard();
                    } else {
                        board = passBoard;
                    }
                    board = GameManager.getInstance().getSimplifiedBoard();
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            output.writeChar(board[i][j]);
                        }
                    }
                    continue;
                }
                passed = false;
                System.out.println("Stone added");
                dataBaseManager.saveMove(x + ":" + y);
                output.writeBoolean(false);
                char[][] board = GameManager.getInstance().getSimplifiedBoard();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        output.writeChar(board[i][j]);
                    }
                }

                if (GameManager.getInstance().addStoneAsBot(botColor)) {
                    board = GameManager.getInstance().getSimplifiedBoard();
                } else {
                    board = passBoard;
                }
                board = GameManager.getInstance().getSimplifiedBoard();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        output.writeChar(board[i][j]);
                    }
                }
            } catch (IncorrectStonePlacementException | NotYourTurnException ex) {
                try {
                    output.writeBoolean(true);
                    output.writeBoolean(true);
                    output.writeInt(ex.getMessage().getBytes().length);
                    output.writeBytes(ex.getMessage());
                } catch (IOException ioex) {
                    System.err.println(ioex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                break;
            }
        }
    }

    @Override
    public void startGame() {
        GameManager.getInstance().startGame(size);
    }

    @Override
    public synchronized void addStone(int x, int y, int player) {
        //Empty
    }

    @Override
    public synchronized void setProposition(char[][] proposition) {
        //Empty
    }

    @SuppressWarnings("PMD.EmptyCatchBlock")
    @Override
    public void endGame() {
        gamesON = false;
        try {
            player.close();
        } catch (IOException ex) {}
    }
    
    public SessionWithBot(Socket player, int size, IDataBaseManager dataBaseManager)
    {
        this.player = player;
        this.dataBaseManager = dataBaseManager;
        this.size = size;
        gamesON = true;
        if (Math.random() < 0.5) {
            isPlayerWhite = false;
            playerNr = 0;
            botColor = StoneColor.WHITE;
        } else {
            isPlayerWhite = true;
            playerNr = 1;
            botColor = StoneColor.BLACK;
        }
        passBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                passBoard[i][j] = '\n';
            }
        }
    }
}
