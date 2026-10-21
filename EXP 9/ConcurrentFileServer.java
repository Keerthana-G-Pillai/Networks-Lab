
import java.net.*;
import java.io.*;
import java.nio.file.*;

class Server {
    public static void main(String args[]) throws Exception {
        DatagramSocket s;
        DatagramPacket rp;
        byte b[];
        long pid;

        s = new DatagramSocket(5000);
        pid = ProcessHandle.current().pid();

        while (true) {
            b = new byte[1024];
            rp = new DatagramPacket(b, b.length);
            s.receive(rp);

            final InetAddress ip = rp.getAddress();
            final int port = rp.getPort();
            final String name = new String(rp.getData(), 0, rp.getLength());

            Thread t = new Thread(() -> {
                try {
                    String result;
                    File f;
                    long tid;
                    byte data[];
                    DatagramPacket dp;

                    tid = Thread.currentThread().threadId();
                    result = "Server PID: " + pid + "\n";
                    result += "Servicing Thread ID: " + tid + "\n";

                    f = new File(name);

                    if (f.exists() && f.isFile())
                        result += "File Found\n" + Files.readString(f.toPath());
                    else
                        result += "File Not Found";

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
