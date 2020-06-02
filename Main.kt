package processor

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val act = Action()

    var n = selectMenu()
    while (n != 0) {
        when (n) {
            1 -> act.add()
            2 -> act.scalar()
            3 -> act.mul()
            4 -> act.transpose()
            5 -> act.determinant()
            6 -> act.inverse()
        }
        println()
        n = selectMenu()
    }
}

fun selectMenu(): Int {
    val scanner = Scanner(System.`in`)
    println("1. Add matrices")
    println("2. Multiply matrix to a constant")
    println("3. Multiply matrices")
    println("4. Transpose matrices")
    println("5. Calculate a determinant")
    println("6. Inverse matrix")
    println("0. Exit")
    println("Your choice:")
    val n = scanner.nextInt()
    return n
}

class Action {
    val scanner = Scanner(System.`in`)

    fun add() {

        println("Enter size of first matrix:")
        val row1 = scanner.nextInt()
        val col1 = scanner.nextInt()
        println("Enter first matrix:")
        val a = Matrix(row1, col1)

        println("Enter size of second matrix:")
        val row2 = scanner.nextInt()
        val col2 = scanner.nextInt()
        val b = Matrix(row1, col1)

        if (row1 != row2 || col1 != col2) {
            println("ERROR")
            return
        }

        println("The addition result is:")
        for (i in 0..row1 - 1) {
            for (j in 0..col1 - 1) {
                if (j > 0) print(" ")
                print(a.get(i, j) + b.get(i, j))
            }
            println()
        }
    }

    fun scalar() {
        println("Enter size of first matrix:")
        val row1 = scanner.nextInt()
        val col1 = scanner.nextInt()
        println("Enter first matrix:")
        val a = Matrix(row1, col1)
        val c = scanner.nextInt()

        for (i in 0..row1 - 1) {
            for (j in 0..col1 - 1) {
                if (j > 0) print(" ")
                print(a.get(i, j) * c)
            }
            println()
        }
    }

    fun mul() {
        println("Enter size of first matrix:")
        val row1 = scanner.nextInt()
        val col1 = scanner.nextInt()
        println("Enter first matrix:")
        val a = Matrix(row1, col1)

        println("Enter size of second matrix:")
        val row2 = scanner.nextInt()
        val col2 = scanner.nextInt()
        val b = Matrix(row2, col2)

        if (col1 != row2) {
            println("ERROR")
            return
        }

        println("The multiplication result is:")
        for (i in 0..row1 - 1) {
            for (j in 0..col2 - 1) {
                var elm = 0.0
                var k = 0
                while (k < col1) {
                    elm += a.get(i, k) * b.get(k, j)
                    k++
                }
                if (j > 0) print(" ")
                print(elm)
            }
            println()
        }
    }

    fun transpose() {
        println()
        println("1. Main diagonal")
        println("2. Side diagonal")
        println("3. Vertical line")
        println("4. Horizontal line")
        println("Your choice:")
        val type = scanner.nextInt()
        println("Enter matrix size:")
        val row1 = scanner.nextInt()
        val col1 = scanner.nextInt()
        println("Enter matrix:")
        val a = Matrix(row1, col1)

        println("The result is:")
        when (type) {
            1 -> mainTp(a)
            2 -> sideTp(a)
            3 -> verticalTp(a)
            4 -> horizontalTp(a)
        }
    }

    fun mainTp(a: Matrix) {
        for (j in 0..a.col - 1) {
            for (i in 0..a.row - 1) {
                if (i > 0) print(" ")
                print(a.get(i, j))
            }
            println()
        }
    }

    fun sideTp(a: Matrix) {
        for (j in 0..a.col - 1) {
            for (i in 0..a.row - 1) {
                if (i > 0) print(" ")
                print(a.get(a.row - i - 1, a.col - j - 1))
            }
            println()
        }
    }

    fun verticalTp(a: Matrix) {
        for (i in 0..a.row - 1) {
            for (j in 0..a.col - 1) {
                if (j > 0) print(" ")
                print(a.get(i, a.col - j - 1))
            }
            println()
        }
    }

    fun horizontalTp(a: Matrix) {
        for (i in 0..a.row - 1) {
            for (j in 0..a.col - 1) {
                if (j > 0) print(" ")
                print(a.get(a.row - i - 1, j))
            }
            println()
        }
    }

