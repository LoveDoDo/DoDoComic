package com.dodo.xinyue.conan.database;

import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
import com.dodo.xinyue.conan.database.listener.IHandleMessage;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;

import java.util.List;

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

    public static long getMessageCount(int messageType) {
        return ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .buildCount()
                .count();
    }

    /**
     * 异步查询消息
     *
     * @param messageType 消息类型 JiGuangMessage.TYPE_
     * @param page        分页查询的页数 从0开始
     * @param count       返回的消息数
     */
    public static void queryMessageAsync(int messageType, int page, int count, final IHandleMessage listener) {
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully()) {
                    if (listener != null) {
                        final List<JiGuangMessage> result = (List<JiGuangMessage>) operation.getResult();
                        listener.onSuccess(result);
                    }
                    return;
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
        Query<JiGuangMessage> query = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .limit(count)
                .offset(page * count)
                .build();
        asyncSession.queryList(query);
    }

    public static void queryAllMessageAsync(int messageType, final IHandleMessage listener) {
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                if (operation.isCompletedSucessfully()) {
                    if (listener != null) {
                        final List<JiGuangMessage> result = (List<JiGuangMessage>) operation.getResult();
                        listener.onSuccess(result);
                    }
                    return;
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
        Query<JiGuangMessage> query = ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .build();
        asyncSession.queryList(query);
    }

    public static void deleteAllMessageAsync(int messageType, final IHandleMessage listener) {
        queryAllMessageAsync(messageType, new IHandleMessage() {
            @Override
            public void onSuccess(List<JiGuangMessage> result) {
                AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                        .getSession().startAsyncSession();
                asyncSession.setListenerMainThread(new AsyncOperationListener() {
                    @Override
                    public void onAsyncOperationCompleted(AsyncOperation operation) {
//                        Log.d("gsfgdgdbf", "删除完成，用时：" + operation.getDuration());
                        if (operation.isCompletedSucessfully()) {
                            if (listener != null) {
                                listener.onSuccess(null);
                            }
                            return;
                        }
                        if (listener != null) {
                            listener.onFailure();
                        }
                    }
                });
                asyncSession.deleteInTx(JiGuangMessage.class, result);
            }

            @Override
            public void onFailure() {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });

    }

}
