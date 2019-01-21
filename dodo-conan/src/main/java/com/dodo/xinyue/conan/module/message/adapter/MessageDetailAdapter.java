package com.dodo.xinyue.conan.module.message.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.module.message.data.MessageCenterItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * MessageDetailAdapter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageDetailAdapter extends MulAdapter {

    protected MessageDetailAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data, delegate);
    }

    public static MessageDetailAdapter create(List<MulEntity> data, DoDoDelegate delegate) {
        return new MessageDetailAdapter(data, delegate);
    }

    public static MessageDetailAdapter create(DataConverter converter, DoDoDelegate delegate) {
        return new MessageDetailAdapter(converter.convert(), delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(MessageCenterItemType.ITEM_MESSAGE_DETAIL, R.layout.item_message_detail)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case MessageCenterItemType.ITEM_MESSAGE_DETAIL:
                setMessageDetail(holder, entity);
                break;
            default:
                break;
        }
    }

    private void setMessageDetail(MulHolder holder, MulEntity entity) {
        holder.addOnClickListener(R.id.tvOpen)
                .addOnClickListener(R.id.rlContainer)
                .addOnLongClickListener(R.id.rlContainer);

        JiGuangMessage bean = entity.getBean();
        final JSONObject extraData = JSON.parseObject(bean.getExtraData());

        String title = bean.getTitle();
        holder.setText(R.id.tvTitle, title);

        long timestamp = bean.getTimestamp();
        holder.setText(R.id.tvTime, TimeUtils.getFriendlyTimeSpanByNow(timestamp));

        String cover = extraData.getString("cover");
        if (!TextUtils.isEmpty(cover)) {
            holder.setGone(R.id.ivPic, true);
        } else {
            holder.setGone(R.id.ivPic, false);
        }

        String content = bean.getContent();
        if (TextUtils.isEmpty(content)) {
            holder.setGone(R.id.tvContent, false);
            return;
        }

        final TextView tvContent = holder.getView(R.id.tvContent);
        boolean isHtml = extraData.getBooleanValue("html");
        if (!isHtml) {
            tvContent.setText(content);
        } else {
            //解决不能换行的问题
            tvContent.setText(Html.fromHtml(content.replace("\n","<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
        }

        holder.setGone(R.id.tvContent, true);
    }

}
