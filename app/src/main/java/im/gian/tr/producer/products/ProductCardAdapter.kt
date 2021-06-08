package im.gian.tr.producer.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.model.Item
import im.gian.tr.producer.ProducerActivity
import im.gian.tr.producer.ProducerViewModel
import im.gian.tr.restaurant.RestaurantActivity
import im.gian.tr.restaurant.RestaurantViewModel

class ProductCardAdapter(private val context: Context?) : RecyclerView.Adapter<ProductCardAdapter.ProductCardViewHolder>() {
    private val producerViewModel: ProducerViewModel =
        ViewModelProvider(context as ProducerActivity).get(ProducerViewModel::class.java)

    private var products: List<String>? = producerViewModel.producer.value?.products

    var storage = Firebase.storage

    class ProductCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewProduct: TextView = row.findViewById(R.id.textViewProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.product_card_view,
            parent, false)

        return ProductCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        //Name
        holder.textViewProduct.text = products!![position]
    }

    override fun getItemCount(): Int = products!!.size
}