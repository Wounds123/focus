package com.app.demo.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.demo.R;
import com.app.demo.activitys.HuishouzhanActivity;
import com.app.demo.activitys.LoginActivity;
import com.app.demo.activitys.PassWordResetActivity;
import com.app.demo.activitys.TypeManageActivity;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.widgts.CustomDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragment extends BaseFragment {

    private String dialog_title = "退出登录";
    private String dialog_content = "是否确定退出登录？";
    private CustomDialog customDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.rela_type, R.id.rela_pwd_reset, R.id.rela_logout,R.id.rela_huishouzhan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rela_type:
                skipActivity(getActivity(), TypeManageActivity.class);
                break;
            case R.id.rela_pwd_reset:
                skipActivity(getActivity(), PassWordResetActivity.class);
                break;
            case R.id.rela_logout:
                Logout();
                break;
            case R.id.rela_huishouzhan:
                skipActivity(getActivity(), HuishouzhanActivity.class);
                break;
        }
    }

    private void Logout() {
        customDialog = DialogUtil.showDialog(getActivity(), customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferencesUtil.removeAll(getContext(), "user");
                getActivity().finish();
                skipActivity(getActivity(), LoginActivity.class);

            }
        });

        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.show();
        }

        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                customDialog = null;
            }
        });
    }
}
