package aoc2018.day03

import aoc2018.Challenge
import aoc2018.day03.model.Claim
import aoc2018.util.readFileAsStream
import java.io.File
import kotlin.streams.toList

class Challenge01 : Challenge() {

    private val conflicts = HashSet<String>()

    override fun solve(): String {
        val claims = readFileAsStream(getInputFile())
            .map { parseClaim(it) }
            .toList()
        val fabric = buildFabric(claims)
        return claims.stream()
            .mapToInt { getTotalConflictingArea(it, fabric) }
            .sum().toString()
    }

    override fun getInputFile(): File {
        return File(javaClass
            .getResource("input.txt")
            .toURI())
    }

    private fun getTotalConflictingArea(claim: Claim, fabric: Array<Array<String>>): Int {
        var totalConflictingArea = 0
        for (col in 0 until claim.area.w) {
            for (row in 0 until claim.area.h) {
                val x = claim.area.x + col
                val y = claim.area.y + row
                val point = "$x,$y"
                if (isAlreadyClaimed(claim, fabric, x, y) && !conflicts.contains(point)) {
                    totalConflictingArea++
                    conflicts.add(point)
                }
            }
        }
        return totalConflictingArea
    }
}

fun main(args: Array<String>) {
    val challenge = Challenge01()
    println(challenge.solve())
}