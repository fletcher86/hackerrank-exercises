import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Encryption {

    // Complete the encryption function below.
    static String encryption(String s) {

        int n = s.length();
        Double sr = Math.sqrt(n);
        int nr = sr.intValue();
        int nc = 0;
        if(nr*nr != n) {
            nc = nr + 1;
        }
        else {
            nc = nr;
        }

        if (nr * nc < n) {
            nr++;
        }

        char[][] a = new char[nr][nc];

        int ctr = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                char aa = ' ';
                if (ctr < n) {
                    aa = s.charAt(ctr);
                }
                a[i][j] = aa;
                ctr++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < nc; j++) {
            for (int i = 0; i < nr; i++) {
                if (a[i][j] != ' ') {
                    sb.append(a[i][j]);
                }
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = scanner.nextLine();

        String result = encryption(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
