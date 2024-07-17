package com.tdd.dogformation.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.Locale


fun Context.hasCameraFeature() =
    packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun Context.openAppPermissionSettings() = startActivity(
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.assignment.one")
    )
)

fun Fragment.navigateTo(action: NavDirections) {
    findNavController().navigate(action)
}

fun Context.hasPermission(permission: String) =
    PermissionChecker.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED

fun Context.hasAllPermissions() =
    hasPermission(Manifest.permission.RECORD_AUDIO) && hasPermission(Manifest.permission.CAMERA) && if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    } else {
        true
    }

fun <T, V> CoroutineScope.asyncAll(list: List<T>, block: suspend (T) -> V): List<Deferred<V>> {
    return list.map {
        async { block.invoke(it) } // Creates a coroutine and returns its future result as an implementation of Deferred.
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}

fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(
            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) // Cache images for better performance
                .fitCenter()
        )
        .into(this)
}

fun String.toCapitalizedWords(): String {
    return this.split(",").joinToString(", ") {
        it.trim().replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase() else char.toString()}
    }
}