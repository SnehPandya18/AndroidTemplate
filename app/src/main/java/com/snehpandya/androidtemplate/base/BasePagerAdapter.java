package com.snehpandya.androidtemplate.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sneh.pandya on 09/03/18.
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mList = new ArrayList<>();

    public BasePagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(getViewType(position), container, false);
        return bindDataAndReturnView(container, view, getItemAtPosition(position), getViewType(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    protected abstract int getViewType(int position);

    protected abstract View bindDataAndReturnView(ViewGroup container, View view, T model, int viewType);

    protected T getItemAtPosition(int position) {
        return mList.get(position);
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
