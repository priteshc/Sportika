package com.webcubictechnologies.remitnow.koroutines

/**
 * Created by webcubictech2 on 5/1/18.
 */
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor

/**
 * Created by thepunkprogrammer on 12/7/17.
 */
private class AndroidContinuation<T>(val cont: Continuation<T>): Continuation<T> by cont {

    override fun resume(value: T) {
        if (Looper.myLooper() == Looper.getMainLooper()) cont.resume(value)
        else Handler(Looper.getMainLooper()).post { cont.resume(value) }
    }

    override fun resumeWithException(exception: Throwable) {
        if (Looper.myLooper() == Looper.getMainLooper()) cont.resumeWithException(exception)
        else Handler(Looper.getMainLooper()).post { cont.resumeWithException(exception) }
    }
}

object UI: AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> = AndroidContinuation(continuation)
}

inline fun <T> bg(crossinline f: () -> T): Deferred<T> {
    return async(CommonPool) {
        delay(500)
        f()
    }
}