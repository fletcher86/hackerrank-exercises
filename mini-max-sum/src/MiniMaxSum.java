import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class MiniMaxSum {

    // Complete the miniMaxSum function below.
    static void miniMaxSum(int[] arr) {

        int n = arr.length;
        List<Long> sums = new ArrayList<>();
        for(int i=0; i< n; i++) {
            long thisSum=0;
            for(int j=0; j<n; j++) {
                if(j!=i) {
                    thisSum = thisSum + arr[j];
                }
            }
            sums.add(thisSum);
        }

        System.out.printf("%d %d\n", Collections.min(sums), Collections.max(sums));

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int[] arr = new int[5];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < 5; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        miniMaxSum(arr);

        scanner.close();
    }
}
