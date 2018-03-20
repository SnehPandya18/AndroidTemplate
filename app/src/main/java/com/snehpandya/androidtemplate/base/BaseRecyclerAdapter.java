package com.snehpandya.androidtemplate.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sneh.pandya on 26/02/18.
 */

public class BaseRecyclerAdapter<T, U extends BaseViewHolder,
        V extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_DATA = 1;

    protected List<T> mList = new ArrayList<>();

    @LayoutRes
    private int emptyViewLayoutResource;
    private Class<U> emptyViewHolder;

    @LayoutRes
    private int dataViewLayoutResource;
    private Class<V> dataViewHolder;
    private Object mViewModel;

    public BaseRecyclerAdapter(Object viewModel, int emptyViewLayoutResource,
                               Class<U> emptyViewHolder, int dataViewLayoutResource, Class<V> dataViewHolder) {
        this.mViewModel = viewModel;
        this.emptyViewLayoutResource = emptyViewLayoutResource;
        this.emptyViewHolder = emptyViewHolder;
        this.dataViewLayoutResource = dataViewLayoutResource;
        this.dataViewHolder = dataViewHolder;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
