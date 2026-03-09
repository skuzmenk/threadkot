import java.util.Random

fun main() {
    Program().start()
}

class Program {

    private val threadsCount = 8
    private val random = Random()

    @Volatile
    private lateinit var canStop: BooleanArray
    private lateinit var startTimes: LongArray
    private lateinit var workTimes: IntArray

    fun start() {

        canStop = BooleanArray(threadsCount)
        startTimes = LongArray(threadsCount)
        workTimes = IntArray(threadsCount)

        for (i in 0 until threadsCount) {
            workTimes[i] = random.nextInt(10001) + 5000
        }

        for (i in 0 until threadsCount) {

            val id = i
            startTimes[i] = System.currentTimeMillis()

            val t = Thread { calculator(id) }
            t.start()
        }

        val controller = Thread { controller() }
        controller.start()
    }

    private fun controller() {

        var allStopped = false

        while (!allStopped) {

            allStopped = true

            for (i in 0 until threadsCount) {

                if (!canStop[i]) {

                    allStopped = false

                    val worked = System.currentTimeMillis() - startTimes[i]

                    if (worked >= workTimes[i]) {
                        canStop[i] = true
                    }
                }
            }

            Thread.sleep(1)
        }
    }

    private fun calculator(id: Int) {

        var sum = 0L
        var count = 0L
        val step = 2L

        while (!canStop[id]) {
            sum += step
            count++
        }

        println("Thread ${id + 1} sum=$sum count=$count")
    }
}