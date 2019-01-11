
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.Channels;

/**
 * This class implements java socket client
 *
 * @author pankaj
 */
public class EchoClient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;

        for (int i = 0; i < 10; i++) {
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 2424);


            System.out.println("Sending request to Socket Server");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("HELLO");


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Message: " + response);

            Thread.sleep(1000);
        }
    }
}