import kotlin.concurrent.thread

fun main() {
    Thread {
        println("running from lambda: ${Thread.currentThread()}")
    }.start();
    thread(start=true) {
        print("sff")
    }
}