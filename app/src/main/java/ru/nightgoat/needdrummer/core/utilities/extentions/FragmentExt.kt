package ru.nightgoat.needdrummer.core.utilities.extentions

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

fun <T : ViewModel> Fragment.provideViewModel(clazz: Class<T>): T {
    return ViewModelProvider(this).get(clazz)
}

fun <T> Fragment.connectLiveData(source: MutableLiveData<out T>, target: MutableLiveData<T>) {
    this.viewLifecycleOwner.connectLiveData(source, target)
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.navigateTo(action: Int) {
    findNavController().navigate(action)
}

fun Fragment.navigateTo(action: Int, bundle: Bundle) {
    findNavController().navigate(action, bundle)
}

fun Fragment.showShortToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showLongToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}