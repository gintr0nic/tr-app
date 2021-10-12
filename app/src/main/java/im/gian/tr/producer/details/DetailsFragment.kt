package im.gian.tr.producer.details

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentProducerDetailsBinding
import im.gian.tr.producer.ProducerViewModel

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

        //Certifications recyclerview
        binding.recyclerViewCertifications.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCertifications.adapter = CertificationCardAdapter(context)

        //Edit button name
        binding.buttonEditName.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity)
            builder.setTitle("Modifica nome")

            val input = EditText(context as FragmentActivity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setText(producerViewModel.producer.value?.name)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                producerViewModel.editProducerName(input.text.toString())
            }
            builder.setNegativeButton("Annulla") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        //Edit button city
        binding.buttonEditCity.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity)
            builder.setTitle("Modifica città")

            val input = EditText(context as FragmentActivity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setText(producerViewModel.producer.value?.city)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                producerViewModel.editProducerCity(input.text.toString())
            }
            builder.setNegativeButton("Annulla") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        //Edit button description
        binding.buttonEditDescription.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity)
            builder.setTitle("Modifica descrizione")

            val input = EditText(context as FragmentActivity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.isSingleLine = false
            input.minLines = 5
            input.setText(producerViewModel.producer.value?.description)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                producerViewModel.editProducerDescription(input.text.toString())
            }
            builder.setNegativeButton("Annulla") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

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