package uk.co.ribot.androidboilerplate.test.common

import android.content.Context

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.test.common.injection.component.DaggerTestComponent
import uk.co.ribot.androidboilerplate.test.common.injection.component.TestComponent
import uk.co.ribot.androidboilerplate.test.common.injection.module.ApplicationTestModule

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
class TestComponentRule(val context: Context) : TestRule {

    private val mTestComponent: TestComponent

    init {
        val application = BoilerplateApplication.get(context)
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(ApplicationTestModule(application))
                .build()
    }

    fun getMockDataManager(): DataManager {
        return mTestComponent.dataManager()
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val application = BoilerplateApplication.get(context)
                application.component = mTestComponent
                base.evaluate()
                application.component = null
            }
        }
    }
}
