import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class PlusMinus {

    // Complete the plusMinus function below.
    static void plusMinus(int[] arr) {

        int n = arr.length;
        int np=0;
        int nn=0;
        int nz=0;

        for(int num: arr) {
            if(num>0) {
                np++;
            }
            else if(num<0) {
                nn++;
            }
            else if(num==0) {
                nz++;
            }
        }
        float rp = (float) np/n;
        float rn = (float) nn/n;
        float rz = (float) nz/n;
        System.out.printf("%1.6f\n", rp);
        System.out.printf("%1.6f\n", rn);
        System.out.printf("%1.6f\n", rz);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        plusMinus(arr);

        scanner.close();
    }
}
