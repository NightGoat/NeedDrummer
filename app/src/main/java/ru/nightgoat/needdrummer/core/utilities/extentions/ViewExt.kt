package ru.nightgoat.needdrummer.core.utilities.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.children
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

data class ScrollPosition(var index: Int = 0, var top: Int = 0) {
    fun drop() {
        index = 0
        top = 0
    }
}


fun View.setVisible(visibility: Boolean = true) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun View.setVisibleGone(isGone: Boolean = true) {
    setVisible(!isGone)
}

fun View.setInvisible(invisible: Boolean = true) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun Drawable.tint(@ColorInt color: Int) {
    val wrapDrawable = DrawableCompat.wrap(this.mutate())
    DrawableCompat.setTint(wrapDrawable, color)
    DrawableCompat.setTintMode(wrapDrawable, PorterDuff.Mode.SRC_IN)
}

var TextView.drawableStart: Drawable?
    get() = drawables[0]
    set(value) = setDrawables(value, drawableTop, drawableEnd, drawableBottom)

var TextView.drawableTop: Drawable?
    get() = drawables[1]
    set(value) = setDrawables(drawableStart, value, drawableEnd, drawableBottom)

var TextView.drawableEnd: Drawable?
    get() = drawables[2]
    set(value) = setDrawables(drawableStart, drawableTop, value, drawableBottom)

var TextView.drawableBottom: Drawable?
    get() = drawables[3]
    set(value) = setDrawables(drawableStart, drawableTop, drawableEnd, value)

private val TextView.drawables: Array<Drawable?>
    @SuppressLint("ObsoleteSdkInt")
    get() = if (Build.VERSION.SDK_INT >= 17) compoundDrawablesRelative else compoundDrawables

private fun TextView.setDrawables(start: Drawable?, top: Drawable?, end: Drawable?, buttom: Drawable?) {
    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= 17)
        setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, buttom)
    else
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, buttom)
}


fun TextView.clear() {
    this.text = null
    this.setOnClickListener(null)
}

fun ImageView.clear() {
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
}

fun Button.clear(isClearText: Boolean = true) {
    if (isClearText) this.text = null
}

fun CheckBox.clear() {
    this.setOnCheckedChangeListener(null)
}

fun Toolbar.clear() {
    this.removeAllViews()
    this.title = null
    this.setNavigationOnClickListener(null)
    this.setOnMenuItemClickListener(null)
}

fun AutoCompleteTextView.clear() {
    onFocusChangeListener = null
    setOnEditorActionListener(null)
    setAdapter(null)
}

fun ViewGroup.clear() {
    this.children
        .asSequence()
        .forEach { it.clearView() }
}

fun ViewPager.clear() {
    this.adapter = null
    this.clearOnPageChangeListeners()
}

fun ViewPager2.clear() {
    this.adapter = null
}

fun TabLayout.clear() {
    this.clearOnTabSelectedListeners()
    this.removeAllTabs()
}

fun EditText.clear() {
    setOnEditorActionListener(null)
    onFocusChangeListener = null
}

fun SeekBar.clear() {
    this.setOnSeekBarChangeListener(null)
}

fun Spinner.clear() {
    this.adapter = null
    this.onItemSelectedListener = null
    this.adapter = null
}

fun RatingBar.clear() {
    this.onRatingBarChangeListener = null
}

fun View?.clearView() {
    when (this) {
        is ImageView -> this.clear()
        is Button -> this.clear()
        is RatingBar -> this.clear()
        is AutoCompleteTextView -> this.clear()
        is EditText -> this.clear()
        is TextView -> this.clear()
        is CheckBox -> this.clear()
        is Toolbar -> this.clear()
        is SeekBar -> this.clear()
        is Spinner -> this.clear()
        is TabLayout -> this.clear()
        is AdapterView<*> -> Unit
        is ViewGroup -> this.clear()
        is ViewPager -> this.clear()
        is ViewPager2 -> this.clear()
    }
}

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setVisibility(isVisible: Boolean = true) {
    if(isVisible) this.show()
    else this.hide()
}

fun View.focusAndShowKeyboard() {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        // It’s very important to remove this listener once we are done.
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}