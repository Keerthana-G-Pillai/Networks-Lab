
import java.net.*;
import java.util.*;

class UDPClient {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        DatagramSocket s;
        Scanner sc;
        String msg, result;
        byte b[], rb[];
        InetAddress ip;
        DatagramPacket dp, rp;

        s = new DatagramSocket(); // Step 1: Create UDP socket
        msg="TIME";
        b = msg.getBytes();// Step 4: Convert sentence into bytes

        ip = InetAddress.getByName("localhost");  // Step 5: Get server IP address

        dp = new DatagramPacket(b, b.length, ip, 5000); // Step 6: Create packet to send

        s.send(dp);  // Step 7: Send packet

        rb = new byte[1024]; // Step 8: Create receiving buffer

        rp = new DatagramPacket(rb, rb.length); // Step 9: Create receiving packet
        s.receive(rp);  // Step 10: Receive packet

        // Step 11: Convert received bytes into string
        result = new String(rp.getData(), 0, rp.getLength());

        // Step 12: Display result
        System.out.println("Time " + result);

        // Step 13: Close resources
        s.close();
      
    }
}
