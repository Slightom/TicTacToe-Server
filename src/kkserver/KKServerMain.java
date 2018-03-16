package kkserver;

import interfaces.IPlayer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class KKServerMain{

    public static void main(String[] args) {

        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            //System.setProperty("java.rmi.server.codebase","file:C:\\Users\\Slightom\\Documents\\NetBeansProjects\\RMIServerSide\\bin");
            System.setProperty("java.rmi.server.codebase", "file:C:\\Users\\Slightom\\Documents\\NetBeansProjects\\KKServer\\build\\classes");
            //System.setProperty("java.rmi.server.codebase", "http://82.149.139.132:1099/ts/");

            System.out.println("Codebase: " + System.getProperty("java.rmi.server.codebase"));

            LocateRegistry.createRegistry(1099);
            KKServer obj1 = new KKServer();

            Naming.rebind("//localhost/ABC", obj1);

            System.out.println("Serwer oczekuje ...");

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
