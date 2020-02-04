# Android Searchable Spinner Dialog

It also includes a Searchable Dialog.

*Written with Kotlin.

## Demo
![Preview](/preview.gif)


## Usage
Add the dependency:

    dependencies {
	        implementation 'tech.hibk.searchablespinnerlibrary:searchablespinnerlibrary:1.0.1'
	}


Using inside xml layout:


    <tech.hibk.searchablespinnerlibrary.SearchableSpinner
            android:id="@+id/spinner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="8dp"
            android:entries="@array/spinner_options"
            app:dialogTitle="Sample"
            app:cancelButtonColor="@color/colorPrimary"
            app:cancelButtontext="CLOSE"
            app:onlyLightTheme="false"
            app:nothingSelectedText="TEST"/>


*app parameters are optional

So you can use it as default Spinner:

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
    
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@SampleActivity, spinner.items[position].title, Toast.LENGTH_SHORT).show()
                }
    }


#### Setup a Dialog
    
    val items = List(100) { i ->
                SearchableItem(i.toLong(),"Test-$i")
                 }
    SearchableDialog(this,
                    items,
                    "Light Dialog",
                    {item, _ ->
                        Toast.makeText(this@SampleActivity, item.title, Toast.LENGTH_SHORT).show()
                    },
                    cancelButtonColor = ContextCompat.getColor(this, R.color.colorPrimary),
                    onlyLightTheme = true).show()
