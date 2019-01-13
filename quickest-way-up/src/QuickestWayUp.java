import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class QuickestWayUp {
    private static Integer MAX_ROLL = 6;
    private static Integer BOARD_LEN = 100;
    private static Map<Integer, List<Integer>> adjencyMap = new HashMap<>();
    private static Map<Integer, Integer> listSnakes = new HashMap<>();
    private static Map<Integer, Integer> listLadders = new HashMap<>();

    // Complete the quickestWayUp function below.
    static int computePath(int[][] ladders, int[][] snakes) {
        int N = 100;

        for (int i = 0; i < ladders.length; i++) {
            //g.addRoute(ladders[i][0]-1, ladders[i][1]-1);
            listLadders.put(ladders[i][0] - 1, ladders[i][1] - 1);
        }

        for (int i = 0; i < snakes.length; i++) {
            //g.addRoute(snakes[i][0]-1, snakes[i][1]-1);
            listSnakes.put(snakes[i][0] - 1, snakes[i][1] - 1);
        }

        for (int step = 1; step <= 6; step++) {
            for (int i = 0; i < N - step; i += step) {
                //g.addRoute(i, i + step);
            }
        }
        final Integer[] board = new Integer[BOARD_LEN];
        //Initialize board
        for (Integer i = 0; i < BOARD_LEN; ++i) {
            board[i] = i;
        }


        createAdjacencyList(board);
        replace();
        printAdjencyList();

        Integer count = breathFirstSearch();

        return count;
    }

    private static void createAdjacencyList(Integer[] board) {

        for (Integer i = 0; i < board.length; i++) {
            for (Integer j = 0; j < 7; j++) {
                if (i + j > 99)
                    break;
                if (adjencyMap.containsKey(board[i]))
                    adjencyMap.get(board[i]).add(board[i + j]);
                else
                    adjencyMap.put(board[i], new ArrayList(board[i + j]));
            }
        }
    }

    private static void printAdjencyList() {
        for (Integer i : adjencyMap.keySet()) {
            System.out.println();
            System.out.print("key: " + i + " values: ");
            for (Integer temp : adjencyMap.get(i)) {
                System.out.print(temp + " ");
            }
        }
    }

    private static void replace() {
        for (Integer t : adjencyMap.keySet()) {
            List<Integer> temp = new ArrayList<Integer>();

            for (Integer t1 : adjencyMap.get(t)) {
                if (listLadders.containsKey(t1)) {
                    temp.add(listLadders.get(t1));
                } else if (listSnakes.containsKey(t1)) {
                    temp.add(listSnakes.get(t1));
                } else {
                    temp.add(t1);
                }
            }
            adjencyMap.put(t, temp);
        }
    }

    private static Integer breathFirstSearch() {
        int count = 0;
        Integer i = 0;
        while (i != 99) {
            List<Integer> temp = adjencyMap.get(i);
            Collections.sort(temp);
            i = temp.get(temp.size() - 1);
            count++;
            if (i > 99) break;
        }
        return count;
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] ladders = new int[n][2];

            for (int i = 0; i < n; i++) {
                String[] laddersRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int laddersItem = Integer.parseInt(laddersRowItems[j]);
                    ladders[i][j] = laddersItem;
                }
            }

            int m = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] snakes = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] snakesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int snakesItem = Integer.parseInt(snakesRowItems[j]);
                    snakes[i][j] = snakesItem;
                }
            }

            int result = computePath(ladders, snakes);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
