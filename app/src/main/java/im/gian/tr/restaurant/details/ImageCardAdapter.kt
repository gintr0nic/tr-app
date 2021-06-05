package im.gian.tr.restaurant.details

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.model.Certification
import im.gian.tr.restaurant.RestaurantActivity
import im.gian.tr.restaurant.RestaurantViewModel

class ImageCardAdapter(private val context: Context?) : RecyclerView.Adapter<ImageCardAdapter.ImageCardViewHolder>() {
    private val restaurantViewModel: RestaurantViewModel =
        ViewModelProvider(context as RestaurantActivity).get(RestaurantViewModel::class.java)

    private var imageUriList: MutableList<Uri> = mutableListOf(Uri.parse("https://cwdaust.com.au/wpress/wp-content/uploads/2015/04/placeholder-restaurant.png"))

    var storage = Firebase.storage

    class ImageCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val imageViewRestaurant: ImageView = row.findViewById(R.id.imageViewRestaurant)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        storage.reference.child("images/${restaurantViewModel.restaurant.value?.id}").list(10).addOnSuccessListener { list->
            list.items.forEachIndexed { index, ref ->
                ref.downloadUrl.addOnSuccessListener {
                    imageUriList.add(it)
                    if(index == list.items.size - 1) notifyDataSetChanged() //If last update data
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.image_card_view,
            parent, false)

        return ImageCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        //Image
        Glide.with(holder.imageViewRestaurant).load(imageUriList[position]).placeholder(R.drawable.restaurant_placeholder).into(holder.imageViewRestaurant)
    }

    override fun getItemCount(): Int = imageUriList.size
}