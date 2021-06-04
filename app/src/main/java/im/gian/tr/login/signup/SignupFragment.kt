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

        //Revert loading animation and go to Home
        val onSignupCompleteListener = OnCompleteListener<AuthResult>() { task ->
            if(task.isSuccessful) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(context, context?.getText(R.string.login_error), Toast.LENGTH_SHORT).show()
            }

            binding.buttonSignup.revertAnimation()
        }

        //Start loading animation and signup user
        binding.buttonSignup.setOnClickListener {
            binding.buttonSignup.startAnimation()

            if(binding.textInputPassword.text.toString() == binding.textInputRepeatPassword.text.toString())
                signupViewModel.signupUser(binding.textInputEmail.text.toString(), binding.textInputPassword.text.toString(), onSignupCompleteListener)
            else {
                Toast.makeText(
                    context,
                    context?.getText(R.string.password_not_match),
                    Toast.LENGTH_SHORT
                ).show()

                binding.buttonSignup.revertAnimation()
            }
        }

        //Go to signin fragment
        binding.buttonSignin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
        }

        //Go to login fragment when back button is pressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        })

        return binding.root
    }
}