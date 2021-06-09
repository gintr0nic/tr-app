package im.gian.tr.producer.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentProducerDetailsBinding
import im.gian.tr.producer.ProducerViewModel
import im.gian.tr.producer.details.CertificationCardAdapter

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProducerDetailsBinding>(
            inflater, R.layout.fragment_producer_details, container, false)

        val producerViewModel: ProducerViewModel = ViewModelProvider(context as FragmentActivity).get(
            ProducerViewModel::class.java)

        binding.details = this
        binding.lifecycleOwner = this
        binding.producerViewModel = producerViewModel

        //Edit button
        if(activity?.intent?.getBooleanExtra("edit", false) == true){
            binding.buttonEditName.visibility = View.VISIBLE
            binding.buttonEditCity.visibility = View.VISIBLE
            binding.buttonEditDescription.visibility = View.VISIBLE
        }

        //Images recyclerview
        binding.recyclerViewImages.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImages.adapter = ImageCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Certifications recyclerview
        binding.recyclerViewCertifications.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCertifications.adapter = CertificationCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Saved
        /*if(producerViewModel.saved.value == true)
            binding.checkBoxProducer.isChecked = true

        binding.checkBoxProducer.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) producerViewModel.addSaved()
            else producerViewModel.removeSaved()
        }*/

        return binding.root
    }
}