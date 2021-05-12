package im.gian.tr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import im.gian.tr.R
import im.gian.tr.databinding.FragmentLoginBinding
import im.gian.tr.databinding.FragmentSigninBinding

class SigninFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSigninBinding>(
            inflater, R.layout.fragment_signin, container, false)

        binding.signin = this

        return binding.root
    }
}