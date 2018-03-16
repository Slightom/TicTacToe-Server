package kkserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import interfaces.IPlayer;
import enums.FieldStateEnum;
import enums.GameStatusEnum;
import interfaces.IKKServer;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.swing.JButton;

public class KKServer extends UnicastRemoteObject implements IKKServer {
    
    private FieldStateEnum[] boardStatus;
    private IPlayer player1 = null;
    private IPlayer player2 = null;
    private IPlayer actualPlayer;
    private int readyCounter = 0;
    private GameStatusEnum gameStatus;
    
    protected KKServer() throws RemoteException {
        super();
        player1 = null;
        player2 = null;
    }
    
    @Override
    public void updateBoard(FieldStateEnum[] fieldState) throws RemoteException {
        System.arraycopy(fieldState, 0, boardStatus, 0, 9);
        
        if (actualPlayer == player1) {
            actualPlayer = player2;
        } else {
            actualPlayer = player1;
        }
        actualPlayer.updateBoard(boardStatus);
        checkForWin();
        if (gameStatus != GameStatusEnum.inProgress) {
            notifyPlayers();
        } else {
            actualPlayer.unlockPlayer();
        }
    }
    
    @Override
    public int addPlayer(IPlayer player) throws RemoteException {
        if (player1 == null) {
            player1 = player;
            return 1;
        } else if (player2 == null) {
            player2 = player;
            initGame();
            actualPlayer = player1;
            actualPlayer.unlockPlayer();
            return 2;
        } else {
            return 0;
        }
    }
    
    private void initGame() {
        boardStatus = new FieldStateEnum[9];
        for (int i = 0; i < 9; i++) {
            boardStatus[i] = FieldStateEnum.empty;
        }
        gameStatus = GameStatusEnum.inProgress;
        readyCounter = 0;
    }
    
    @Override
    public void removePlayer(IPlayer player) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void notifyPlayers() throws RemoteException {
        String msg1 = "", msg2 = "";
        switch (gameStatus) {
            case player1Won:
                msg1 = "You won! Bravo! Click 'ok' to play again.";
                msg2 = "You lost :( Click 'ok' to play again.";
                break;
            case player2Won:
                msg2 = "You won! Bravo! CLick 'ok' to play again.";
                msg1 = "You lost :( Click 'ok' to play again.";
                break;
            case nobodyWon:
                msg1 = msg2 = "Nobody won. Click 'ok' to play again.";
                break;
        }
        
        System.out.println(msg1 + " 2: " + msg2 + " gs: " + gameStatus.toString());
        
        MyThread thread = new MyThread();
        thread.run(player1, msg1);
        //player1.gameResult(msg1);
        player2.gameResult(msg2);
    }
    
    public void checkForWin() {
        if (nobodyWon()) {
        } else if (weHaveVictoryHere(0, 3, 6, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(1, 4, 7, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(2, 5, 8, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(0, 1, 2, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(3, 4, 5, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(6, 7, 8, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(0, 4, 8, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(2, 4, 6, FieldStateEnum.X)) {
        } else if (weHaveVictoryHere(0, 3, 6, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(1, 4, 7, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(2, 5, 8, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(0, 1, 2, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(3, 4, 5, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(6, 7, 8, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(0, 4, 8, FieldStateEnum.O)) {
        } else if (weHaveVictoryHere(2, 4, 6, FieldStateEnum.O)) {
        }
    }
    
    private boolean weHaveVictoryHere(int i1, int i2, int i3, FieldStateEnum shape) {
        if (boardStatus[i1] == shape && boardStatus[i2] == shape && boardStatus[i3] == shape) {
            gameStatus = (shape == FieldStateEnum.X ? gameStatus.player1Won : gameStatus.player2Won);
            System.out.println("Player " + (shape.equals("X") ? "1" : "2") + " Won!");
            return true;
        } else {
            return false;
        }
    }
    
    private boolean nobodyWon() {
        int i = 0;
        for (i = 0; i < 9; i++) {
            if (boardStatus[i] == FieldStateEnum.empty) {
                break;
            }
        }
        if (i == 9) {
            gameStatus = gameStatus.nobodyWon;
            System.out.println("Nobody won!");
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void playAgain() throws RemoteException {
        if (++readyCounter == 2) {
            initGame();
            player1.updateBoard(boardStatus);
            player2.updateBoard(boardStatus);
            actualPlayer.unlockPlayer();
        }
    }
    
}

class MyThread extends Thread {
    
    public void run(IPlayer player1, String msg) throws RemoteException {
        player1.gameResult(msg);
    }
}

