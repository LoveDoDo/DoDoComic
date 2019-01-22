package com.dodo.xinyue.conan.database.util;

import com.dodo.xinyue.conan.database.ConanDataBaseManager;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.database.bean.JiGuangMessageDao;
import com.dodo.xinyue.conan.database.listener.IHandleMessage;
import com.dodo.xinyue.core.util.log.DoDoLogger;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * ConanMessageDBUtil
 *
 * @author DoDo
 * @date 2019/1/22
 */
public class ConanMessageDBUtil {

    /**
     * 查询指定type的消息总数
     *
     * @param messageType
     * @return
     */
    public static long getMessageCount(int messageType) {
        return ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .buildCount()
                .count();
    }

    public static void getMessageCountAsync(int messageType, final IHandleMessage listener) {
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
        ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .build().setParameter()
//        asyncSession.queryList(query);
    }

    /**
     * 查询指定type的最新一条消息
     *
     * @param messageType
     * @return
     */
    public static JiGuangMessage getMessageAtFirst(int messageType) {
        return ConanDataBaseManager.getInstance().getMessageDao()
                .queryBuilder()
                .where(JiGuangMessageDao.Properties.Type.eq(messageType))
                .orderDesc(JiGuangMessageDao.Properties.Id)//倒序
                .limit(1)//限制返回结果数
                .unique();//返回唯一结果
    }

    /**
     * 异步分页查询指定type的所有消息
     *
     * @param messageType 消息类型 JiGuangMessage.TYPE_
     * @param page        分页查询的页数 从0开始
     * @param count       返回的消息数
     * @param listener
     */
    public static void queryMessageAsync(int messageType, int page, int count, final IHandleMessage listener) {
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                DoDoLogger.d("查询用时:" + operation.getDuration());
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

    /**
     * 异步查询指定type的所有消息
     *
     * @param messageType
     * @param listener
     */
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

    /**
     * 异步删除指定type的所有消息
     *
     * @param messageType
     * @param listener
     */
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

    /**
     * 异步批量插入多条消息
     *
     * @param listener
     */
    public static void insertOrReplaceInTxAsync(List<JiGuangMessage> messages, final IHandleMessage listener) {
        AsyncSession asyncSession = ConanDataBaseManager.getInstance().getMessageDao()
                .getSession().startAsyncSession();
        asyncSession.setListenerMainThread(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                ConanDataBaseManager.getInstance().getMessageDao().detachAll();//清除缓存
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
        asyncSession.insertOrReplaceInTx(JiGuangMessage.class, messages);
    }

    /**
     * 清除缓存
     * 适用于取出对象后需要改变对象属性
     */
    public static void clearMessageCache() {
        ConanDataBaseManager.getInstance().getMessageDao()
                .detachAll();
    }

}
