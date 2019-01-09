package com.dodo.xinyue.dodocomic.database.helper;

import android.content.Context;

import com.dodo.xinyue.conan.database.bean.DaoMaster;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * ReleaseOpenHelper
 * 必须继承DatabaseOpenHelper，不法升然无级数据库，不走onUpgrade。。。神坑！！
 *
 * @author DoDo
 * @date 2018/10/31
 */
public class ReleaseOpenHelper extends DatabaseOpenHelper {

    public ReleaseOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    @Override
    public void onCreate(Database db) {
        DaoMaster.createAllTables(db, false);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < newVersion) {
            //noinspection unchecked
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                        @Override
                        public void onCreateAllTables(Database db, boolean ifNotExists) {
                            DaoMaster.createAllTables(db, ifNotExists);
                        }

                        @Override
                        public void onDropAllTables(Database db, boolean ifExists) {
                            DaoMaster.dropAllTables(db, ifExists);
                        }
                    },
                    //这里添加Dao类，不是实体类 XxxDao.class
                    //不管新增还是修改，都需要添加在这里
                    //不能修改现有字段的类型，否则会丢失全部数据
                    JiGuangMessageDao.class
            );
        }
    }

}
