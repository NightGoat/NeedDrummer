package ru.nightgoat.needdrummer.data.base

interface IBaseRepo<out L : ILocalSource, out R : IRemoteSource> :
    IBaseLocalRepo<L>, IBaseRemoteRepo<R>

interface IBaseRemoteRepo<out R : IRemoteSource> {
    val remoteSource: R
}

interface IBaseLocalRepo<out R : ILocalSource> {
    val localSource: R
}

interface ILocalSource

interface IRemoteSource