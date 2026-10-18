
import java.net.*;

class UDPServer {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        DatagramSocket s;
        DatagramPacket p, rp;
        byte b[], rb[];
        String msg;

        // Step 1: Create UDP socket at port 9876
        s = new DatagramSocket(9876);

        // Step 2: Create buffer to receive data
        b = new byte[1024];

        // Step 3: Create packet to receive data
        p = new DatagramPacket(b, b.length);

        // Step 4: Display server status
        System.out.println("Server Started...");

        // Step 5: Receive packet from client
        s.receive(p);

        // Step 6: Convert received bytes into string
        msg = new String(p.getData(), 0, p.getLength());

        // Step 7: Display received sentence
        System.out.println("Received: " + msg);

        // Step 8: Expand abbreviations
        msg = msg.replace("tbh", "to be honest");
        msg = msg.replace("ig", "I guess");
        msg = msg.replace("tbf", "to be fair");
        msg = msg.replace("atm", "at the moment");
        msg = msg.replace("irl", "in real life");
        msg = msg.replace("lol", "laughing out loud");
        msg = msg.replace("asap", "as soon as possible");
        msg = msg.replace("omg", "oh my God");
        msg = msg.replace("ttyl", "talk to you later");
        msg = msg.replace("idk", "I don't know");
        msg = msg.replace("nvm", "never mind");
        msg = msg.replace("idc", "I don't care");

        // Step 9: Convert translated sentence into bytes
        rb = msg.getBytes();

        // Step 10: Create packet to send back to client
        rp = new DatagramPacket(
            rb, rb.length, p.getAddress(), p.getPort()
        );

        // Step 11: Send translated sentence
        s.send(rp);

        // Step 12: Display confirmation
        System.out.println("Translation Sent.");

        // Step 13: Close socket
        s.close();
    }
}
