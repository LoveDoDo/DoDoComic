package com.dodo.xinyue.conan.database;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
import com.dodo.xinyue.conan.database.listener.IHandleMessage;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;

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

    public static void deleteByType(int messageType, IHandleMessage listener) {
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
//                Log.d("gsfgdgdbf", "删除完成，用时：" + operation.getDuration());
//                operation.getResult()
            }
        });
        Query<JiGuangMessage> query = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .build();
        asyncSession.queryList(query);
//        asyncSession.deleteInTx(JiGuangMessage.class, list);
    }

//    public static void

}
