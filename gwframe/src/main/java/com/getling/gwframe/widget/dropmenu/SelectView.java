package com.getling.gwframe.widget.dropmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.getling.gwframe.R;
import com.getling.gwframe.utils.ListUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * @Author: getling
 * @CreateDate: 2019/9/6 9:15
 * @Description:
 */
public class SelectView extends LinearLayout {

    private Context context;

    private TabLayout tabLayout;
    private View line;

    private int currentTabPosition = -1;

    private SparseArray<PopupWindow> popupWindowList;
    private List<String> titleList;

    public SelectView(Context context) {
        super(context);
        init(context);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_select, this);
        this.context = context;
        popupWindowList = new SparseArray<>();

        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);

        line = findViewById(R.id.v_bottom_line);
        tabLayout = findViewById(R.id.tab_layout);
    }

    /**
     * 初始化筛选tab页，如果有传入筛选条件,并且筛选条件跟tab个数一致，构造popup window
     */
    public void initTab(List<String> titleList, List<List<String>> dataList) {
        if (titleList == null) {
            return;
        }
        this.titleList = titleList;
        for (String text : titleList) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.view_tab);
            TabViewHolder vh = new TabViewHolder(tab.getCustomView());
            vh.setText(text);
            vh.setImage(R.drawable.icon_drop_down_unselected);
            tabLayout.addTab(tab);
        }
        if (dataList != null && dataList.size() == titleList.size()) {
            for (int i = 0; i < dataList.size(); i++) {
                List<String> data = dataList.get(i);
                initPop(i, data);
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showDropDownMenu(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabImage(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                showDropDownMenu(tab);
            }
        });
    }

    /**
     * 更新popup window数据源
     */
    public void updatePop(int tabPosition, List<String> data) {
        updatePop(tabPosition, data, false, false);
    }

    public void updatePop(int tabPosition, List<String> data, boolean callClick, boolean showPop) {
        PopupWindow pop = popupWindowList.get(tabPosition);
        if (pop == null) {
            initPop(tabPosition, data);
        } else {
            ListView lv = (ListView) pop.getContentView();
            ListDropDownAdapter adapter = (ListDropDownAdapter) lv.getAdapter();
            adapter.setData(data);
            adapter.setCheckItem(0);
            if (ListUtil.isEmptyList(data)) {
                setTabText(tabPosition, titleList.get(tabPosition));
            } else {
                setTabText(tabPosition, data.get(0));
            }
        }
        if (onItemSelectedListener != null && callClick) {
            onItemSelectedListener.onItemSelected(tabPosition, 0);
        }
        if (showPop) {
            showDropDownMenu(tabLayout.getTabAt(tabPosition));
        }
    }

    /**
     * 获取不同筛选条件下，选定的项
     */
    public int getSelectedPosition(int tabPosition) {
        ListView lv = (ListView) popupWindowList.get(tabPosition).getContentView();
        ListDropDownAdapter adapter = (ListDropDownAdapter) lv.getAdapter();
        return adapter.getCheckItemPosition();
    }

    public void setTabText(int tabPosition, String text) {
        TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
        if (tab != null && tab.getCustomView() != null) {
            TabViewHolder vh = new TabViewHolder(tab.getCustomView());
            vh.setText(text);
        }
    }

    private void initPop(final int tabPosition, List<String> itemList) {
        ListView lv = new ListView(context);
        lv.setVerticalScrollBarEnabled(false);
        lv.setOverScrollMode(OVER_SCROLL_NEVER);
        lv.setDividerHeight(0);
        final ListDropDownAdapter adapter = new ListDropDownAdapter(context, itemList);
        lv.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(lv,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeTabImage(tabLayout.getTabAt(tabPosition), false);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTabText(tabPosition, adapter.getData().get(position));
                adapter.setCheckItem(position);
                popupWindow.dismiss();
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(currentTabPosition, position);
                }
            }
        });
        popupWindowList.put(tabPosition, popupWindow);
        if (ListUtil.isEmptyList(itemList)) {
            setTabText(tabPosition, titleList.get(tabPosition));
        } else {
            setTabText(tabPosition, itemList.get(0));
        }
    }

    private void showDropDownMenu(TabLayout.Tab tab) {
        KeyboardUtils.hideSoftInput(this);
        if (tab == null) {
            Toast.makeText(context, "暂无可选项", Toast.LENGTH_SHORT).show();
            return;
        }
        currentTabPosition = tab.getPosition();
        if (popupWindowList.get(currentTabPosition) != null) {
            popupWindowList.get(currentTabPosition)
                    .showAsDropDown(line);
            changeTabImage(tab, true);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(currentTabPosition);
            }
        } else {
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(currentTabPosition);
                return;
            }
            Toast.makeText(context, "暂无可选项", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeTabImage(TabLayout.Tab tab, boolean selected) {
        if (tab == null || tab.getCustomView() == null) {
            return;
        }
        TabViewHolder vh = new TabViewHolder(tab.getCustomView());
        if (selected) {
            vh.setImage(R.drawable.icon_drop_down_selected);
        } else {
            vh.setImage(R.drawable.icon_drop_down_unselected);
        }
    }

    private class TabViewHolder {
        private View itemView;
        private TextView tv;
        private ImageView iv;

        private TabViewHolder(View itemView) {
            this.itemView = itemView;
            tv = itemView.findViewById(R.id.tv_tab);
            iv = itemView.findViewById(R.id.iv_tab);
        }

        private void setText(String text) {
            tv.setText(text);
        }

        private void setImage(int resId) {
            iv.setImageResource(resId);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int tabPosition, int itemPosition);
    }

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnTabClickListener {
        void onTabClick(int tabPosition);
    }

    private OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }
}
