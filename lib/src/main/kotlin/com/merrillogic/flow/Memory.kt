package com.merrillogic.flow;

interface Memory<T> {
    fun getOnSubscribedItemList() : List<T>
    fun processEmittedItem(item: T)
}