    fun determinant() {
        println("Enter matrix size:")
        val row = scanner.nextInt()
        val col = scanner.nextInt()
        if (row != col) {
            println("ERROR")
            return
        }
        println("Enter matrix:")
        val a = Matrix(row, col)
        println("The result is:")
        println(a.calcdet())
    }

    fun inverse() {
        println("Enter matrix size:")
        val row = scanner.nextInt()
        val col = scanner.nextInt()
        if (row != col) {
            println("ERROR")
            return
        }
        println("Enter matrix:")
        val a = Matrix(row, col)

        println("The result is:")
        a.inverse()
        for (i in 0..row - 1) {
            for (j in 0..col - 1) {
                if (j > 0) print(" ")
                print(a.getInverse(i, j))
            }
            println()
        }
    }
}

class Matrix(val row: Int, val col: Int) {
    val a = Array(row, { arrayOfNulls<Double>(col) })
    var ainverse = Array(row, { arrayOfNulls<Double>(col) })

    init {
        val scanner = Scanner(System.`in`)
        for (i in 0..row - 1) {
            for (j in 0..col - 1) {
                a[i][j] = scanner.nextDouble()
            }
        }
    }

    fun get(rowIndex: Int, colIndex: Int): Double {
        return a[rowIndex][colIndex]!!
    }

    fun getInverse(rowIndex: Int, colIndex: Int): Double {
        return ainverse[rowIndex][colIndex]!!
    }

    fun calcdet(): Double {
        val m = cloneMatrix(this)
        return clonecalcdet(m)
    }

    fun inverse() {
        val m = cloneMatrix(this)
        ainverse = cloneInverse(m)
    }
}

fun clonecalcdet(m: Array<Array<Double?>>): Double {
    val dim = m.size
    if (dim == 2) {
        return m[0][0]!! * m[1][1]!! - m[0][1]!! * m[1][0]!!
    }
    if (dim == 1) {
        return m[0][0]!!
    }

    var det = 0.0
    for (j in 0..dim - 1) {
        val c = row1SubMatrix(j, m)
        val sign = if (j % 2 == 0) {
            1
        } else {
            -1
        }
        det += m[0][j]!! * clonecalcdet(c) * sign
    }
    return det
}

fun row1SubMatrix(colIndex: Int, matrix: Array<Array<Double?>>): Array<Array<Double?>> {
    val a = Array(matrix.size - 1, { arrayOfNulls<Double>(matrix.size - 1) })

    for (i in 0..matrix.size - 2) {
        for (j in 0..matrix.size - 2) {
            if (j < colIndex) {
                a[i][j] = matrix[i + 1][j]
            } else {
                a[i][j] = matrix[i + 1][j + 1]
            }
        }
    }

    return a
}

fun subMatrix(rowIndex: Int, colIndex: Int, matrix: Array<Array<Double?>>): Array<Array<Double?>> {
    val a = Array(matrix.size - 1, { arrayOfNulls<Double>(matrix.size - 1) })

    for (i in 0..matrix.size - 2) {
        for (j in 0..matrix.size - 2) {
            if (i < rowIndex) {
                if (j < colIndex) {
                    a[i][j] = matrix[i][j]
                } else {
                    a[i][j] = matrix[i][j + 1]
                }
            } else {
                if (j < colIndex) {
                    a[i][j] = matrix[i + 1][j]
                } else {
                    a[i][j] = matrix[i + 1][j + 1]
                }
            }
        }
    }

    return a
}

fun cloneMatrix(matrix: Matrix): Array<Array<Double?>> {
    val a = Array(matrix.row, { arrayOfNulls<Double>(matrix.col) })

    for (i in 0..matrix.row - 1) {
        for (j in 0..matrix.col - 1) {
            a[i][j] = matrix.a[i][j]
        }
    }
    return a
}

fun cloneInverse(m: Array<Array<Double?>>): Array<Array<Double?>> {
    val dim = m.size
    val a = Array(dim, { arrayOfNulls<Double>(dim) })

    val deta =clonecalcdet(m)
    if (deta == 0.0) return emptyArray()

    for (i in 0..dim - 1) {
        for (j in 0..dim - 1) {
            val c = subMatrix(i, j, m)
            val sign = if ((i + j) % 2 == 0) {
                1
            } else {
                -1
            }
            a[j][i] = clonecalcdet(c) * sign / deta
        }
    }

    return a
}