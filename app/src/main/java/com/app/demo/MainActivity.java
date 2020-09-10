package com.app.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.demo.fragments.DataFragment;
import com.app.demo.fragments.SetFragment;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_3)
    TextView txt3;

    Fragment mFragment;
    BaseFragment dataFragment, setFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeEnabled(false);
        initData();
    }


    private void initData() {
            initFragment();
    }


    private void initFragment() {


        mFragment = new Fragment();
        if (dataFragment == null) {
            dataFragment = new DataFragment();
        }
        txt1.setSelected(true);
        txt3.setSelected(false);
        switchContent(mFragment, dataFragment);
    }

    /**
     * 更换fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fragment_container, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @OnClick({R.id.txt_1, R.id.txt_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_1:
                checkPosition(1);
                break;

            case R.id.txt_3:
                checkPosition(3);
                break;
        }
    }


    private void checkPosition(int position) {
        if (position == 1) {
            if (dataFragment == null) {
                dataFragment = new DataFragment();
            }
            txt1.setSelected(true);
            txt3.setSelected(false);
            switchContent(mFragment, dataFragment);
        } else if (position == 3) {
            if (setFragment == null) {
                setFragment = new SetFragment();
            }
            txt1.setSelected(false);
            txt3.setSelected(true);
            switchContent(mFragment, setFragment);
        }

    }

}
