package im.gian.tr.producer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.databinding.ActivityProducerBinding
import im.gian.tr.databinding.ActivityRestaurantBinding
import im.gian.tr.model.Certification
import im.gian.tr.model.Producer
import im.gian.tr.model.Restaurant
import im.gian.tr.restaurant.RestaurantActivity

class ProducerActivity : AppCompatActivity() {
    val producerViewModel: ProducerViewModel by viewModels()

    private val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityProducerBinding>(this,R.layout.activity_producer)
        val navController = findNavController(R.id.navHostFragment)

        binding.producerViewModel = producerViewModel
        binding.lifecycleOwner = this

        //Load producer
        producerViewModel.fetchProducer(intent.getStringExtra("producer"))

        //Fetch data only when producer is loaded
        val producerObserver = Observer<Producer> { producer ->
            if(producer.id != ""){
                //Load image
                storage.reference.child("images/${producerViewModel.producer.value?.id}").list(1).addOnSuccessListener { list->
                    list.items.first().downloadUrl.addOnSuccessListener {
                        Glide.with(binding.producerImageView).load(it).placeholder(R.drawable.restaurant_placeholder).into(binding.producerImageView)
                    }
                }

                //Load data
                producerViewModel.fetchProducts()
                producerViewModel.fetchCertifications()
            }
        }
        producerViewModel.producer.observe(this, producerObserver)

        //Setup bottom bar
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu_producer)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

    }
}