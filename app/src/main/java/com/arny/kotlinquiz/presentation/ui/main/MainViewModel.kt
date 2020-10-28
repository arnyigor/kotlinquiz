package com.arny.kotlinquiz.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arny.kotlinquiz.presentation.utils.mutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.SecureRandom

class MainViewModel : ViewModel() {
    private var sorting: Boolean = false
    private val random = SecureRandom()
    private var launchCount = 0
    val message = mutableLiveData("")
    fun launchDataLoad() {
        viewModelScope.launch {
            sortingWork()
        }
    }

    private suspend fun sortingWork() {
        if (sorting) return
        sorting = true
        launchCount++
        message.value = "Загрузка $launchCount"
        val sortList = sortList(generateList(20E3.toInt()))
        message.value = "Загрузка $launchCount окончена"
        delay(1000)
        message.value = "Список из ${sortList.size} элементов"
        sorting = false
    }

    private fun randomBytes(size: Int): ByteArray {
        val bytes = ByteArray(size)
        random.nextBytes(bytes)
        return bytes
    }

    private fun randomInt(min: Int = 3, max: Int = 10): Int {
        if (min >= max) {
            if (min == max) return max
            throw IllegalArgumentException("randomInt: $min >= $max")
        }
        val rand = random
        return synchronized(rand) { rand.nextInt((max - min) + 1) } + min
    }

    private fun randomHash(length: Int): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(randomBytes(length))
            .fold("", { str, it -> str + "%02x".format(it) })
    }

    private suspend fun generateList(size: Int) = withContext(Dispatchers.Default) {
        val arrayList = ArrayList<String>(size)
        for (i in 0 until size) {
            val perc = (i.toDouble() / size) * 100
            val toInt = perc.toInt()
            if (toInt % 10 == 0){
                withContext(Dispatchers.Main) {
                    message.value = "Загрузка $launchCount - $toInt %"
                }
            }
            arrayList.add(randomHash(randomInt()))
        }
        arrayList
    }

    private suspend fun sortList(generatedList: List<String>) = withContext(Dispatchers.Default) {
        generatedList.sorted()
    }
}