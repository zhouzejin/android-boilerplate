package uk.co.ribot.androidboilerplate.ui.main;

import com.sunny.commonbusiness.base.MvpView;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.bean.Subject;

public interface MainMvpView extends MvpView {

    void showSubjects(List<Subject> subjects);

    void showSubjectsEmpty();

    void showError();

}
