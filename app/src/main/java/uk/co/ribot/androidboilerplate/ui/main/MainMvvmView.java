package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.ui.base.MvvmView;

public interface MainMvvmView extends MvvmView {

    void showSubjects(List<Subject> ribots);

    void showSubjectsEmpty();

    void showError();

}
