package com.snehpandya.androidtemplate.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sneh.pandya on 09/03/18.
 */

public abstract class BaseDialogFragment<DATABINDING extends ViewDataBinding,
        VIEWMODEL> extends AppCompatDialogFragment {

    private Context mContext;
    private DATABINDING mDataBinding;
    private VIEWMODEL mViewModel;

    private BaseActivity mBaseActivity;

    protected abstract int getLayout();

    protected abstract VIEWMODEL createViewModel();

    protected abstract void setViewModel(DATABINDING dataBinding, VIEWMODEL viewModel);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = createViewModel();
        setViewModel(mDataBinding, mViewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mBaseActivity = (BaseActivity) context;
    }

    protected void showDialogFragment(AppCompatDialogFragment targetAppCompatDialogFragment,
                                      AppCompatDialogFragment appCompatDialogFragment) {
        mBaseActivity.showDialogFragment(targetAppCompatDialogFragment,
                appCompatDialogFragment, BaseActivity.DEFAULT_REQUEST_CODE);
    }

    protected void launchActivity(Intent intent) {
        mBaseActivity.launchActivity(intent);
    }

    protected void launchActivity(Intent intent, boolean finishCurrent) {
        mBaseActivity.launchActivity(intent, finishCurrent);
    }

    protected void finishWithResultOk() {
        mBaseActivity.finishWithResultOk();
    }

    protected void showToast(String message) {
        mBaseActivity.showToast(message);
    }
}
