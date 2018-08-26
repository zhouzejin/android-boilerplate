package uk.co.ribot.androidboilerplate.data.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.content.Context
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import uk.co.ribot.androidboilerplate.utils.i
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbOpenHelper @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val DATABASE_NAME = "ribots.db"
        const val DATABASE_VERSION = 2
    }

    val openHelper: SupportSQLiteOpenHelper

    init {
        val configuration = SupportSQLiteOpenHelper.Configuration
                .builder(context)
                .name(DATABASE_NAME)
                .callback(DbCallback(DATABASE_VERSION))
                .build()
        val factory = FrameworkSQLiteOpenHelperFactory()
        openHelper = factory.create(configuration)
    }

    private class DbCallback constructor(version: Int) : SupportSQLiteOpenHelper.Callback(version) {
        override fun onConfigure(db: SupportSQLiteDatabase) {
            super.onConfigure(db)
            //Uncomment line below if you want to enable foreign keys
            //db.execSQL("PRAGMA foreign_keys=ON;");
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            db.beginTransaction()
            try {
                db.execSQL(Subject.CREATE_TABLE)
                //Add other tables here
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }

        override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
            i("升级数据库：db = [" + db.path + "], " +
                    "oldVersion = [" + oldVersion + "], currentVersion = [" + newVersion + "]")
        }
    }

}
