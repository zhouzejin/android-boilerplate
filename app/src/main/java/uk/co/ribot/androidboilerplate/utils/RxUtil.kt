package uk.co.ribot.androidboilerplate.utils

import io.reactivex.disposables.Disposable

fun dispose(disposable: Disposable?) {
    if (disposable?.isDisposed == false) {
        disposable.dispose()
    }
}
