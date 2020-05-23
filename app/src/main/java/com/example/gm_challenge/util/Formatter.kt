package com.example.gm_challenge.util


private val c = charArrayOf('k', 'm', 'b', 't')

/**
 * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
 * @param n the number to format
 * @param iteration in fact this is the class from the array c
 * @return a String representing the number n formatted in a cool looking way.
 */
fun coolFormat(n: Double, iteration: Int): String? {
    val d = n.toLong() / 100 / 10.0
    val isRound =
        d * 10 % 10 == 0.0 //true if the decimal part is equal to 0 (then it's trimmed anyway)
    return if (d < 1000) //this determines the class, i.e. 'k', 'm' etc
        (if (d > 99.9 || isRound || !isRound && d > 9.99) //this decides whether to trim the decimals
            d.toInt() * 10 / 10 else d.toString() + "" // (int) d * 10 / 10 drops the decimal
                ).toString() + "" + c[iteration] else coolFormat(d, iteration + 1)
}