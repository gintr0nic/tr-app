package im.gian.tr.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import im.gian.tr.R

class HomeViewModel : ViewModel() {
    private val _titleTextRes = MutableLiveData<Int>(R.string.home)
    val titleTextRes: LiveData<Int>
        get() = _titleTextRes

    fun setTitleTextRes(textRes: Int){
        _titleTextRes.value = textRes
    }

    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test

    fun setTest(value: String){
        _test.value = value
    }
}