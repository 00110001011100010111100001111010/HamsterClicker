package me.oneqxz.hamsterclicker

object Utils {

    fun <T> getRandomElementOrNull(list: List<T>): T? {
        return if (list.isNotEmpty()) {
            list.random()
        } else {
            null
        }
    }

}