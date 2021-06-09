package im.gian.tr.restaurant.details

import android.content.DialogInterface
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
import im.gian.tr.databinding.FragmentRestaurantDetailsBinding
import im.gian.tr.restaurant.RestaurantViewModel

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRestaurantDetailsBinding>(
            inflater, R.layout.fragment_restaurant_details, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(
            RestaurantViewModel::class.java)

        binding.details = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel

        //Images recyclerview
        binding.recyclerViewImages.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImages.adapter = ImageCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Certifications recyclerview
        binding.recyclerViewCertifications.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCertifications.adapter = CertificationCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Saved
        if(restaurantViewModel.saved.value == true)
            binding.checkBoxRestaurant.isChecked = true

        binding.checkBoxRestaurant.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) restaurantViewModel.addSaved()
            else restaurantViewModel.removeSaved()
        }

        //Edit buttons visibility
        if(activity?.intent?.getBooleanExtra("edit", false) == true){
            binding.buttonEditName.visibility = View.VISIBLE
            binding.buttonEditCity.visibility = View.VISIBLE
            binding.buttonEditDescription.visibility = View.VISIBLE
            binding.checkBoxRestaurant.visibility = View.GONE
        }

        //Edit button name
        binding.buttonEditName.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity)
            builder.setTitle("Modifica nome")

            val input = EditText(context as FragmentActivity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setText(restaurantViewModel.restaurant.value?.name)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                restaurantViewModel.editRestaurantName(input.text.toString())
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
            input.setText(restaurantViewModel.restaurant.value?.city)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                restaurantViewModel.editRestaurantCity(input.text.toString())
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
            input.setText(restaurantViewModel.restaurant.value?.description)

            builder.setView(input)
            builder.setPositiveButton("Modifica") { _, _ ->
                restaurantViewModel.editRestaurantDescription(input.text.toString())
            }
            builder.setNegativeButton("Annulla") { dialog, _ -> dialog.cancel() }
            builder.show()
        }

        return binding.root
    }
}