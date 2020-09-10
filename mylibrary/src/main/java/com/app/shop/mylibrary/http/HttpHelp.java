package com.app.shop.mylibrary.http;

import android.content.Context;


import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.util.Map;


/**
 * Created by seven on 2016/5/21.
 */
public class HttpHelp {


    I_success i_success;
    I_failure i_failure;
    Context context;
    String url;


    public HttpHelp(I_success i_success, I_failure i_failure, Context context, String url) {
        this.i_failure = i_failure;
        this.i_success = i_success;
        this.context = context;
        this.url = url;
    }


    //这里添加自定义的一些操作
    public void getHttp(final Map map) {
        initHttpSecret(map);
    }


    /**
     * 网络请求:
     *
     * @param map 参数集合
     */
    private void initHttpSecret(final Map map) {
        //这里封装公共参数
        HttpPublishConfig.setPublicParam(map);

        String paramJson = new Gson().toJson(map);

        OkGo.<String>post(url).tag(context).upJson(paramJson).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Logger.v("-----------HttpURL----" + url);
                Logger.v("-----------paramJson--" + paramJson);
                Logger.v("-----------json-------" + response.body());

                String s = response.body();
                try {
                    i_success.doSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
