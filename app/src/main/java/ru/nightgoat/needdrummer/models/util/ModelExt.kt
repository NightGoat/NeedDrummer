package ru.nightgoat.needdrummer.models.util

fun String.toEmail() = Email(this)
fun String.toPassword() = Password(this)
fun String.toName() = Name(this)