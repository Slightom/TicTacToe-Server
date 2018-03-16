package interfaces;

import enums.FieldStateEnum;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IKKServer extends Remote {

    void updateBoard(FieldStateEnum[] fieldState) throws RemoteException;

    int addPlayer(IPlayer player) throws RemoteException;

    void removePlayer(IPlayer player) throws RemoteException;
    
    void notifyPlayers() throws RemoteException;
    
    void playAgain() throws RemoteException;
    
}
