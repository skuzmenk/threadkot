import java.util.concurrent.Semaphore

fun main() {
    Program().start()
}

class Program {

    private val threadsCount = 8

    private val semaphore = Semaphore(2)

    fun start() {

        for (i in 1..threadsCount) {
            val id = i
            Thread { calculator(id) }.start()
        }
    }

    private fun calculator(threadId: Int) {

        semaphore.acquire()

        try {
            var sum = 0L
            var count = 0L
            val step = 2L

            val endTime = System.currentTimeMillis() + 30000

            while (System.currentTimeMillis() < endTime) {
                sum += step
                count++
            }

            println("Thread $threadId -> $sum / $count")
        }
        finally {
            semaphore.release()
        }
    }
}