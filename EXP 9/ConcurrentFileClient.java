
import java.net.*;
import java.util.*;

class FileClient {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        DatagramSocket s;
        DatagramPacket dp, rp;
        byte b[], rb[];
        InetAddress ip;
        String name, result;
        int port = 5000;
        Scanner sc;

        // Step 1: Create UDP socket
        s = new DatagramSocket();

        // Step 2: Read filename
        sc = new Scanner(System.in);

        System.out.println("Enter filename:");
        name = sc.nextLine();

        // Step 3: Convert filename into bytes
        b = name.getBytes();

        // Step 4: Get server IP
        ip = InetAddress.getByName("localhost");

        // Step 5: Create packet to send
        dp = new DatagramPacket(b, b.length, ip, port);

        // Step 6: Send request
        s.send(dp);

        // Step 7: Create receiving buffer
        rb = new byte[8192];

        // Step 8: Create receiving packet
        rp = new DatagramPacket(rb, rb.length);

        // Step 9: Receive response
        s.receive(rp);

        // Step 10: Convert received bytes into string
        result = new String(rp.getData(), 0, rp.getLength());

        // Step 11: Display response
        System.out.println(result);

        // Step 12: Close resources
        s.close();
        sc.close();
    }
}
