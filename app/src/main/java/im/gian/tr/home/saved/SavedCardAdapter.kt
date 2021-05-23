package im.gian.tr.home.saved

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.home.model.Restaurant
import java.math.RoundingMode

class SavedCardAdapter(private var savedList: List<Restaurant>?) : RecyclerView.Adapter<SavedCardAdapter.SavedCardViewHolder>() {
    var storage = Firebase.storage

    class SavedCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewSavedName: TextView = row.findViewById(R.id.textViewSavedName)
        val textViewSavedCity: TextView = row.findViewById(R.id.textViewSavedCity)
        val imageViewSaved: ImageView = row.findViewById(R.id.imageViewSaved)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.saved_card_view,
            parent, false)
        return SavedCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: SavedCardViewHolder, position: Int) {
        holder.textViewSavedName.text = savedList!![position].name
        holder.textViewSavedCity.text = savedList!![position].city

        val imageReference = storage.reference.child("propics/${savedList!![position].id}.jpg")
        imageReference.downloadUrl.addOnSuccessListener {
            Glide.with(holder.imageViewSaved).load(it).into(holder.imageViewSaved)
        }

    }

    override fun getItemCount(): Int = savedList!!.size

}