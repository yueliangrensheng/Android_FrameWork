package com.yazao.base;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;

import com.yazao.lib.xbase.BaseActivity;

public abstract class BaseToolbarActivity<DB extends ViewDataBinding> extends BaseActivity<DB> {

    protected void setToolbar(Toolbar toolbar){
        //设置Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
