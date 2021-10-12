package im.gian.tr.producer.details

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.model.Certification
import im.gian.tr.producer.ProducerActivity
import im.gian.tr.producer.ProducerViewModel

class ImageCardAdapter(private val context: Context?) : RecyclerView.Adapter<ImageCardAdapter.ImageCardViewHolder>() {
    private val producerViewModel: ProducerViewModel =
        ViewModelProvider(context as ProducerActivity).get(ProducerViewModel::class.java)

    private var images: List<Uri> = producerViewModel.images.value!!

    var storage = Firebase.storage

    class ImageCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val imageViewProducer: ImageView = row.findViewById(R.id.imageViewRestaurant)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        producerViewModel.images.observe(context as ProducerActivity, imagesObserver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.image_card_view,
            parent, false)

        return ImageCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        //Image
        if(images[position] != Uri.EMPTY)
            Glide.with(holder.imageViewProducer).load(images[position]).placeholder(R.drawable.restaurant_placeholder).into(holder.imageViewProducer)
    }

    override fun getItemCount(): Int = images.size

    //Update images list when viewmodel data changes
    private val imagesObserver = Observer<List<Uri>> {
        images = producerViewModel.images.value!!
        notifyDataSetChanged()
    }

}