package im.gian.tr.login.signin

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninViewModel : ViewModel() {
    private val auth = Firebase.auth

    fun signinUser(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener)
    }
}