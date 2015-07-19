package com.merrillogic.flow.memories

import com.merrillogic.flow.Memory
import java.util.ArrayList

public class NoMemory<T> : Memory<T> {
    private val mMemory = ArrayList<T>()
    override fun getOnSubscribedItemList(): List<T> {
        return mMemory
    }

    override fun processEmittedItem(item: T) {
        //do nothing
    }
}
