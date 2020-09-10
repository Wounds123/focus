package com.app.demo.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.demo.R;
import com.app.demo.activitys.AddScheduleActivity;
import com.app.demo.activitys.MessageDetailActivity;
import com.app.demo.adapters.CalendarListAdapter;
import com.app.demo.beans.MessageBean;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerCallback;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerUtil;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends BaseFragment implements CalendarView.OnCalendarSelectListener {

    @BindView(R.id.calendarRecyclerView)
    RecyclerView calendarRecyclerView;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @BindView(R.id.todayText)
    TextView todayText;


    private int selectYear;
    private int selectMonth;
    private int selectDay;
    private String selectTime = "";
    private String todayTime = "";

    private View emptyView;
    private boolean firstTime = true;

    private CalendarListAdapter adapter;
    private List<MessageBean> list_all = new ArrayList<>();
    private List<MessageBean> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initList();

        todayTime = calendarView.getCurYear() + "-" + append0(calendarView.getCurMonth()) + "-" + append0(calendarView.getCurDay());

        calendarView.setOnCalendarSelectListener(this);
        calendarView.setRange(2020, 1, 1, calendarView.getCurYear(), 12, 31);
        calendarView.scrollToCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay());

        initData(todayTime);
        return view;
    }

    private void initData(String time_default) {

        point(); //笔记红点

        list_all = DataSupport.findAll(MessageBean.class);
        list.clear();
        for (int i = 0; i < list_all.size(); i++) {
//            if (list_all.get(i).getMessageDate().equals(time_default)) {
//                list.add(list_all.get(i));
//            }

            if (list_all.get(i).getIsDel() == 0) {
                list.add(list_all.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void point() {
        calendarView.clearSchemeDate();

        Map<String, Calendar> map = new ConcurrentHashMap<>();

        map.clear();

        for (int i = 0; i < list_all.size(); i++) {

            String messageDate = list_all.get(i).getMessageDate();
            int year = Integer.parseInt(messageDate.substring(0, 4));
            int month = Integer.parseInt(messageDate.substring(5, 7));
            int day = Integer.parseInt(messageDate.substring(8, 10));
            Calendar calendar = getCalendar(year, month, day);
            map.put(calendar.toString(), calendar);
        }
        calendarView.setSchemeDate(map);

    }


    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        return calendar;
    }


    private void initList() {
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CalendarListAdapter(R.layout.view_calendarview_list_item, list);
        View headView = getLayoutInflater().inflate(R.layout.view_calendar_list_head, null);
        emptyView = headView.findViewById(R.id.emptyView);
        ImageView iv_add = headView.findViewById(R.id.iv_add);
        adapter.setHeaderView(headView);
        calendarRecyclerView.setAdapter(adapter);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipActivity(getActivity(), AddScheduleActivity.class);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("messageBean", list.get(position));
                skipActivity(getActivity(), MessageDetailActivity.class, bundle);
            }
        });
    }

    @OnClick({R.id.toToday, R.id.todayText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toToday:
                calendarView.scrollToCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay());
                break;

            case R.id.todayText:
                TimePickerUtil mTimePicker = new TimePickerUtil();
                int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                java.util.Calendar endDate = java.util.Calendar.getInstance();
                endDate.setTimeInMillis(System.currentTimeMillis());
                endDate.set(year, 11, 31);

                java.util.Calendar selectDate = java.util.Calendar.getInstance();
                if (selectYear != 0) {
                    selectDate.set(selectYear, selectMonth - 1, selectDay);
                }

                java.util.Calendar startDate = java.util.Calendar.getInstance();
                startDate.set(2020, 0, 1);


                mTimePicker.setDatePicker(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY, selectDate, startDate, endDate, new TimePickerCallback() {
                    @Override
                    public void setTimeCallback(int order, int year, int month, int day, int hour, int min, String idcard) {
                        calendarView.scrollToCalendar(year, month, day);
                        selectYear = year;
                        selectMonth = month;
                        selectDay = day;
                    }
                });
                mTimePicker.showTimePicker(0);
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        todayText.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
        String time = calendar.getYear() + "-" + append0(calendar.getMonth()) + "-" + append0(calendar.getDay());

        if (!selectTime.equals(time)) {
            if (firstTime) {
                if (todayTime.equals(time)) {
                    firstTime = false;
                    selectTime = time;
                }
            } else {
                selectTime = time;
            }
            if (!"".equals(selectTime)) {

                initData(selectTime);
            }
        }
    }


    private String append0(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return String.valueOf(value);
    }


    public static String removeZero(String s) {
        if (TextUtils.isEmpty(s)) return "";
        if (s.startsWith("0")) {
            s = s.substring(1);
        }
        return s;
    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.Refresh) {
            initData(selectTime);
        }
    }

}
