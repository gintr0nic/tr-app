package im.gian.tr.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import im.gian.tr.R
import im.gian.tr.databinding.FragmentLoginBinding
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeActivity
import im.gian.tr.login.signup.SignupViewModel

class SigninFragment : Fragment() {
    val viewModel : SigninViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSigninBinding>(
            inflater, R.layout.fragment_signin, container, false)

        binding.signin = this

        val onSigninCompleteListener = OnCompleteListener<AuthResult>() { task ->
            if(task.isSuccessful) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }

            binding.buttonSignin.revertAnimation()
        }

        binding.signin = this

        binding.buttonSignin.setOnClickListener {
            binding.buttonSignin.startAnimation()
            viewModel.signinUser(email = binding.textInputEmail.text.toString(), password = binding.textInputPassword.toString(), onCompleteListener = onSigninCompleteListener)
        }

        binding.viewModel = viewModel

        return binding.root
    }
}