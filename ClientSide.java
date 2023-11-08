import java.io.*;
import java.util.Scanner;
import java.net.*;
//calismiyo neden anlamadim ama input almiyo consoledan
//multithreadingden buyuk ihtimal threadler aciliyo ama Sender threadi calismiyo duzgun
//yarin multithreadsiz yazmayi denicem ama projede multithreading onemli diyodu.............

public class ClientSide {

    Socket sock;
    public static void main(String[] args) throws Exception{
        
        Socket sock = new Socket("localhost",21375);

        DataInputStream in = new DataInputStream(sock.getInputStream());
        DataOutputStream out = new DataOutputStream(sock.getOutputStream());

        Thread senderthread = new Thread(new Sender(out));
    
        //kontrol icin ama calismadi hepsini yaziyı thread bozuk sanirim
        senderthread.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

class Sender implements Runnable {

    private DataOutputStream output;

    Sender(DataOutputStream output){
        this.output = output;
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                String message = scanner.nextLine();
                output.writeUTF(message + "\n");
                output.flush();
                System.out.println("sent :" + message);
        }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


