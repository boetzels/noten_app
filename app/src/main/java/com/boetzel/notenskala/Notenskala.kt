package com.boetzel.notenskala

import android.util.Log

private const val LOGTAG = "NotenSkala"

class NotenSkala(var maxPoints: Double = 20.0, var suffPoints: Double = 12.0, var pointPath: Double = 0.5, var gradeRound: Double = 0.5, var myPoints: Double = 16.0) {
    private val _MIN_GRADE : Byte = 1
    private val _SUFF_GRADE : Byte = 4
    private val _MAX_GRADE : Byte = 6
    private val _STANDARD_COEFF : Double = 0.6;

    operator fun set(toString: String, value: Double) {
        when (toString) {
            "maxPoints" -> maxPoints = value
            "suffPoints" -> suffPoints = value
            "myPoints" -> myPoints = value
        }
    }

    // main function
    fun calcGrade(): Double {
        Log.d(LOGTAG, "entering private fun calcGrade($maxPoints, $suffPoints, $myPoints, $pointPath, $gradeRound)")
        //val pointDecs : Byte =
        val pointDecimals = this.getNbrOfDecimals(pointPath)
        val gradeDecimals = this.getNbrOfDecimals(gradeRound)

        val suffPointsFunc = this.calcLinearFunction(0.0, this._MIN_GRADE.toDouble(), suffPoints, this._SUFF_GRADE.toDouble());
        val maxPointsFunc = this.calcLinearFunction(suffPoints, this._SUFF_GRADE.toDouble(), maxPoints, this._MAX_GRADE.toDouble());

        var linFunc = suffPointsFunc;

        if (myPoints > suffPoints)  {
            linFunc = maxPointsFunc
        }

        val grade = Math.round(Math.round((myPoints*linFunc["m"]!!.toDouble()+linFunc["b"]!!.toDouble())/gradeRound)*gradeRound*Math.pow(10.0, gradeDecimals.toDouble()))/Math.pow(10.0, gradeDecimals.toDouble()).toDouble()

        Log.d(LOGTAG, "leaving private fun calcGrade($grade)")
        return grade
    }

    // get number of decimals of given number
    fun getNbrOfDecimals(number : Double) : Byte {
        Log.d(LOGTAG, "entering private fun getNbrOfDecimals($number)")
        if (Math.floor(number) == number) {
            return 0
        }
        val stringNumberSep = number.toString().split(".")
        var decimals : Byte
        decimals = if (stringNumberSep.size > 1) {
            stringNumberSep[1].toString().length.toByte()
        }
        else {
            0
        }
        Log.d(LOGTAG, "leaving private fun getNbrOfDecimals() -> $decimals")
        return decimals
    }

    // calcs linear function of 2 points
    fun calcLinearFunction(p1x : Double, p1y : Double, p2x : Double, p2y : Double) : Map<String,Double>  {
        Log.d(LOGTAG, "entering private fun calcLinearFunction($p1x, $p1y, $p2x, $p2y)")
        val m: Double = (p2y - p1y) / (p2x - p1x)
        val b: Double = p1y - m * p1x
        Log.d(LOGTAG, "leaving private fun calcLinearFunction() -> $m, $b")
        return mapOf<String,Double>("m" to m, "b" to b)
    }
}