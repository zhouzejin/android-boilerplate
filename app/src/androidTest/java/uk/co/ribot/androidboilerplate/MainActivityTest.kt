package uk.co.ribot.androidboilerplate

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import uk.co.ribot.androidboilerplate.test.common.TestComponentRule
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory
import uk.co.ribot.androidboilerplate.ui.main.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val main: ActivityTestRule<MainActivity> =
            object : ActivityTestRule<MainActivity>(MainActivity::class.java, false, false) {
                override fun getActivityIntent(): Intent {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext(), false)
                }
            }

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @JvmField
    @Rule
    val chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun listOfSubjectsShows() {
        val testDataSubjects = TestDataFactory.makeListSubject(20)
        `when`(component.getMockDataManager().getSubjects())
                .thenReturn(Observable.just(testDataSubjects))

        main.launchActivity(null)

        for ((position, subject) in testDataSubjects.withIndex()) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
            onView(withText(subject.title()))
                    .check(matches(isDisplayed()))
        }
    }

}
