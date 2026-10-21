import java.io.*;
import java.net.*;
import java.util.*;

class ChatClient {
    public static void main(String args[]) throws Exception {
        Socket s;
        BufferedReader in;
        PrintWriter out;
        Scanner sc;
        String name, msg;
        Thread t;

        s = new Socket("localhost", 5000);

        in = new BufferedReader(
            new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(), true);
        sc = new Scanner(System.in);

        System.out.print("Enter name: ");
        name = sc.nextLine();
        out.println(name);

        t = new Thread(() -> {
            try {
                String result;

                while ((result = in.readLine()) != null)
                    System.out.println(result);

            } catch (Exception e) {
                System.out.println("Disconnected");
            }
        });

        t.start();

        while (true) {
            msg = sc.nextLine();
            out.println(msg);

            if (msg.equalsIgnoreCase("exit"))
                break;
        }

        s.close();
        sc.close();
    }
}