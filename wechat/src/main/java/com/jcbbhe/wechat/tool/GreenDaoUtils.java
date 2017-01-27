package com.jcbbhe.wechat.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.jcbbhe.dao.DaoMaster;
import com.jcbbhe.dao.DaoSession;

/**
 * Created by jcbbhe on 17/1/6.
 */
public class GreenDaoUtils {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoUtils greenDaoUtils;

    private Context context;

    private GreenDaoUtils(Context context) {
        this.context = context;
    }

    public static GreenDaoUtils getSingleTon(Context c) {
        if (greenDaoUtils == null) {
            greenDaoUtils = new GreenDaoUtils(c);
        }
        return greenDaoUtils;
    }

    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(context, "test-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        if (mDaoMaster == null) {
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            initGreenDao();
        }
        return db;
    }
}
