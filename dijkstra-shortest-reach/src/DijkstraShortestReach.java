import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class DijkstraShortestReach {

    static List<DirectedEdge> directedEdges = new ArrayList<>();
    // Complete the shortestReach function below.
    static int[] shortestReach(int n, int[][] edges, int s) {
        directedEdges.clear();
        for(int i=0; i< edges.length; i++) {

            Node n1 = Node.getNode(Integer.toString(edges[i][0]));
            Node n2 = Node.getNode(Integer.toString(edges[i][1]));
            Integer weight = edges[i][2];

            DirectedEdge de1 = new DirectedEdge();
            de1.setStart(n1);
            de1.setEnd(n2);
            de1.setWeight(weight);

            DirectedEdge de2 = new DirectedEdge();
            de2.setStart(n2);
            de2.setEnd(n1);
            de2.setWeight(weight);

            directedEdges.add(de1);
            directedEdges.add(de2);
        }

        DijkstraAlgorithm d = new DijkstraAlgorithm();

        int [] weights = new int[n-1];
        int ctr=0;
        for(int i=1; i<= n; i++) {
            String weight = null;
            if(i!=s) {
                try {
                    weight = d.findShortestPath(Integer.toString(s), Integer.toString(i), directedEdges);
                }
                catch(GraphMapException e) {
                    weight = "-1";
                }
                weights[ctr] = Integer.parseInt(weight);
                ctr++;
            }

        }

        return weights;
    }

    public static class DijkstraAlgorithm
    {
        private Map<String, GraphMap> visitedMap = new HashMap<>();

        public String findShortestPath(String start, String end, List<DirectedEdge> conveyorSystemRoutes)
        {
            GraphMap gm;
            if (visitedMap.containsKey(start))
            {
                gm = visitedMap.get(start);
            } else
            {
                gm = new GraphMap();
                gm.buildGraphMap(conveyorSystemRoutes);
                gm.computePaths(start);
                visitedMap.put(start, gm);
            }

            List<Node> shortestPath = gm.getShortestPath(end);
            return getPathString(shortestPath);
        }

        private String getPathString(List<Node> path)
        {
            StringBuffer line = new StringBuffer();

            line.append(path.get(path.size() - 1).getTime());
            return line.toString();
        }

    }
    public static class Node implements Comparable<Node>
    {

        private static Map<String, Node> nodes = new HashMap<String, Node>();
        private String name;
        private Node previousNode = null;
        private Integer nodeId;
        private final Map<Node, Integer> neighbors = new HashMap<>();
        private Integer time = Integer.MAX_VALUE;

        public static Node getNode(String name)
        {
            Node n = nodes.get(name);
            if (n == null)
            {
                n = new Node(name);
                n.setNodeId(Integer.parseInt(name)); // assign unique integer to node
                nodes.put(name, n);
            }
            return n;
        }


        public static Node getNodeById(Integer id)
        {
            Node rtnNode = null;
            for (Node n : nodes.values())
            {
                if (n.getNodeId().equals(id))
                {
                    rtnNode = n;
                    break;
                }
            }
            return rtnNode;
        }

        private Node()
        {

        }

        private Node(String name)
        {
            this.name = name;

        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return name;
        }

        public Integer getNodeId()
        {
            return nodeId;
        }

        public void setNodeId(Integer nodeId)
        {
            this.nodeId = nodeId;
        }

        public static Integer getNodeCount()
        {
            return nodes.size();
        }

        public Map<Node, Integer> getNeighbors()
        {
            return neighbors;
        }

        public int getTime()
        {
            return time;
        }

        public void setTime(int time)
        {
            this.time = time;
        }

        public Node getPreviousNode()
        {
            return previousNode;
        }

        public void setPreviousNode(Node previousNode)
        {
            this.previousNode = previousNode;
        }

        public List<Node> getShortestPathTo()
        {
            List<Node> path = new ArrayList<Node>();
            path.add(this);
            Node node = this.getPreviousNode();
            while (node != null && !path.contains(node))
            {
                path.add(node);
                node = node.getPreviousNode();
            }

            Collections.reverse(path);
            return path;
        }

        @Override
        public int compareTo(Node thatNode)
        {
            if (this.time == thatNode.time)
                return this.name.compareTo(thatNode.name);

            return Integer.compare(this.time, thatNode.time);
        }

    }

    public static class DirectedEdge
    {

        Node start;
        Node end;
        Integer weight;

        public Node getStart()
        {
            return start;
        }

        public void setStart(Node n)
        {
            this.start = n;
        }

        public Node getEnd()
        {
            return end;
        }

        public void setEnd(Node n)
        {
            this.end = n;
        }

        public Integer getWeight()
        {
            return weight;
        }

        public void setWeight(Integer weight)
        {
            this.weight = weight;
        }

        public String toString()
        {
            return "[" + start + "][" + end + "][" + weight + "]";
        }
    }

    public static class GraphMap
    {
        private Map<String, Node> graphMap;
        public GraphMap()
        {

        }

        public void buildGraphMap(List<DirectedEdge> conveyorSystemRoutes)
        {
            graphMap = new HashMap<>(conveyorSystemRoutes.size());

            for (DirectedEdge e : conveyorSystemRoutes)
            {
                String start = e.getStart().getName();
                String end = e.getEnd().getName();
                if (!graphMap.containsKey(start))
                    graphMap.put(start, Node.getNode(start));
                if (!graphMap.containsKey(end))
                    graphMap.put(end, Node.getNode(end));
            }

            for (DirectedEdge e : conveyorSystemRoutes)
            {
                String start = e.getStart().getName();
                String end = e.getEnd().getName();
                graphMap.get(start).getNeighbors().put(graphMap.get(end), e.getWeight());
            }

            for (DirectedEdge e : conveyorSystemRoutes)
            {
                String start = e.getStart().getName();
                String end = e.getEnd().getName();
                Node n = graphMap.get(end);
                Node s = graphMap.get(start);
                s.getNeighbors().put(n, e.getWeight());
            }
        }

        public void computePaths(String startName)
        {
            if (!graphMap.containsKey(startName))
            {
                throw new GraphMapException("This GraphMap does not contain the starting Node named:" + startName);
            }

            Node start = graphMap.get(startName);
            NavigableSet<Node> queue = new TreeSet<>();

            for (Node n : graphMap.values())
            {
                n.setPreviousNode(n == start ? start : null);
                n.setTime(n == start ? 0 : Integer.MAX_VALUE);
                queue.add(n);
            }

            determineQuickestPathToNeighbor(queue);
        }

        public List<Node> getShortestPath(String endName)
        {
            if (!graphMap.containsKey(endName))
            {
                throw new GraphMapException("Graph doesn't contain end node : " + endName);
            }

            return graphMap.get(endName).getShortestPathTo();
        }

        private void determineQuickestPathToNeighbor(final NavigableSet<Node> q)
        {
            while (!q.isEmpty())
            {
                Node n = q.pollFirst();
                if (n.getTime() == Integer.MAX_VALUE)
                    break;

                for (Map.Entry<Node, Integer> a : n.getNeighbors().entrySet())
                {
                    Node neighbor = a.getKey();

                    final int alternateTime = n.getTime() + a.getValue();
                    if (alternateTime < neighbor.getTime())
                    {
                        /*
                         * Then a shorter path to neighbor found
                         */
                        q.remove(neighbor);
                        neighbor.setTime(alternateTime);
                        neighbor.setPreviousNode(n);
                        q.add(neighbor);
                    }
                }
            }
        }
    }

    public static class GraphMapException extends RuntimeException
    {

        private static final long serialVersionUID = 8081940240189417771L;

        public GraphMapException(String message)
        {
            super(message);
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
