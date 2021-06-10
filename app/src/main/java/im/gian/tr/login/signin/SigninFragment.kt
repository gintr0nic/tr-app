package im.gian.tr.login.signin

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
import im.gian.tr.R
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeActivity

class SigninFragment : Fragment() {
    val signinViewModel : SigninViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSigninBinding>(
            inflater, R.layout.fragment_signin, container, false)

        binding.signin = this
        binding.signinViewModel = signinViewModel

        //Revert loading animation and go to Home
        val onSigninCompleteListener = OnCompleteListener<AuthResult>() { task ->
            if(task.isSuccessful) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(context, context?.getText(R.string.login_error), Toast.LENGTH_SHORT).show()
            }

            binding.buttonSignin.revertAnimation()
        }

        //Start loading animation and signin user
        binding.buttonSignin.setOnClickListener {
            binding.buttonSignin.startAnimation()
            signinViewModel.signinUser(binding.textInputEmail.text.toString(), binding.textInputPassword.text.toString(), onSigninCompleteListener)
        }

        //Go to signup fragment
        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
        }

        //Button forgot
        binding.buttonForgot.setOnClickListener {
            Toast.makeText(context, context?.getText(R.string.not_available), Toast.LENGTH_SHORT).show()
        }

        //Go to login fragment when back button is pressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_signinFragment_to_loginFragment)
            }
        })

        return binding.root
    }
}