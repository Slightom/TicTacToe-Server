package interfaces;

import enums.FieldStateEnum;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote {

    public String getName() throws RemoteException;

    public void gameResult(String info) throws RemoteException;

    public void updateBoard(FieldStateEnum[] fieldState) throws RemoteException;

    public void unlockPlayer() throws RemoteException;
}
