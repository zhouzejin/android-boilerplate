package com.sunny.main.ui.main;

import com.sunny.commonbusiness.mvp.MvpView;
import com.sunny.datalayer.model.bean.Subject;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showSubjects(List<Subject> subjects);

    void showSubjectsEmpty();

    void showError();

}
