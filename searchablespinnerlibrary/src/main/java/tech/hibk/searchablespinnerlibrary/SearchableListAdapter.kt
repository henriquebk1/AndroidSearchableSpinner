package tech.hibk.searchablespinnerlibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class SearchableListAdapter(context: Context, objects: List<SearchableItem>) : ArrayAdapter<SearchableItem>(context, R.layout.item_layout) {
    var searchListItems: MutableList<SearchableItem> = objects as MutableList<SearchableItem>
    var suggestions: MutableList<SearchableItem> = ArrayList()
    var filter = CustomFilter()

    override fun getCount(): Int {
        return searchListItems.size
    }

    override fun getItem(i: Int): SearchableItem {
        return searchListItems[i]
    }

    override fun getItemId(i: Int): Long {
        return searchListItems[i].id
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        var inflateview = view
        if (inflateview == null) {
            inflateview = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        }
        val tv = inflateview!!.findViewById<View>(R.id.t1) as TextView
        tv.text = searchListItems[i].title
        return inflateview
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class CustomFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            suggestions.clear()
            constraint.let {
                for (i in searchListItems.indices) {
                    if (searchListItems[i].title.toLowerCase(Locale.ENGLISH).contains(constraint)) { // Compare item in original searchListItems if it contains constraints.
                        suggestions.add(searchListItems[i]) // If TRUE add item in Suggestions.
                    }
                }
            }
            val results = FilterResults() // Create new Filter Results and return this to publishResults;
            results.values = suggestions
            results.count = suggestions.size
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }

}