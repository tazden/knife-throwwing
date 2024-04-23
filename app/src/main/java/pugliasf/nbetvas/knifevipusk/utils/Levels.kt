package pugliasf.nbetvas.knifevipusk.utils

class Levels {
    companion object{
        const val LEVEL1 = 5
        const val LEVEL1_TIME = 15
        const val LEVEL2 = 5
        const val LEVEL2_TIME = 25
        const val LEVEL3 = 5
        const val LEVEL3_TIME = 35
        const val LEVEL4 = 5
        const val LEVEL4_TIME = 50
    }

    fun getHits(level : Int) : Int{
        return when(level){
            2 -> LEVEL2
            3 -> LEVEL3
            4 -> LEVEL4
            else -> LEVEL1
        }
    }

    fun getTime(level : Int) : Int{
        return when(level){
            2 -> LEVEL2_TIME
            3 -> LEVEL3_TIME
            4 -> LEVEL4_TIME
            else -> LEVEL1_TIME
        }
    }
}