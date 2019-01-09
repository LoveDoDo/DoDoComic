package com.dodo.xinyue.dodocomic.database;

import android.content.Context;

import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.DaoMaster;
import com.dodo.xinyue.conan.database.bean.DaoSession;
import com.dodo.xinyue.dodocomic.database.helper.ReleaseOpenHelper;

import org.greenrobot.greendao.database.Database;

/**
 * DatabaseManager
 *
 * @author DoDo
 * @date 2018/10/31
 */
public class DatabaseManager {

    //数据库名称
    private static final String DB_NAME = "dodo_comic.db";
    //数据库版本 更新数据库需要增加该数值
    private static final int DB_VERSION = 1;

    private DaoSession mDaoSession = null;

    private DatabaseManager() {
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        initDao(context);
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, DB_NAME, DB_VERSION);
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        //将相应的dao传给对应module的DatabaseManager
        ConanDataBaseManager.getInstance().init(
                mDaoSession.getJiGuangMessageDao()
        );
    }

}
