import java.io.*
import java.util.*

object OrgBalls {

    private val scanner = Scanner(System.`in`)

    // Complete the organizingContainers function below.
    internal fun organizingContainers(container: Array<IntArray>): String {

        val n = container.size
        val cont = ArrayList<Int>()
        val balls = ArrayList<Int>()

        var colsum: Int? = 0
        var rowsum: Int? = 0
        for (i in 0 until n) {
            for (j in 0 until n) {
                rowsum = rowsum!! + container[i][j]
                colsum = colsum!! + container[j][i]
            }
            balls.add(rowsum!!)
            cont.add(colsum!!)
            rowsum = 0
            colsum = 0
        }

        cont.removeAll(balls)
        return if (cont.isEmpty()) {
            "Possible"
        } else {
            "Impossible"
        }
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val bufferedWriter = BufferedWriter(FileWriter(System.getenv("OUTPUT_PATH")))

        val q = scanner.nextInt()
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

        for (qItr in 0 until q) {
            val n = scanner.nextInt()
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

            val container = Array(n) { IntArray(n) }

            for (i in 0 until n) {
                val containerRowItems =
                    scanner.nextLine().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?")

                for (j in 0 until n) {
                    val containerItem = Integer.parseInt(containerRowItems[j])
                    container[i][j] = containerItem
                }
            }

            val result = organizingContainers(container)

            bufferedWriter.write(result)
            bufferedWriter.newLine()
        }

        bufferedWriter.close()

        scanner.close()
    }
}
