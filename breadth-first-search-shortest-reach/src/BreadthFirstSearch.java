import java.io.*;
import java.util.*;

public class BreadthFirstSearch {

    private static int N = 0;
    private static int M = 0;
    private static int[] nodes;

    // Complete the bfs function below.
    static int[] bfs(int n, int m, int[][] edges, int s) {
        N = n;
        M = m;

        init();
        Graph g = new Graph(N);

        assignDirectedEdges(g, edges);

        s--;
        int[] rtnList = new int[N - 1];
        int ctr = 0;
        for (int e = 0; e < N; e++) {
            if (s != e) {
                Integer distance = g.bfs(s, e);
                rtnList[ctr] = (distance);
                ctr++;
            }
        }
        return rtnList;
    }

    private static void init() {
        nodes = new int[N];

        for (int i = 0; i < N; i++) {
            nodes[i] = -1;
        }
    }

    private static void assignDirectedEdges(Graph g, int[][] edges) {
        for (int i = 0; i < M; i++) {
            int x = edges[i][0];
            int y = edges[i][1];
            x--;
            y--;
            g.addEdge(x, y);
            g.addEdge(y, x);
        }
    }

    public static class Graph {
        Map<Integer, List<Integer>> adjMap;
        int size;

        public Graph(int size) {
            adjMap = new HashMap<>();
            this.size = size;
            int ctr = 0;
            while (size-- > 0) {//Initialize the adjancency list.
                adjMap.put(ctr, new ArrayList<Integer>());
                ctr++;
            }
        }

        public void addEdge(int first, int second) {
            if (!adjMap.get(first).contains(second)) {
                adjMap.get(first).add(second);
            }
        }

        Set<Integer> visited;

        public Integer bfs(Integer n1, Integer n2) {

            if (n1 == null) {
                return -1;
            }

            Queue<List<Integer>> queue = new LinkedList<>(); //initialize queue
            visited = new HashSet<>();  //initialize visited log

            //a collection to hold the path through which a node has been reached
            //the node it self is the last element in that collection
            List<Integer> pathToNode = new ArrayList<>();
            pathToNode.add(n1);

            queue.add(pathToNode);

            while (!queue.isEmpty()) {

                pathToNode = queue.poll();

                //get node (last element) from queue
                n1 = pathToNode.get(pathToNode.size() - 1);

                if (n1.equals(n2)) {
                    //print path
                    //System.out.print(pathToNode + ":" + (pathToNode.size() - 1) * 6);
                    return (pathToNode.size() - 1) * 6;
                }

                //loop over neighbors
                for (Integer nextNode : getNeighbors(n1)) {

                    if (!isVisited(nextNode)) {
                        //create a new collection representing the path to nextNode
                        List<Integer> pathToNextNode = new ArrayList<>(pathToNode);
                        pathToNextNode.add(nextNode);
                        queue.add(pathToNextNode); //add collection to the queue
                    }
                }
            }


            return -1;
        }

        private List<Integer> getNeighbors(Integer node) {
            return adjMap.get(node);
        }

        private boolean isVisited(Integer node) {
            if (visited.contains(node)) {
                return true;
            }
            visited.add(node);
            return false;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nm = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nm[0]);

            int m = Integer.parseInt(nm[1]);

            int[][] edges = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] edgesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int edgesItem = Integer.parseInt(edgesRowItems[j]);
                    edges[i][j] = edgesItem;
                }
            }

            int s = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] result = bfs(n, m, edges, s);

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
