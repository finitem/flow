package com.merrillogic.flow

import com.merrillogic.flow.memories.RepeatLastMemory

public enum class LifecycleState {
    INIT,
    INIT_COMPLETE,
    ACTIVE,
    INACTIVE,
    FINISHING,
    DESTROYED
}

public open class Lifecycle(
		startState: LifecycleState = LifecycleState.INIT
) : Source<LifecycleState>(RepeatLastMemory<LifecycleState>()) {

    var currentState: LifecycleState = startState
    set(value) {
        currentState = value
        emit(value)
    }

    /**
     * Inclusive.
     * @param first
     * @param last
     */
    fun currentStateIsBetween(first: LifecycleState , last: LifecycleState) : Boolean {
        return first.compareTo(currentState) >= 0 && last.compareTo(currentState) <= 0;
    }
}