import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class JumpingOnClouds {

    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {

        Graph g = new Graph(c.length);
        int c1, c2, c3, nj=0;

        if (c.length > 3) {
            for (int i = 0; i < c.length - 2; i++) {
                c1 = c[i];
                c2 = c[i + 1];
                c3 = c[i + 2];
                if (c1 == 0 && c2 == 0 && c3 == 0) {
                    g.addRoute(i, i + 1);
                    g.addRoute(i + 1, i + 2);
                    g.addRoute(i, i + 2);
                }
                if (c1 == 0 && c2 == 0 && c3 == 1) {
                    g.addRoute(i, i + 1);
                }
                if (c1 == 0 && c2 == 1 && c3 == 0) {
                    g.addRoute(i, i + 2);
                }
                if (c1 == 1 && c2 == 0 && c3 == 0) {
                    g.addRoute(i + 1, i + 2);
                }
            }
            nj = g.getShortestPath(0, c.length - 1);
        }
        else {
            nj=1;
        }

        return nj;
    }

    public static class Graph {
        private int numNodes;
        private List<List<Integer>> possibleRoutes;

        public List<Integer>[] getAdjList() {
            return adjList;
        }

        private List<Integer>[] adjList;

        public Graph(int numNodes) {
            this.numNodes = numNodes;
            init();
        }

        private void init() {
            adjList = new ArrayList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                adjList[i] = new ArrayList<>();
            }
            possibleRoutes = new ArrayList<List<Integer>>();
        }

        public void addRoute(int node, int adjNode) {
            if (!adjList[node].contains(adjNode)) {
                adjList[node].add(adjNode);
            }
        }

        public Integer getShortestPath(int start, int end) {
            boolean[] isVisited = new boolean[numNodes];
            List<Integer> pathList = new ArrayList<>();
            computePath(start, end, isVisited, pathList);

            return getOptimal();
        }

        public void computePath(Integer start, Integer end, boolean[] isVisited, List<Integer> path) {
            isVisited[start] = true;
            if (start.equals(end)) {
                addPossibleRoute(path);
            }
            for (Integer i : adjList[start]) {
                if (!isVisited[i]) {
                    path.add(i);
                    computePath(i, end, isVisited, path);
                    path.remove(i);
                }
            }
            isVisited[start] = false;
        }

        public void addPossibleRoute(List<Integer> r) {
            List<Integer> route = new ArrayList<Integer>();
            route.addAll(r);
            this.possibleRoutes.add(route);
        }

        public Integer getOptimal() {
            int min = 999;
            for (List<Integer> list : this.possibleRoutes) {
                if (list.size() < min) {
                    min = list.size();
                }
            }
            return min;
        }
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] c = new int[n];

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int cItem = Integer.parseInt(cItems[i]);
            c[i] = cItem;
        }

        int result = jumpingOnClouds(c);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
