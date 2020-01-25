package tech.hibk.searchablespinnerlibrary

import java.io.Serializable

interface SearchableItemListener : Serializable {
    fun onSearchableItemClicked(item: SearchableItem, position: Int)
}