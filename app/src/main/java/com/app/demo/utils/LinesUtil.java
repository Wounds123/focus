package com.app.demo.utils;

import android.app.Dialog;
import android.content.Context;

import com.app.shop.mylibrary.http.HttpHelp;
import com.app.shop.mylibrary.http.I_failure;
import com.app.shop.mylibrary.http.I_success;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.widgts.LoadingDialog;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @time :   2020/3/31
 * @description :
 */


public class LinesUtil {

    public static final String APPKEY = "998c2c343ae07a3f";// 你的appkey
    public static final String Turn_URL = "https://api.jisuapi.com/transit/station2s"; //换乘
    public static final String Near_URL = "https://api.jisuapi.com/transit/nearby"; //附近
    public static final String city = "北京市";// utf-8
    public static final String start = "西溪竞舟苑";// utf-8
    public static final String end = "杭州汽车北站";// utf-8
    public static final String type = "";

    //换乘
    public static void GetTurnLine(Context context, String city, String start, String end, I_success i_success) {
//        String url = URL + "?city=" + URLEncoder.encode(city, "utf-8") + "&start=" + URLEncoder.encode(start, "utf-8") + "&end" + URLEncoder.encode(end, "utf-8") + "&appkey=" + APPKEY;
        String url = Turn_URL + "?city=" + city + "&start=" + start + "&end=" + end + "&endcity=" + "&appkey=" + APPKEY;
        getHttp(context, url, i_success);
    }


    //附近站点
    public static void GetNearStation(Context context, String city, String address, I_success i_success) {
//        String url = URL + "?city=" + URLEncoder.encode(city, "utf-8") + "&start=" + URLEncoder.encode(start, "utf-8") + "&end" + URLEncoder.encode(end, "utf-8") + "&appkey=" + APPKEY;
        String url = Near_URL + "?city=" + city + "&address=" + address + "&appkey=" + APPKEY;
        getHttp(context, url, i_success);
    }


    public static void getHttp(Context context, String url, I_success i_success) {

        Dialog mLoadingDialog = LoadingDialog.createLoadingDialog(context, true, "正在查询，请稍后");
        LoadingDialog.showDialog(mLoadingDialog);
        new HttpHelp(new I_success() {
            @Override
            public void doSuccess(String t) throws JSONException {
                LoadingDialog.closeDialog(mLoadingDialog);
                Logger.e("---------t----  " + t);
                String result = "";
                String msg = "";

                try {
                    JSONObject jsonObject = new JSONObject(t);
                    result = jsonObject.getString("status");
                    msg = jsonObject.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result.equals("0")) {
                    i_success.doSuccess(t);
                } else {
                    ToastUtil.showToast(context, msg);
                }


            }
        }, new I_failure() {
            @Override
            public void doFailure() {

            }
        }, context, url).getHttp(new HashMap());
    }
}
