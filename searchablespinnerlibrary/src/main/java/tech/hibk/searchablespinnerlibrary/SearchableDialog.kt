package tech.hibk.searchablespinnerlibrary

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper

class SearchableDialog(
    var context: Context,
    var itens: List<SearchableItem>,
    var title: String,
    var listener: (item: SearchableItem, position: Int) -> Unit,
    var cancelButtonText: String = "Cancel",
    var cancelButtonColor: Int? = null,
    var onlyLightTheme: Boolean = false
) {
    private val TAG = "SearchableDialog"
    private lateinit var alertDialog: AlertDialog
    var position: Int = 0
    var selected: SearchableItem? = null


    lateinit var searchListAdapter: SearchableListAdapter
    lateinit var listView: ListView
    /***
     *
     * show the seachable dialog
     */
    fun show() {

        val c: Context
        if (this.onlyLightTheme) {
            c = ContextThemeWrapper(context, R.style.LightTheme)
            context.setTheme(R.style.LightTheme)
        } else {
            c = ContextThemeWrapper(context, R.style.DayNightTheme)
            context.setTheme(R.style.DayNightTheme)
        }

        val adb = AlertDialog.Builder(c)
        val view = LayoutInflater.from(context).inflate(R.layout.searchable_dialog, null)
        val rippleViewClose = view.findViewById(R.id.close) as TextView
        val title = view.findViewById(R.id.spinerTitle) as TextView
        if(this.title.isNotBlank()) {
            title.text = this.title
        }else{
            title.visibility = View.GONE
        }

        listView = view.findViewById(R.id.list) as ListView

        val searchBox = view.findViewById(R.id.searchBox) as EditText
        searchListAdapter = SearchableListAdapter(context, itens)
        listView.adapter = searchListAdapter
        adb.setView(view)
        alertDialog = adb.create()
        alertDialog.setCancelable(true)

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, v, _, _ ->
            val t = v.findViewById<TextView>(R.id.t1)
            for (j in itens.indices) {
                if (t.text.toString().equals(itens[j].title, ignoreCase = true)) {
                    position = j
                    selected = itens[position]
                }
            }
            try {
                listener(selected!!, position)
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: e.toString())
            }

            alertDialog.dismiss()
        }

        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val filteredValues = arrayListOf<SearchableItem>()
                for (i in itens.indices) {
                    val item = itens[i]
                    if (item.title.toLowerCase().trim { it <= ' ' }.contains(searchBox.text.toString().toLowerCase().trim { it <= ' ' })) {
                        filteredValues.add(item)
                    }
                }
                searchListAdapter = SearchableListAdapter(context, filteredValues)
                listView.adapter = searchListAdapter
            }
        })

        if(cancelButtonText.isNotBlank()){
            rippleViewClose.text = cancelButtonText
            cancelButtonColor?.let { color ->
                rippleViewClose.setTextColor(color)
            }

            rippleViewClose.setOnClickListener { alertDialog.dismiss() }
        }else{
            rippleViewClose.visibility = View.GONE
        }
        alertDialog.show()
    }
}
