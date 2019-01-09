package com.dodo.xinyue.conan.database;

import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;

/**
 * ConanDataBaseManager
 *
 * @author DoDo
 * @date 2018/10/31
 */
public class ConanDataBaseManager {

    private JiGuangMessageDao mMessageDao = null;

    private ConanDataBaseManager() {
    }

    private static final class Holder {
        private static final ConanDataBaseManager INSTANCE = new ConanDataBaseManager();
    }

    public static ConanDataBaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(JiGuangMessageDao messageDao) {
        this.mMessageDao = messageDao;
    }

    public final JiGuangMessageDao getMessageDao() {
        return mMessageDao;
    }

}
