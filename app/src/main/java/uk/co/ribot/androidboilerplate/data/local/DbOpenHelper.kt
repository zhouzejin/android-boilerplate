package uk.co.ribot.androidboilerplate.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import javax.inject.Inject
import javax.inject.Singleton

import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext

@Singleton
class DbOpenHelper @Inject constructor(@ApplicationContext context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ribots.db"
        const val DATABASE_VERSION = 2
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        //Uncomment line below if you want to enable foreign keys
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(Subject.CREATE_TABLE)
            //Add other tables here
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}
