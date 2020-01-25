package tech.hibk.searchablespinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sample.*
import tech.hibk.searchablespinnerlibrary.SearchableDialog
import tech.hibk.searchablespinnerlibrary.SearchableItem

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = List(100) { i ->
            SearchableItem(i.toLong(),"Test-$i")
             }


        setContentView(R.layout.activity_sample)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@SampleActivity, spinner.items[position].title, Toast.LENGTH_SHORT).show()
            }
        }

        spinner2.items = items
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@SampleActivity, items[position].title, Toast.LENGTH_SHORT).show()
            }
        }

        showDialog.setOnClickListener {
            SearchableDialog(this,
                items,
                "Day Night Dialog",
                {item, _ ->
                    Toast.makeText(this@SampleActivity, item.title, Toast.LENGTH_SHORT).show()
                }).show()
        }

        showLightDialog.setOnClickListener {
            SearchableDialog(this,
                items,
                "Light Dialog",
                {item, _ ->
                    Toast.makeText(this@SampleActivity, item.title, Toast.LENGTH_SHORT).show()
                },
                cancelButtonColor = ContextCompat.getColor(this, R.color.colorPrimary),
                onlyLightTheme = true).show()
        }


        darkMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        lightMode.setOnClickListener {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}