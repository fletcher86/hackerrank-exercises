import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Staircase {

    // Complete the staircase function below.
    static void staircase(int n) {

        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = n-1; j >= 0; j--) {
                if (i >= j) {
                    sb.append("#");
                } else {
                    sb.append(" ");
                }
            }
            System.out.println(sb.toString());
        }

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        staircase(n);

        scanner.close();
    }
}
