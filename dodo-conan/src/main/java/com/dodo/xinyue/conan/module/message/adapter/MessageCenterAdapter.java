package com.dodo.xinyue.conan.module.message.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.database.bean.JiGuangMessage;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.module.message.data.MessageCenterItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.image.GlideApp;
import com.dodo.xinyue.core.ui.recycler.DataConverter;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulFields;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * MessageCenterAdapter
 *
 * @author DoDo
 * @date 2019/1/18
 */
public class MessageCenterAdapter extends MulAdapter {

    protected MessageCenterAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data, delegate);
    }

    public static MessageCenterAdapter create(List<MulEntity> data, DoDoDelegate delegate) {
        return new MessageCenterAdapter(data, delegate);
    }

    public static MessageCenterAdapter create(DataConverter converter, DoDoDelegate delegate) {
        return new MessageCenterAdapter(converter.convert(), delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(MessageCenterItemType.ITEM_MESSAGE_CENTER, R.layout.item_message_center)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case MessageCenterItemType.ITEM_MESSAGE_CENTER:
                setMessageCenter(holder, entity);
                break;
            default:
                break;
        }

    }

    private void setMessageCenter(MulHolder holder, MulEntity entity) {
        int cover = entity.getField(MulFields.DATA);
        GlideApp.with(mContext)
                .load(cover)
                .apply(DEFAULT_OPTIONS)
                .transform(new CircleCrop())
                .into((ImageView) holder.getView(R.id.ivCover));

        JiGuangMessage bean = entity.getBean();
        int type = bean.getType();
        String title = ApiHelper.getMessageNickName(type);
        holder.setText(R.id.tvTitle, title);

        if (TextUtils.isEmpty(bean.getMessageID())) {
            holder.setGone(R.id.tvContent, false);
            holder.setGone(R.id.tvTime, false);
            holder.setGone(R.id.tvQuiet, false);
            holder.setGone(R.id.ivPic, false);
            boolean isQuiet = ApiHelper.getMessageIsQuiet(type);
            if (isQuiet) {
                holder.setGone(R.id.tvQuiet2, true);
            } else {
                holder.setGone(R.id.tvQuiet2, false);
            }
            return;
        }

        holder.setGone(R.id.tvQuiet2, false);

        long timestamp = bean.getTimestamp();
        holder.setText(R.id.tvTime, TimeUtils.getFriendlyTimeSpanByNow(timestamp));
        holder.setGone(R.id.tvTime, true);

        boolean isQuiet = ApiHelper.getMessageIsQuiet(type);
        if (isQuiet) {
            holder.setGone(R.id.tvQuiet, true);
        } else {
            holder.setGone(R.id.tvQuiet, false);
        }

        String content = bean.getContent();
        if (TextUtils.isEmpty(content)) {
            holder.setGone(R.id.ivPic, true);
            return;
        }

        holder.setGone(R.id.ivPic, false);

        final TextView tvContent = holder.getView(R.id.tvContent);
        final JSONObject extraData = JSON.parseObject(bean.getExtraData());
        boolean isHtml = extraData.getBooleanValue("html");
        if (!isHtml) {
            tvContent.setText(content);
        } else {
            //解决不能换行的问题
            tvContent.setText(Html.fromHtml(content.replace("\n", "<br>")));//例如："这是<font color=#ff0000>红色</font>,这是<font color=#0000ff>蓝色</font>"
        }
        holder.setGone(R.id.tvContent, true);

    }

}
