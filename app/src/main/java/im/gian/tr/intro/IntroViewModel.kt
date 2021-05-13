package im.gian.tr.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import im.gian.tr.R

class IntroViewModel : ViewModel() {
    private val _buttonTextRes = MutableLiveData<Int>(R.string.intro_button_next)
    val buttonTextRes: LiveData<Int>
        get() = _buttonTextRes

    fun setButtonRes(step: Int){
        when(step){
            0 -> _buttonTextRes.value = R.string.intro_button_next
            1 -> _buttonTextRes.value = R.string.intro_button_start
            else -> _buttonTextRes.value = R.string.intro_button_next
        }
    }
}