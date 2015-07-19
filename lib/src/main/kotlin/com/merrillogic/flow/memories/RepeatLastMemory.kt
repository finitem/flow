package com.merrillogic.flow.memories

import com.merrillogic.flow.Memory

import java.util.ArrayList

public class RepeatLastMemory<T> : Memory<T> {

    private var lastItem: T = null

    override fun getOnSubscribedItemList(): List<T> {
        val tempList = ArrayList<T>()
        //Disallow emitting null items.
        if (lastItem != null) {
            tempList.add(lastItem)
        }
        return tempList
    }

    override fun processEmittedItem(item: T) {
        lastItem = item
    }
}
