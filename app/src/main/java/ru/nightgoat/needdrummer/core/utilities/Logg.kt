package ru.nightgoat.needdrummer.core.utilities

import timber.log.Timber

fun loggE(whatToLog: () -> String) {
    Timber.e(whatToLog.invoke())
}

fun loggE(exception: Exception) {
    Timber.e(exception)
}

fun loggD(whatToLog: () -> String) {
    Timber.d(whatToLog.invoke())
}

