package im.gian.tr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import im.gian.tr.R
import im.gian.tr.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false)

        binding.login = this

        binding.buttonEmailSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.buttonFacebookLogin.setOnClickListener {
            Toast.makeText(context, context?.getText(R.string.not_available), Toast.LENGTH_SHORT).show()
        }

        binding.buttonGoogleLogin.setOnClickListener {
            Toast.makeText(context, context?.getText(R.string.not_available), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}