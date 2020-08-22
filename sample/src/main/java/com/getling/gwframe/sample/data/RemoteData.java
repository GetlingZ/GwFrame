package com.getling.gwframe.sample.data;

import androidx.appcompat.app.AppCompatActivity;

import com.getling.gwframe.data.source.DataSource;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: getling
 * @CreateDate: 2020/8/21 17:18
 * @Description:
 */
public class RemoteData implements DataSource.Remote {

    private AppCompatActivity activity;

    @Override
    public void setActivity(@NotNull AppCompatActivity activity) {
        this.activity = activity;
    }
}
