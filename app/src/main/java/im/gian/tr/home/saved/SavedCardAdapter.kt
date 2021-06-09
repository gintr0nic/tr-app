package im.gian.tr.home.saved

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant
import im.gian.tr.restaurant.RestaurantActivity

class SavedCardAdapter(private val context: Context?) : RecyclerView.Adapter<SavedCardAdapter.SavedCardViewHolder>() {
    var storage = Firebase.storage

    private val homeViewModel: HomeViewModel =
        ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

    private var savedList: List<Restaurant>? = homeViewModel.saved.value

    class SavedCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val cardSaved: MaterialCardView = row.findViewById(R.id.cardSaved)
        val textViewSavedName: TextView = row.findViewById(R.id.textViewSavedName)
        val textViewSavedCity: TextView = row.findViewById(R.id.textViewSavedCity)
        val imageViewSaved: ImageView = row.findViewById(R.id.imageViewSaved)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.saved_card_view,
            parent, false)
        return SavedCardViewHolder(layout)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        //Observe for changes in viewmodel
        homeViewModel.saved.observe(context as FragmentActivity, savedObserver)
    }

    //Update saved list when viewmodel data changes
    private val savedObserver = Observer<List<Restaurant>> {
        savedList = homeViewModel.saved.value
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SavedCardViewHolder, position: Int) {
        //Card
        holder.cardSaved.setOnClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurant",Gson().toJson(savedList!![position]))
            intent.putExtra("saved", true)

            context?.startActivity(intent)
        }

        //Name
        holder.textViewSavedName.text = savedList!![position].name

        //City
        holder.textViewSavedCity.text = savedList!![position].city

        //Image
        val imageReference = storage.reference.child("propics/${savedList!![position].id}.jpg")
        imageReference.downloadUrl.addOnSuccessListener {
            Glide.with(holder.imageViewSaved).load(it).into(holder.imageViewSaved)
        }

    }

    override fun getItemCount(): Int = savedList!!.size
}