package uk.co.ribot.androidboilerplate.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.content.Context;

import com.sunny.common.injection.qualifier.ApplicationContext;
import com.sunny.common.utils.LogUtil;
import com.sunny.datalayer.model.bean.Subject;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbOpenHelper {

    public static final String DATABASE_NAME = "ribots.db";
    public static final int DATABASE_VERSION = 2;

    private SupportSQLiteOpenHelper mOpenHelper;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        SupportSQLiteOpenHelper.Configuration configuration = SupportSQLiteOpenHelper.Configuration
                .builder(context)
                .name(DATABASE_NAME)
                .callback(new DbCallback(DATABASE_VERSION))
                .build();
        FrameworkSQLiteOpenHelperFactory factory = new FrameworkSQLiteOpenHelperFactory();
        mOpenHelper = factory.create(configuration);
    }

    public SupportSQLiteOpenHelper getOpenHelper() {
        return mOpenHelper;
    }

    private static class DbCallback extends SupportSQLiteOpenHelper.Callback {
        DbCallback(int version) {
            super(version);
        }

        @Override
        public void onConfigure(SupportSQLiteDatabase db) {
            super.onConfigure(db);
            //Uncomment line below if you want to enable foreign keys
            //db.execSQL("PRAGMA foreign_keys=ON;");
        }

        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            db.beginTransaction();
            try {
                db.execSQL(Subject.CREATE_TABLE);
                //Add other tables here
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
            LogUtil.i("升级数据库：db = [" + db.getPath() + "], " +
                    "oldVersion = [" + oldVersion + "], currentVersion = [" + newVersion + "]");
        }
    }

}
