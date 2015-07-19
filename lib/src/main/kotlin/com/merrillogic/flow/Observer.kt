package com.merrillogic.flow

import rx.Observer

abstract class Observer<T>(): rx.Observer<T> {
    abstract fun onLinkSevered()
}