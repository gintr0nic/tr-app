package im.gian.tr.login.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.databinding.FragmentSignupBinding
import im.gian.tr.home.HomeActivity

class SignupFragment : Fragment() {
    val signupViewModel :SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater, R.layout.fragment_signup, container, false)

        binding.signup = this
        binding.signupViewModel = signupViewModel

        val onSignupCompleteListener = OnCompleteListener<AuthResult>() { task ->
            if(task.isSuccessful) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }

            binding.buttonSignup.revertAnimation()
        }

        binding.buttonSignup.setOnClickListener {
            binding.buttonSignup.startAnimation()
            signupViewModel.signupUser(email = binding.textInputEmail.text.toString(), password = binding.textInputPassword.text.toString(), onCompleteListener = onSignupCompleteListener)
        }

        binding.buttonSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        })

        return binding.root
    }
}