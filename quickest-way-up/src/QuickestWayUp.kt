import java.io.*
import java.util.*

object QuickestWayUp {
    private val BOARD_LEN = 100
    private val adjMap : MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val listSnakes : MutableMap<Int, Int> = mutableMapOf()
    private val listLadders : MutableMap<Int, Int> = mutableMapOf()

    private val scanner = Scanner(System.`in`)

    // Complete the quickestWayUp function below.
    internal fun computePath(ladders: Array<IntArray>, snakes: Array<IntArray>): Int {
        val N = 100

        init()
        val board = arrayOfNulls<Int>(BOARD_LEN)
        //Initialize board
        for (i in 0 until BOARD_LEN) {
            board[i] = i
        }

        createAdjacencyList(board)

        for (i in ladders.indices) {
            listLadders[ladders[i][0] - 1] = ladders[i][1] - 1
        }

        for (i in snakes.indices) {
            listSnakes[snakes[i][0] - 1] = snakes[i][1] - 1
        }

        replace()

        val g = Graph(BOARD_LEN)

        for (currentNode in 0 until BOARD_LEN) {
            val adjNodes = adjMap[currentNode]
            for (adjNode in adjNodes!!) {
                g.addEdge(currentNode, adjNode!!)
            }
        }

        val count = g.bfs(0)

        return count!!
    }

    fun init() {
        for (i in 0 until BOARD_LEN) {
            adjMap[i] = mutableListOf()
        }
        listLadders.clear()
        listSnakes.clear()
    }

    private fun createAdjacencyList(board: Array<Int?>) {

        for (i in 0 until BOARD_LEN) {
            for (j in 1..6) {
                addDirectedEdge(i, i + j)
            }
        }
    }

    fun addDirectedEdge(node: Int, adjNode: Int) {
        if (adjNode < BOARD_LEN) {
            adjMap[node]?.add(adjNode)
        }
    }

    private fun replace() {
        for (node in adjMap.keys) {
            val replaceAdjNodes : MutableList<Int> = mutableListOf()
            for (adjNode in adjMap[node]!!) {
                if (listLadders.containsKey(adjNode)) {
                    listLadders!![adjNode]?.let { replaceAdjNodes.add(it) }
                } else if (listSnakes.containsKey(adjNode)) {
                    listSnakes!![adjNode]?.let { replaceAdjNodes.add(it) }
                } else {
                    replaceAdjNodes.add(adjNode)
                }
            }
            adjMap[node] = replaceAdjNodes
        }
    }

    class Graph(internal var size: Int) {
        internal var adjLst: MutableList<MutableList<Int>> = mutableListOf()

        internal var visited: MutableSet<Int> = mutableSetOf()

        init {
            //adjLst = ArrayList()
            for (i in 0 until size) {
                val l : MutableList<Int> = mutableListOf()
                adjLst.add(l)
            }
        }

        fun addEdge(first: Int, second: Int) {
            adjLst[first].add(second)
        }

        fun bfs(node: Int?): Int? {
            var node: Int? = node ?: return -1

            val queue = LinkedList<MutableList<Int>>()

            //val queue = LinkedList<MutableList<Int>>
            visited.clear()  //initialize visited log

            //a collection to hold the path through which a node has been reached
            //the node it self is the last element in that collection
            var pathToNode: MutableList<Int> = mutableListOf()
            pathToNode.add(node!!)

            queue.add(pathToNode)

            while (!queue.isEmpty()) {

                pathToNode = queue.poll()
                //get node (last element) from queue
                node = pathToNode[pathToNode.size - 1]

                if (isSolved(node)) {
                    //print path
                    println(pathToNode)
                    return pathToNode.size - 1
                }

                //loop over neighbors
                for (nextNode in getNeighbors(node)) {

                    if (!isVisited(nextNode)) {
                        //create a new collection representing the path to nextNode
                        val pathToNextNode = ArrayList(pathToNode)
                        pathToNextNode.add(nextNode)
                        queue.add(pathToNextNode) //add collection to the queue
                    }
                }
            }


            return -1
        }

        private fun getNeighbors(node: Int?): List<Int> {
            return adjLst[node!!]
        }

        private fun isSolved(node: Int?): Boolean {
            return if (node == BOARD_LEN - 1) {
                true
            } else {
                false
            }
        }

        private fun isVisited(node: Int?): Boolean {
            if (visited.contains(node)) {
                return true
            }
            visited.add(node!!)
            return false
        }
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val bufferedWriter = BufferedWriter(FileWriter(System.getenv("OUTPUT_PATH")))

        val t = scanner.nextInt()
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

        for (tItr in 0 until t) {
            val n = scanner.nextInt()
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

            val ladders = Array(n) { IntArray(2) }

            for (i in 0 until n) {
                val laddersRowItems =
                    scanner.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

                for (j in 0..1) {
                    val laddersItem = Integer.parseInt(laddersRowItems[j])
                    ladders[i][j] = laddersItem
                }
            }

            val m = scanner.nextInt()
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

            val snakes = Array(m) { IntArray(2) }

            for (i in 0 until m) {
                val snakesRowItems =
                    scanner.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

                for (j in 0..1) {
                    val snakesItem = Integer.parseInt(snakesRowItems[j])
                    snakes[i][j] = snakesItem
                }
            }

            val result = computePath(ladders, snakes)

            bufferedWriter.write(result.toString())
            bufferedWriter.newLine()
        }

        bufferedWriter.close()

        scanner.close()
    }
}
