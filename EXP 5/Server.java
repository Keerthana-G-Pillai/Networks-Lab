
import java.io.*;
import java.net.*;

class Server {
    public static void main(String args[]) throws Exception {

        // Variable declarations
        ServerSocket ss;
        Socket s;
        DataInputStream in;
        DataOutputStream out;
        int n, matrix[][], x;
        boolean upper, lower, diagonal;
        String result;

        // Step 1: Create server socket
        ss = new ServerSocket(9002);

        System.out.println("Server waiting for connection...");

        // Step 2: Accept client connection
        s = ss.accept();

        System.out.println("Client Connected.");

        // Step 3: Create input stream
        in = new DataInputStream(s.getInputStream());

        // Step 4: Receive matrix order
        n = in.readInt();

        // Step 5: Create matrix
        matrix = new int[n][n];

        // Step 6: Receive matrix elements
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x = in.readInt();
                matrix[i][j] = x;
            }
        }

        // Step 7: Display received matrix
        System.out.println("Received Matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }

        // Step 8: Initialize variables
        upper = true;
        lower = true;
        diagonal = true;

        // Step 9: Check matrix type
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i > j && matrix[i][j] != 0) {
                    upper = false;
                }

                if (i < j && matrix[i][j] != 0) {
                    lower = false;
                }

                if (i != j && matrix[i][j] != 0) {
                    diagonal = false;
                }
            }
        }

        // Step 10: Find matrix type
        if (diagonal) {
            result = "Diagonal Matrix";
        } else if (upper) {
            result = "Upper Triangular Matrix";
        } else if (lower) {
            result = "Lower Triangular Matrix";
        } else {
            result = "Normal Matrix";
        }

        System.out.println("Matrix Type: " + result);

        // Step 11: Create output stream
        out = new DataOutputStream(s.getOutputStream());

        // Step 12: Send result to client
        out.writeUTF(result);
        out.flush();

        // Step 13: Close connections
        s.close();
        ss.close();
    }
}
