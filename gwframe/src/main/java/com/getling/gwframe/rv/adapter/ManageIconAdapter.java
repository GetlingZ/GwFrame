package com.getling.gwframe.rv.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.getling.gwframe.R;
import com.getling.gwframe.rv.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: getling
 * @CreateDate: 2019/6/3 10:36
 * @Description:
 */
public class ManageIconAdapter extends BaseRecyclerViewAdapter<String> {
    private List<Integer> drawableList;

    public void setDrawable(List<Integer> drawableList) {
        this.drawableList = drawableList;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_manage_icon;
    }

    @Override
    protected void convert(BaseViewHolder holder, @NotNull String item) {
        holder.setText(R.id.tv_home_title, item);
        holder.getView(R.id.tv_home_title).setSelected(true);
        if (drawableList != null && drawableList.size() > holder.getAdapterPosition()) {
            ImageView iv = holder.getView(R.id.iv_home_icon);
            Glide.with(iv)
                    .load(drawableList.get(holder.getAdapterPosition()))
                    .into(iv);
        }
    }
}
