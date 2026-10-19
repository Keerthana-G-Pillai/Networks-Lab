import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    public static void main(String args[]) throws Exception {
        // Variable declarations
        Socket s;
        Scanner sc;
        Random r;
        DataOutputStream out;
        DataInputStream in;
        int n, matrix[][], type;
        String result;

        // Step 1: Connect to server
        s = new Socket("localhost", 9002);

        // Step 2: Create Scanner and Random
        sc = new Scanner(System.in);
        r = new Random();

        // Step 3: Read matrix order
        System.out.print("Enter order of matrix: ");
        n = sc.nextInt();

        // Step 4: Generate matrix
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = r.nextInt(50) + 1;
            }
        }

        // Step 5: Select matrix type
        type = r.nextInt(4);

        switch (type) {
            case 0: // Upper triangular
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        if (i > j)
                            matrix[i][j] = 0;
                break;

            case 1: // Lower triangular
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        if (i < j)
                            matrix[i][j] = 0;
                break;

            case 2: // Diagonal
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        if (i != j)
                            matrix[i][j] = 0;
                break;

            default: // Normal matrix
                break;
        }

        // Step 6: Display matrix
        System.out.println("\nGenerated Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }

        // Step 7: Send matrix
        // Step 7: Send matrix
        out = new DataOutputStream(s.getOutputStream());

        out.writeInt(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.writeInt(matrix[i][j]);
            }
        }

        out.flush();

        // Step 8: Receive result
        in = new DataInputStream(s.getInputStream());
        result = in.readUTF();

        // Step 9: Display result
        System.out.println("\nMatrix Type: " + result);

        // Step 10: Close connection
        s.close();
        sc.close();
    }
}
