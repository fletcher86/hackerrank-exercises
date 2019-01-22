import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class DijkstraShortestReach {

    public static ArrayList<HashMap<Integer, Integer>> adj = new ArrayList<HashMap<Integer, Integer>>();
    public static int[] shortestPaths;

    // Complete the shortestReach function below.
    static int[] shortestReach(int n, int[][] edges, int s) {
        adj.clear();
        for (int j = 0; j < n; j++) {
            adj.add(new HashMap<>());
        }

        for (int i = 0; i < edges.length; i++) {

            int x = edges[i][0];
            int y = edges[i][1];
            int r = edges[i][2];

            x--;
            y--;

            if (!adj.get(x).containsKey(y) || adj.get(x).get(y) > r) {
                adj.get(x).put(y, r);
            }
            if (!adj.get(y).containsKey(x) || adj.get(y).get(x) > r) {
                adj.get(y).put(x, r);
            }
        }

        DijkstraAlgorithm d = new DijkstraAlgorithm();
        s--;
        d.dijkstra(s);

        int [] newPaths = new int[n-1];
        int ctr = 0;
        for (int j = 0; j < n; j++) {
            if(j!=s) {
                if (shortestPaths[j] == Integer.MAX_VALUE) {
                    shortestPaths[j] = -1;
                }
                newPaths[ctr]= shortestPaths[j];
                ctr++;
            }
        }
        return newPaths;
    }

    public static class Pair implements Comparable<Pair> {

        public int x;
        public int c;

        Pair(int x, int c) {
            this.x = x;
            this.c = c;
        }

        public int compareTo(Pair b) {
            return (b.c < this.c) ? 1 : -1;
        }
    }

    public static class DijkstraAlgorithm {

        public static void dijkstra(int s) {
            shortestPaths = new int[adj.size()];
            for (int i = 0; i < adj.size(); i++) {
                shortestPaths[i] = Integer.MAX_VALUE;
            }
            shortestPaths[s] = 0;
            boolean[] visited = new boolean[adj.size()];
            PriorityQueue<Pair> queue = new PriorityQueue<>();
            queue.add(new Pair(s, 0));

            while (!queue.isEmpty()) {
                Pair current = queue.poll();
                if (!visited[current.x]) {
                    visited[current.x] = true;
                    Iterator<Map.Entry<Integer, Integer>> entries = adj.get(current.x).entrySet().iterator();

                    while (entries.hasNext()) {
                        Map.Entry<Integer, Integer> entry = entries.next();
                        if (current.c + entry.getValue() < shortestPaths[entry.getKey()]) {
                            shortestPaths[entry.getKey()] = current.c + entry.getValue();
                            if (!visited[entry.getKey()]) {
                                queue.add(new Pair(entry.getKey(), shortestPaths[entry.getKey()]));
                            }
                        }
                    }
                }
            }
        }
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] edges = new int[m][3];

            for (int i = 0; i < m; i++) {
                String[] edgesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 3; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            int s = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] result = shortestReach(n, edges, s);

            for (int i = 0; i < result.length; i++) {
                bufferedWriter.write(String.valueOf(result[i]));

                if (i != result.length - 1) {
                    bufferedWriter.write(" ");
                }
            }

            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
