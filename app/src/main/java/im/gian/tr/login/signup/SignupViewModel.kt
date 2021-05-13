package im.gian.tr.login.signup

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupViewModel : ViewModel() {
    private val auth = Firebase.auth

    fun signupUser(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener)
    }
}