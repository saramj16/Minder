package User.Model;

import User.Controller.ControllerClient;

import java.io.*;
import java.net.Socket;

public class Connectivity extends Thread {

    private DataInputStream diStream2;
    private ObjectInputStream oiStream2;
    private ControllerClient controllerClient;

    public Connectivity(ObjectInputStream oiStream, DataInputStream diStream) throws IOException {
        //Socket sClient2 = new Socket(ip, puerto+10);
        this.oiStream2 = oiStream;
        this.diStream2 = diStream;

        start();
    }

    public void go() {
        Object object;
        int id = 0;

        while (true){
            try {
                id = diStream2.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (id){
                case 1:
                    String mensaje = null;
                    String username = null;
                    try {
                        mensaje = diStream2.readUTF();
                        System.out.println("mensaje que recibimos: " + mensaje);
                        username = diStream2.readUTF();
                        System.out.println("al usuario: " + username);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    controllerClient.functionalities(1, mensaje, username);
                    System.out.println("lo mostramos por pantalla");
                    controllerClient.getAutenticationView().registerController(controllerClient);
                    break;

                default:
                    System.out.println("entramos default COnnectivity");
                    String mensaje2 = null;
                    String username2 = null;
                    try {
                        mensaje2 = diStream2.readUTF();
                        System.out.println("mensaje que recibimos: " + mensaje2);
                        username2 = diStream2.readUTF();
                        System.out.println("al usuario: " + username2);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    controllerClient.functionalities(1, mensaje2, username2);
                    System.out.println("lo mostramos por pantalla");
                    controllerClient.getAutenticationView().registerController(controllerClient);
                    break;
            }
        }
    }

    public void setController(ControllerClient controller) {
        this.controllerClient = controller;
    }
}
