import java.io.*;
import java.util.*;

public class QuickestWayUp {
    private static Integer MAX_ROLL = 6;
    private static Integer BOARD_LEN = 100;
    private static Map<Integer, List<Integer>> adjMap = new HashMap<>();
    private static Map<Integer, Integer> listSnakes = new HashMap<>();
    private static Map<Integer, Integer> listLadders = new HashMap<>();

    // Complete the quickestWayUp function below.
    static int computePath(int[][] ladders, int[][] snakes) {
        int N = 100;

        init();
        final Integer[] board = new Integer[BOARD_LEN];
        //Initialize board
        for (Integer i = 0; i < BOARD_LEN; ++i) {
            board[i] = i;
        }

        createAdjacencyList(board);

        for (int i = 0; i < ladders.length; i++) {
             listLadders.put(ladders[i][0] - 1, ladders[i][1] - 1);
        }

        for (int i = 0; i < snakes.length; i++) {
             listSnakes.put(snakes[i][0] - 1, snakes[i][1] - 1);
        }

        replace();

        Graph g = new Graph(BOARD_LEN);

        for (int currentNode = 0; currentNode < BOARD_LEN; currentNode++) {
            List<Integer> adjNodes = adjMap.get(currentNode);
            for (Integer adjNode : adjNodes) {
                g.addEdge(currentNode, adjNode);
            }
        }

        Integer count = g.bfs(0);

        return count;
    }

    public static void init() {
        for (Integer i = 0; i < BOARD_LEN; i++) {
            adjMap.put(i, new ArrayList<>());
        }
        listLadders.clear();
        listSnakes.clear();
    }

    private static void createAdjacencyList(Integer[] board) {

        for (int i = 0; i < BOARD_LEN; i++) {
            for (int j = 1; j <= 6; j++) {
                addDirectedEdge(i, i + j);
            }
        }
    }

    public static void addDirectedEdge(int node, int adjNode) {
        if (adjNode < BOARD_LEN) {
            adjMap.get(node).add(adjNode);
        }
    }

    private static void replace() {
        for (Integer node : adjMap.keySet()) {
            List<Integer> replaceAdjNodes = new ArrayList<Integer>();
            for (Integer adjNode : adjMap.get(node)) {
                if (listLadders.containsKey(adjNode)) {
                    replaceAdjNodes.add(listLadders.get(adjNode));
                } else if (listSnakes.containsKey(adjNode)) {
                    replaceAdjNodes.add(listSnakes.get(adjNode));
                } else {
                    replaceAdjNodes.add(adjNode);
                }
            }
            adjMap.put(node, replaceAdjNodes);
        }
    }

    public static class Graph {
        List<List<Integer>> adjLst;
        int size;

        public Graph(int size) {
            adjLst = new ArrayList<>();
            this.size = size;
            while (size-- > 0)//Initialize the adjancency list.
                adjLst.add(new ArrayList<Integer>());
        }

        public void addEdge(int first, int second) {
            adjLst.get(first).add(second);
         }

        Set<Integer> visited;

        public Integer bfs(Integer node) {

            if (node == null) {
                return -1;
            }

            Queue<List<Integer>> queue = new LinkedList<>(); //initialize queue
            visited = new HashSet<>();  //initialize visited log

            //a collection to hold the path through which a node has been reached
            //the node it self is the last element in that collection
            List<Integer> pathToNode = new ArrayList<>();
            pathToNode.add(node);

            queue.add(pathToNode);

            while (!queue.isEmpty()) {

                pathToNode = queue.poll();
                //get node (last element) from queue
                node = pathToNode.get(pathToNode.size() - 1);

                if (isSolved(node)) {
                    //print path
                    System.out.println(pathToNode);
                    continue;
                }

                //loop over neighbors
                for (Integer nextNode : getNeighbors(node)) {

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
            return adjLst.get(node);
        }

        private boolean isSolved(Integer node) {
            if (node == BOARD_LEN - 1) {
                return true;
            } else {
                return false;
            }
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
