package com.dodo.xinyue.conan.main.mine.adapter;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.main.mine.bean.MineBean;
import com.dodo.xinyue.conan.main.mine.bean.MineItemType;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.ui.recycler.ItemTypeBuilder;
import com.dodo.xinyue.core.ui.recycler.MulAdapter;
import com.dodo.xinyue.core.ui.recycler.MulEntity;
import com.dodo.xinyue.core.ui.recycler.MulHolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * MineAdapter
 *
 * @author DoDo
 * @date 2018/10/25
 */
public class MineAdapter extends MulAdapter {

    private MineAdapter(List<MulEntity> data, DoDoDelegate delegate) {
        super(data, delegate);
    }

    public static MineAdapter create(List<MulEntity> data, DoDoDelegate delegate) {
        return new MineAdapter(data, delegate);
    }

    @Override
    public LinkedHashMap<Integer, Integer> addItemTypes(ItemTypeBuilder builder) {
        return builder
                .addItemType(MineItemType.ITEM, R.layout.item_mine)
                .addItemType(MineItemType.NONE, R.layout.item_mine_none)
                .build();
    }

    @Override
    public void handle(MulHolder holder, MulEntity entity) {
        switch (holder.getItemViewType()) {
            case MineItemType.ITEM:
                final MineBean bean = entity.getBean();
                holder.setText(R.id.tvIcon, bean.getIcon())
                        .setText(R.id.tvTitle, bean.getTitle());
                break;
            default:
                break;
        }
    }
}
