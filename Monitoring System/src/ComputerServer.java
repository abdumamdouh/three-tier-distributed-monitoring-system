import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ComputerServer {

    public static void main(String[] args) {
        try {

            //Port Number
            int ComputerServerPort = 8080;

            //1. open server socket
            ServerSocket ComputerServerSocket = new ServerSocket(ComputerServerPort);
            System.out.println("Computer Server Running...");

            while(true){
                //2.accept connection of the client
                Socket socketDriverComputer = ComputerServerSocket.accept();
                System.out.println("Client Accepted...");

                //3.create I/O data streams for communication with Driver Client
                DataInputStream dis1 = new DataInputStream(socketDriverComputer.getInputStream());
                DataOutputStream dos1 = new DataOutputStream(socketDriverComputer.getOutputStream());

                //Create Socket for communication with the Central Server
                //with IP:127.0.0.0 -localhost-
                String hostIP = "127.0.0.1";
                //and with port number #8081
                int CentralServerPort = 8081;

                Socket socketComputerCentral = new Socket(hostIP, CentralServerPort);

                // Create I/O streams for communicating between Computer Server and Central Server
                DataInputStream dis2 = new DataInputStream(socketComputerCentral.getInputStream());
                DataOutputStream dos2 = new DataOutputStream(socketComputerCentral.getOutputStream());


                //4. perform the I/O operations with the client
                while (true) {

                    //a. request current location of the driver
                    dos1.writeUTF("Please enter your location");
                    dos1.flush();
                    String location = dis1.readUTF();
                    //check data
                    //----------------------
                    //b. request the destination of the driver
                    dos1.writeUTF("Please enter your destination:");
                    dos1.flush();
                    String destination = dis1.readUTF();
                    //----------------------
                    //send the data to the central server to get the recommendation and send it back to the client
                    dos2.writeUTF("location " + location + " and the destination " + destination);
                    dos2.flush();
                    String recommendation = dis2.readUTF();
                    //----------------------
                    //send the recommendation to the driver client
                    dos1.writeUTF(recommendation + ", Do you want another recommendation [y/n]?");
                    dos1.flush();
                    System.out.println("recommendation received and sent to the client");
                    String usr_choice = dis1.readUTF();
                    //check data
                    if (usr_choice.equals("n"))
                    {
                        dos1.writeUTF("bye");
                        dos1.flush();
                        break;
                    }

                }

                //5.close connection
                dis1.close();
                dos1.close();
                socketDriverComputer.close();
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
