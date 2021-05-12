package im.gian.tr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import im.gian.tr.R
import im.gian.tr.databinding.FragmentLoginBinding
import im.gian.tr.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater, R.layout.fragment_signup, container, false)

        binding.signup = this

        return binding.root
    }
}