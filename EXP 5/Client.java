
import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        Socket s;
        DataInputStream in;
        DataOutputStream out;
        Scanner sc;
        Random r;
        int n, type, x;
        String result;

        // Step 1: Connect to server
        s = new Socket("localhost", 9002);

        // Step 2: Create input and output streams
        in = new DataInputStream(s.getInputStream());
        out = new DataOutputStream(s.getOutputStream());

        // Step 3: Create Scanner and Random
        sc = new Scanner(System.in);
        r = new Random();

        // Step 4: Read matrix order
        System.out.println("Enter N:");
        n = sc.nextInt();

        // Step 5: Send matrix order
        out.writeInt(n);

        // Step 6: Select matrix type randomly
        type = r.nextInt(4);

        System.out.println("Generated Matrix:");

        // Step 7: Generate and send matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                x = r.nextInt(50) + 1;

                if (type == 0 && i > j) {
                    x = 0;
                }

                if (type == 1 && i < j) {
                    x = 0;
                }

                if (type == 2 && i != j) {
                    x = 0;
                }

                System.out.print(x + " ");
                out.writeInt(x);
            }

            System.out.println();
        }

        // Step 8: Send data
        out.flush();

        // Step 9: Receive matrix type
        result = in.readUTF();

        // Step 10: Display result
        System.out.println("Matrix Type: " + result);

        // Step 11: Close socket
        s.close();
        sc.close();
    }
}
