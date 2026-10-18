
import java.net.*;
import java.io.*;

class ConcurrentFileServer {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        DatagramSocket s;
        DatagramPacket dp;
        byte b[], rb[];
        InetAddress ip;
        int port;
        long pid;
        String name;

        // Step 1: Create UDP server socket
        s = new DatagramSocket(5000);

        // Step 2: Get server PID
        pid = ProcessHandle.current().pid();

        System.out.println("Server Started...");

        // Step 3: Receive requests continuously
        while (true) {
            rb = new byte[1024];

            dp = new DatagramPacket(rb, rb.length);

            s.receive(dp);

            // Step 4: Get client IP and port
            ip = dp.getAddress();
            port = dp.getPort();

            // Step 5: Convert received bytes into string
            name = new String(dp.getData(), 0, dp.getLength());

            // Step 6: Create thread for client
            final InetAddress clientIP = ip;
            final int clientPort = port;
            final String fileName = name;

            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        String result;
                        File f;
                        FileReader fr;
                        BufferedReader fin;
                        String line;

                        result = "Server PID: " + pid + "\n";
                        f = new File(fileName);

                        if (f.exists() && f.isFile()) {
                            result += "File Found\n";

                            fr = new FileReader(f);
                            fin = new BufferedReader(fr);

                            while ((line = fin.readLine()) != null) {
                                result += line + "\n";
                            }

                            fin.close();
                        } else {
                            result += "File Not Found";
                        }

                        // Convert response into bytes
                        byte b[] = result.getBytes();

                        // Create reply packet
                        DatagramPacket rp = new DatagramPacket(
                            b, b.length, clientIP, clientPort);

                        // Send reply
                        s.send(rp);

                    } catch (Exception e) {
                        System.out.println("Error handling client");
                    }
                }
            });

            // Step 7: Start thread
            t.start();
        }
    }
}
