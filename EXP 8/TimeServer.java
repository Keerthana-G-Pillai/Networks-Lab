import java.net.*;
import java.util.*;

class TimeServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket s;
        DatagramPacket rp;
        byte b[];

        s = new DatagramSocket(5000);

        while (true) {
            b = new byte[1024];
            rp = new DatagramPacket(b, b.length);
            s.receive(rp);

            final InetAddress ip = rp.getAddress();
            final int port = rp.getPort();

            Thread t = new Thread(() -> {
                try {
                    String result;
                    byte data[];
                    DatagramPacket dp;

                    result = new Date().toString();

                    data = result.getBytes();
                    dp = new DatagramPacket(data, data.length, ip, port);
                    s.send(dp);

                } catch (Exception e) {
                    System.out.println(e);
                }
            });

            t.start();
        }
    }
}
