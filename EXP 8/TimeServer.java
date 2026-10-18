
import java.net.*;
import java.util.*;

class TimeServer {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        DatagramSocket s;
        DatagramPacket dp;
        byte rb[];
        InetAddress ip;
        int port;

        // Step 1: Create UDP server socket
        s = new DatagramSocket(5000);

        System.out.println("Time Server Started...");

        // Step 2: Receive client requests continuously
        while (true) {

            // Step 3: Create receiving buffer
            rb = new byte[1024];

            // Step 4: Create receiving packet
            dp = new DatagramPacket(rb, rb.length);

            // Step 5: Receive request
            s.receive(dp);

            // Step 6: Get client's IP and port
            ip = dp.getAddress();
            port = dp.getPort();

            // Step 7: Make copies for the thread
            final InetAddress clientIP = ip;
            final int clientPort = port;

            // Step 8: Create thread
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        // Step 9: Get current server time
                        String result = new Date().toString();

                        // Step 10: Convert time into bytes
                        byte b[] = result.getBytes();

                        // Step 11: Create reply packet
                        DatagramPacket rp = new DatagramPacket(
                            b, b.length, clientIP, clientPort);

                        // Step 12: Send reply
                        s.send(rp);

                        System.out.println("Time Sent.");

                    } catch (Exception e) {
                        System.out.println("Error sending time.");
                    }
                }
            });

            // Step 13: Start thread
            t.start();
        }
    }
}
