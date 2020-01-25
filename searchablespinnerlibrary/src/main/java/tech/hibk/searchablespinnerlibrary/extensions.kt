package tech.hibk.searchablespinnerlibrary

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.toActivity(): Activity? {
    if(this is Activity){
        return this
    }
    if(this is ContextWrapper){
        return this.baseContext.toActivity()
    }
    return null
}