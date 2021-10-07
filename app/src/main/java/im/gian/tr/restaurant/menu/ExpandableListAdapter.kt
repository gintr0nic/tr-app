package im.gian.tr.restaurant.menu

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import im.gian.tr.R
import im.gian.tr.model.Item
import im.gian.tr.producer.ProducerActivity

import java.util.HashMap

class ExpandableListAdapter internal constructor(private val context: Context, private val items: List<Item>) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Map<String, String> {
        return this.items[listPosition].ingredients[expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val ingredientName = getChild(listPosition, expandedListPosition)["name"]
        val producerName = getChild(listPosition, expandedListPosition)["producer_name"]
        val producer = getChild(listPosition, expandedListPosition)["producer"]

        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_ingredient_view, null)

            convertView.setOnClickListener {
                val intent = Intent(this.context, ProducerActivity::class.java)
                Log.d("producer: ", producer.toString())
                intent.putExtra("producer", producer)
                this.context.startActivity(intent)
            }
        }



        val ingredientNameView = convertView!!.findViewById<TextView>(R.id.ingredientName)
        ingredientNameView.text = ingredientName

        val ingredientProducerView = convertView!!.findViewById<TextView>(R.id.ingredientProducer)
        ingredientProducerView.text = producerName

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.items[listPosition].ingredients.size
    }

    override fun getGroup(listPosition: Int): Item {
        return this.items[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.items.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val itemName = getGroup(listPosition).name
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_title_view, null)
        }

        val itemNameView = convertView!!.findViewById<TextView>(R.id.itemName)
        itemNameView.setTypeface(null, Typeface.BOLD)
        itemNameView.text = itemName

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}