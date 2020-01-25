package tech.hibk.searchablespinnerlibrary

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getTextArrayOrThrow

class SearchableSpinner : Spinner, OnTouchListener {
    var items: List<SearchableItem> = listOf()
        set(value) {
            field = value
            setSelection(0)
            adapter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, items.map { it.title })
        }
    var dialogTitle = ""
    var dialogCancelButtonText = ""
    var dialogCancelButtonColor: Int? = null
    var dialogOnlyLightTheme: Boolean = false

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(att: AttributeSet?) {
        att?.let { attrs ->
            for (i in 0 until attrs.attributeCount) {
                Log.e("TE", attrs.getAttributeName(i))
            }

            val a = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner)
            for (i in 0 until a.indexCount) {
                when (val attr = a.getIndex(i)) {
                    R.styleable.SearchableSpinner_dialogTitle -> {
                        a.getString(attr)?.let {
                            dialogTitle = it
                        }
                    }
                    R.styleable.SearchableSpinner_onlyLightTheme -> {
                        dialogOnlyLightTheme = a.getBoolean(attr, false)
                    }
                    R.styleable.SearchableSpinner_cancelButtontext -> {
                        a.getString(attr)?.let {
                            dialogCancelButtonText = it
                        }
                    }
                    R.styleable.SearchableSpinner_cancelButtonColor -> {
                        try {
                            dialogCancelButtonColor = a.getColorOrThrow(attr)
                        } catch (e: Exception) {
                        }
                    }
                    R.styleable.SearchableSpinner_android_entries -> {
                        try {
                            items = a.getTextArrayOrThrow(attr)
                                .map { t -> SearchableItem(t.hashCode().toLong(), t.toString()) }
                        } catch (e: Exception) {
                        }
                    }

                }
            }
            a.recycle()
        }
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (!isSpinnerDialogOpen) {
                isSpinnerDialogOpen = true
                if (event.action == MotionEvent.ACTION_UP) {
                    SearchableDialog(
                        context, items, dialogTitle, { item, _ ->
                            adapter = ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, items.map { it.title })
                            setSelection(items.indexOf(item))
                        },
                        dialogCancelButtonText,
                        dialogCancelButtonColor,
                        dialogOnlyLightTheme
                    ).show()
                }
                return true
            }
            isSpinnerDialogOpen = false
        }
        Handler().postDelayed({ isSpinnerDialogOpen = false }, 500)
        return true
    }


    override fun getSelectedItem(): SearchableItem? {
        return items[this.selectedItemPosition]
    }

    override fun getSelectedItemId(): Long {
        return items[this.selectedItemPosition].id
    }

    companion object {
        var isSpinnerDialogOpen = false
    }

}