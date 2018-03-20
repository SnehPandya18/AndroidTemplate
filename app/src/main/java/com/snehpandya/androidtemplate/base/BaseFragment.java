package com.snehpandya.androidtemplate.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snehpandya.androidtemplate.R;

/**
 * Created by sneh.pandya on 26/02/18.
 */

public abstract class BaseFragment<DATABINDING extends ViewDataBinding, VIEWMODEL> extends Fragment {

    private static final int FRAGMENT_TRANSACTION_ADD = 200;
    private static final int FRAGMENT_TRANSACTION_REPLACE = 300;

    protected BaseActivity mBaseActivity;
    protected Context mContext;
    protected DATABINDING mBinding;
    protected VIEWMODEL mViewmodel;

    protected abstract int getLayout();

    protected abstract VIEWMODEL createViewModel();

    protected abstract void setViewModel(DATABINDING binding, VIEWMODEL viewModel);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewmodel = createViewModel();
        setViewModel(mBinding, mViewmodel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mBaseActivity = (BaseActivity) context;
    }

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, false);
    }

    private void addFragment(Fragment fragment, boolean addToBackStack) {
        loadFragment(fragment, FRAGMENT_TRANSACTION_ADD, addToBackStack);
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        loadFragment(fragment, FRAGMENT_TRANSACTION_REPLACE, addToBackStack);
    }

    protected void loadFragment(Fragment fragment, int transactionType) {
        loadFragment(fragment, transactionType, false);
    }

    private void loadFragment(Fragment fragment, int transactionType, boolean addToBackStack) {
        loadFragment(fragment, R.id.frame, transactionType, addToBackStack);
    }

    private void loadFragment(Fragment fragment, int containerId, int transactionType, boolean addToBackStack) {
        String fragmentName = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = mBaseActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentName);
        }
        if (transactionType == FRAGMENT_TRANSACTION_ADD) {
            fragmentTransaction.add(containerId, fragment, fragmentName);
        } else {
            fragmentTransaction.replace(containerId, fragment, fragmentName);
        }
        fragmentTransaction.commit();
    }

    protected void launchActivity(Intent intent) {
        launchActivity(intent, BaseActivity.DEFAULT_REQUEST_CODE);
    }

    private void launchActivity(Intent intent, int requestCode) {
        if (requestCode == BaseActivity.DEFAULT_REQUEST_CODE) {
            mBaseActivity.launchActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyBoard() {
        mBaseActivity.hideKeyboard();
    }

    protected boolean isInternetAvailable() {
        return mBaseActivity.isInternetAvailable();
    }
}
