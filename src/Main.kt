fun main() {
    Program().start()
}

class Program {

    private val threadsCount = 8

    fun start() {
        for (i in 1..threadsCount) {
            val id = i
            Thread { calculator(id) }.start()
        }
    }

    private fun calculator(threadId: Int) {
        var sum = 0L
        var count = 0L
        val step = 2L

        val endTime = System.currentTimeMillis() + 30000  // 30 секунд

        while (System.currentTimeMillis() < endTime) {
            sum += step
            count++
        }

        println("Thread $threadId -> $sum / $count")
    }
}