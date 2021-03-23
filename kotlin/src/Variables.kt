fun main() {
    var a: String = "initial"  // 1
    println(a)
    a = "fff"
    println(a)
    val b: Int = 1             // 2
    println(b)
//    b = 123 // 不可变对象
    println(b)
    val c = 3                  // 3


    /**
     * var 可变对象
     * val 不可变对象
     */
    var s = 123
    s = 2344


    var e: Int = 1  // 1
    e = 12
    e = 123
    println(e)  // Variable 'e' must be initialized
}