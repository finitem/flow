package com.merrillogic.flow;

import com.merrillogic.flow.memories.NoMemory
import rx.Observable
import rx.Subscription
import java.util.HashMap
import java.util.HashSet

public class Link<T, Y>(
        public val subscriber: Observer<Y>,
        public val subscribeFunction: (observer: Observable<T>) -> Subscription,
        public val verifier: LinkVerifier,
        lifecycle: Lifecycle?
    ) : Observer<LifecycleState>() {
    init {
        lifecycle?.subscribe(this, Observable.Transformer<LifecycleState, LifecycleState> { observable -> observable })
    }

    override fun onCompleted() {
        throw UnsupportedOperationException()
    }

    override fun onError(e: Throwable?) {
        throw UnsupportedOperationException()
    }

    override fun onNext(t: LifecycleState?) {
        throw UnsupportedOperationException()
    }

    override fun onLinkSevered() {
        throw UnsupportedOperationException()
    }
}

public interface LinkVerifier {
    public fun isActive(): Boolean
}

//TODO: Synchronization
abstract class Source<T>(
        val memory: Memory<T> = NoMemory<T>()) {
    val links: HashMap<Observer<out Any>, Link<T, out Any>> = HashMap()
    val startWorkOnSubscription = true;
    val isWorking = false;

    //TODO: Make emit and error not be such duplicates of eachother.
    fun emit(item: T) {
        val deadLinks: HashSet<Link<T, out Any>> = HashSet();
        for (link in links.values()) {
            if (link.verifier.isActive()) {
                link.subscribeFunction(Observable.just(item))
            } else {
                deadLinks.add(link);
            }
        }
        for (link in deadLinks) {
            links.remove(link.subscriber)
        }
    }

    fun error(thrown: Throwable) {
        val deadLinks: HashSet<Link<T, out Any>> = HashSet();
        for (link in links.values()) {
            if (link.verifier.isActive()) {
                link.subscribeFunction(Observable.error<T>(thrown))
            } else {
                deadLinks.add(link);
            }
        }
        for (link in deadLinks) {
            links.remove(link.subscriber)
        }
    }

    //If a catchup function, it's the above just with Observable.from(memory.getAll())). Easy. Yay observables.

    fun terminate() {
        for (link in links.values()) {
            if (link.verifier.isActive()) {
                link.subscriber.onLinkSevered()
            }
        }
        links.clear()
    }

    fun <Y> subscribe(
            subscriber: Observer<Y>,
            transform: Observable.Transformer<T, Y>,
            verifier: LinkVerifier = DummyVerifier(),
            lifecycle: Lifecycle? = null) {
        links.put(subscriber, Link(subscriber, { observable: Observable<T> -> observable.compose(transform).subscribe(subscriber) }, verifier, lifecycle))
        //TODO: Catch up with the memory.
    }

    fun unsubscribe(subscriber: Observer<out Any>) {
        links.remove(subscriber)
    }
}

class DummyVerifier : LinkVerifier {
    override fun isActive(): Boolean {
        return true
    }

}