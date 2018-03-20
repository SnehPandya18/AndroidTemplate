package com.snehpandya.androidtemplate.base;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by sneh.pandya on 26/02/18.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mViewDataBinding;
    private Object mViewModel;
    private Object mModel;

    private BaseActivity mBaseActivity;

    public BaseViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.mViewDataBinding = viewDataBinding;
        mBaseActivity = (BaseActivity) this.mViewDataBinding.getRoot().getContext();
    }

    public Object getViewModel() {
        return mViewModel;
    }

    public Object getModel() {
        return mModel;
    }

    protected void launchActivity(Intent intent) {
        mBaseActivity.launchActivity(intent);
    }
}
