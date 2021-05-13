package im.gian.tr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater, R.layout.fragment_signup, container, false)

        val auth = Firebase.auth

        binding.signup = this

        binding.buttonRegister.setOnClickListener {
            binding.buttonRegister.startAnimation()

            auth.createUserWithEmailAndPassword(binding.textInputEmail.text.toString(), binding.textInputPassword.toString())
                .addOnCompleteListener { task ->

                    if(task.isSuccessful)
                        Toast.makeText(context, "Success: ${auth.currentUser}", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(context, "Error: ${task.exception}", Toast.LENGTH_LONG).show()

                    binding.buttonRegister.revertAnimation()
            }
        }

        return binding.root
    }
}