package im.gian.tr.producer.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import im.gian.tr.R
import im.gian.tr.databinding.FragmentProducerContactsBinding
import im.gian.tr.databinding.FragmentRestaurantContactsBinding
import im.gian.tr.producer.ProducerViewModel
import im.gian.tr.restaurant.RestaurantViewModel

class ContactsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProducerContactsBinding>(
            inflater, R.layout.fragment_producer_contacts, container, false)

        val producerViewModel: ProducerViewModel = ViewModelProvider(context as FragmentActivity).get(
            ProducerViewModel::class.java)

        binding.contacts = this
        binding.lifecycleOwner = this
        binding.producerViewModel = producerViewModel

        binding.cellphoneContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + producerViewModel.producer.value!!.cellphone)
            startActivity(intent)
        }

        binding.facebookContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www." + producerViewModel.producer.value!!.facebook))
            startActivity(intent)
        }

        binding.instagramContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www." + producerViewModel.producer.value!!.instagram))
            startActivity(intent)
        }

        binding.websiteContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www." + producerViewModel.producer.value!!.website))
            startActivity(intent)
        }

        return binding.root
    }
}