import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


class CentralServerHandler implements Runnable {

    //Socket reference variable,  class field

    Socket socket;

    //overloaded constructor
    public  CentralServerHandler(Socket socket) {
        this.socket = socket;
    }

    //overriding the run method with the multithreaded code

    @Override
    public void run() {

        try {


            //3.create I/O data streams for communication
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //4. perform the I/O operations with the client
            while (true) {
                //a. receive massage from the computer server and print it
                String ComputerServerMessage = dis.readUTF();
                System.out.println(ComputerServerMessage);
                //----------------------
                //b. calculating the best route and send the recommendation to the computer server
                String route = "go straight then turn left then right";
                dos.writeUTF(route);
                dos.flush();

            }


        }

        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}


public class MultithreadedCentralServer {

    public static void main(String[] args) {
        try {

            //Port Number
            int port = 8081;

            //1. open server socket
            ServerSocket CentralServerSocket = new ServerSocket(port);
            System.out.println("Central Server Running...");

            while(true){
                //2.accept connection of the computer server
                Socket socket = CentralServerSocket.accept();
                System.out.println("Computer Server Accepted...");


                //3. open a thread to handle the client request and start it
                CentralServerHandler CSH = new CentralServerHandler(socket);
                Thread thread = new Thread(CSH);
                thread.start();


                //5.close connection
                //dis.close();
                //dos.close();
                //socket.close();
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
