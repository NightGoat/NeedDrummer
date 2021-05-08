package ru.nightgoat.needdrummer.repos.Interfaces

interface IResourcesRepo {
    val authError: String
    val registerError: String
    val rememberPassError: String
    val wrongEmailOrPass: String
}