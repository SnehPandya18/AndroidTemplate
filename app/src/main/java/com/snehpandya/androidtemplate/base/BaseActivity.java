package com.snehpandya.androidtemplate.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.snehpandya.androidtemplate.R;
import com.snehpandya.androidtemplate.util.NetworkUtil;

/**
 * Created by sneh.pandya on 08/03/18.
 */

public abstract class BaseActivity<DATABINDING extends ViewDataBinding, VIEWMODEL> extends AppCompatActivity {

    protected static final int DEFAULT_REQUEST_CODE = 100;
    protected static final int FRAGMENT_TRANSACTION_ADD = 200;
    protected static final int FRAGMENT_TRANSACTION_REPLACE = 300;

    protected DATABINDING mBinding;
    protected VIEWMODEL mViewModel;

    protected abstract void setViewModel(DATABINDING binding, VIEWMODEL viewModel);

    protected abstract VIEWMODEL createViewModel();

    protected abstract int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayout());
        mViewModel = createViewModel();
        setViewModel(mBinding, mViewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void launchActivity(Intent intent) {
        launchActivity(intent, false);
    }

    protected void launchActivity(Intent intent, boolean finishCurrent) {
        launchActivity(intent, DEFAULT_REQUEST_CODE, finishCurrent);
    }

    private void launchActivity(Intent intent, int requestCode, boolean finishCurrent) {
        if (requestCode != DEFAULT_REQUEST_CODE) {
            startActivityForResult(intent, requestCode);
        } else {
            if (finishCurrent) {
                finish();
            }
            startActivity(intent);
        }
    }

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, false);
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack) {
        loadFragment(fragment, FRAGMENT_TRANSACTION_ADD, addToBackStack);
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false);
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        loadFragment(fragment, FRAGMENT_TRANSACTION_REPLACE, addToBackStack);
    }

    private void loadFragment(Fragment fragment, int transactionType, boolean addToBackStack) {
        loadFragment(fragment, R.id.frame, transactionType, addToBackStack);
    }

    private void loadFragment(Fragment fragment, int containerId, int transactionType, boolean addToBackStack) {
        String fragmentName = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

    protected void showDialogFragment(AppCompatDialogFragment appCompatDialogFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(appCompatDialogFragment, appCompatDialogFragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void showDialogFragment(Fragment targetFragment,
                                      AppCompatDialogFragment appCompatDialogFragment, int requestCode) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        appCompatDialogFragment.setTargetFragment(targetFragment, requestCode);
        fragmentTransaction.add(appCompatDialogFragment, appCompatDialogFragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void showDialogFragment(BaseDialogFragment targetFragment,
                                      AppCompatDialogFragment appCompatDialogFragment, int requestCode) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        appCompatDialogFragment.setTargetFragment(targetFragment, requestCode);
        fragmentTransaction.add(appCompatDialogFragment, appCompatDialogFragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    protected void finishWithResultOk() {
        finishWithResultOk(null);
    }

    protected void finishWithResultOk(Intent intent) {
        hideKeyboard();
        if (intent == null) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    protected void finishWithResultCancel(Intent intent) {
        hideKeyboard();
        if (intent == null) {
            setResult(RESULT_CANCELED);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
        finish();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected boolean isInternetAvailable() {
        return NetworkUtil.isNetworkAvailable(this);
    }
}
