package uk.co.ribot.androidboilerplate.ui.main

import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.ui.base.MvpView

interface MainMvpView : MvpView {

    fun showSubjects(subjects: List<Subject>)
    fun showSubjectsEmpty()
    fun showError()

}
