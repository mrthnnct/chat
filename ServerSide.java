import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
//sanirim calisiyo ama emin olamiyorum cunku client bozuk

public class ServerSide {

    static ArrayList<ClientRunner> clientes = new ArrayList<ClientRunner>();
    public static void main(String[] args) throws IOException {
        ServerSocket serversock = new ServerSocket(21375);
        System.out.println("waiting...");

        while (true) {
            Socket clientsock = serversock.accept();
            System.out.println("client in: " + clientsock.getInetAddress());

            ClientRunner clientman = new ClientRunner(clientsock);
            clientes.add(clientman);

            clientman.run();
        }
    }
}

class ClientRunner implements Runnable {
    private DataInputStream in;
    private DataOutputStream out;
    private int clients;
    String username;

    ClientRunner(Socket sock) throws IOException {

        in = new DataInputStream(sock.getInputStream());
        out = new DataOutputStream(sock.getOutputStream());
    }

    public void theSender(String m) throws IOException {
        out.writeUTF(m);
    }

    private void theAnnouncer(String m) throws IOException {
        for (int i = 0; i<clients; i++){
            out.writeUTF(m);

        }
    }

    @Override
    public void run() {
        try {
            String message = "";
            
            while (message != "close chat!") {
                message = in.readLine();
                System.out.println("[" + username + "]" + " says: " + message);
                theAnnouncer("[" + username + "]" + ": " + message);
            }

        } catch (IOException ioex) {
            System.out.println(username + " left chat :(");

        }
        

    }
}