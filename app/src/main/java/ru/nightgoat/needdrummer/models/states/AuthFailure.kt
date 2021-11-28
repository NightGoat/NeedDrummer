package ru.nightgoat.needdrummer.models.states

import com.rasalexman.sresult.data.dto.SResult

sealed class AuthFailure : SResult.AbstractFailure.Failure() {
    object EmptyName : AuthFailure()
    object EmptyEmail : AuthFailure()
    object EmptyPassword : AuthFailure()
    object BadEmail : AuthFailure()
}