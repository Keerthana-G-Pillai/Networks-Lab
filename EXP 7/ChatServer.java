import java.io.*;
import java.net.*;
import java.util.*;

class ChatServer {
    static Vector<Socket> v = new Vector<>();

    public static void main(String args[]) throws Exception {
        ServerSocket ss;
        Socket s;

        ss = new ServerSocket(5000);
        System.out.println("Server started");

        while (true) {
            s = ss.accept();
            System.out.println("Client connected");
            v.add(s);

            final Socket cs = s;

            Thread t = new Thread(() -> {
                try {
                    BufferedReader in;
                    PrintWriter out;
                    String name, msg;

                    in = new BufferedReader(
                        new InputStreamReader(cs.getInputStream()));
                    out = new PrintWriter(cs.getOutputStream(), true);

                    name = in.readLine();
                    System.out.println(name + " joined");

                    while ((msg = in.readLine()) != null) {
                        if (msg.equalsIgnoreCase("exit"))
                            break;

                        System.out.println(name + ": " + msg);

                        for (Socket x : v) {
                            if (x != cs) {
                                PrintWriter o = new PrintWriter(
                                    x.getOutputStream(), true);
                                o.println(name + ": " + msg);
                            }
                        }
                    }

                    v.remove(cs);
                    System.out.println(name + " left");
                    cs.close();

                } catch (Exception e) {
                    v.remove(cs);
                }
            });

            t.start();
        }
    }
}