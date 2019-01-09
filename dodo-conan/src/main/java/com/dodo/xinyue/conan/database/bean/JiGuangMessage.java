package com.dodo.xinyue.conan.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 极光推送的消息dao
 *
 * DAO(Data Access Object) 数据访问对象
 *
 * @author DoDo
 * @date 2018/10/31
 */
@Entity(nameInDb = "jiguang_message")
public class JiGuangMessage {

    //展示形式：不展示 默认
    public static final int FORM_NONE = 0;
    //展示形式：Dialog
    public static final int FORM_DIALOG = 1;
    //展示形式：通知
    public static final int FORM_NOTIFICATION = 2;

    //消息类型：其他 默认
    public static final int TYPE_NONE = 0;
    //消息类型：更新
    public static final int TYPE_UPDATE = 1;
    //消息类型：公告
    public static final int TYPE_NOTICE = 2;

    //主键
    @Id(autoincrement = true)
    private Long id;
    //消息ID
    private String messageID;
    //消息标题
    private String title;
    //消息内容
    private String content;
    //类型
    private int type;
    //更新信息
    private String updateInfo;
    //是否已读
    private boolean read;
    //时间戳
    private long timestamp;

    @Generated(hash = 85702317)
    public JiGuangMessage(Long id, String messageID, String title, String content,
            int type, String updateInfo, boolean read, long timestamp) {
        this.id = id;
        this.messageID = messageID;
        this.title = title;
        this.content = content;
        this.type = type;
        this.updateInfo = updateInfo;
        this.read = read;
        this.timestamp = timestamp;
    }
    @Generated(hash = 1137148594)
    public JiGuangMessage() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessageID() {
        return this.messageID;
    }
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUpdateInfo() {
        return this.updateInfo;
    }
    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
    public boolean getRead() {
        return this.read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}