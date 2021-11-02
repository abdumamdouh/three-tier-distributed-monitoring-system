

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class DriverClient {
    public static void main(String[] args) {

        //Scanner Object for getting input from user
        Scanner myScanner = new Scanner(System.in);

        try {

            //1.create a socket to connect to the server
            //with IP:127.0.0.0 -localhost-
            String hostIP = "127.0.0.1";
            //and with port number #8080
            int port = 8080;

            Socket S = new Socket(hostIP, port);

            //2. Create I/O streams for communicating
            DataInputStream dis = new DataInputStream(S.getInputStream());
            DataOutputStream dos = new DataOutputStream(S.getOutputStream());

            //3. Perform I/O operations with the server

            while(true) {

                //a. receive server massage & print it to user
                String ComputerServerMessage = dis.readUTF();

                if(ComputerServerMessage.equals("bye")){
                    System.out.println("Session Ended");
                    break;
                }

                System.out.println(ComputerServerMessage);
                //take input from the user using the scanner
                String userInput = myScanner.nextLine();
                dos.writeUTF(userInput);
                dos.flush();
            }


            //4. close connection of the socket and the I/O streams
            dis.close();
            dos.close();
            S.close();


        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

}
