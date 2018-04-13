package com.whieenz.searchselect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whieenz on 2018/4/13.
 * 作用：
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnOpenSearchDialog;
    Button btnOpenFuzzMatch;
    private List<String> mDatas;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        btnOpenSearchDialog = view.findViewById(R.id.btn_open_search_dialog);
        btnOpenFuzzMatch = view.findViewById(R.id.btn_open_fuzz_match);
        textView = view.findViewById(R.id.tv_result);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOpenFuzzMatch.setOnClickListener(this);
        btnOpenSearchDialog.setOnClickListener(this);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_search_dialog:
                openSearchSelectDialog();
                break;
            case R.id.btn_open_fuzz_match:
                openFuzzMatchFragment();
                break;

        }
    }

    private void openFuzzMatchFragment() {
        Fragment fragment = new FuzzMatchFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void openSearchSelectDialog() {
        SerachSelectDialog.Builder alert = new SerachSelectDialog.Builder(getContext());
        alert.setListData(mDatas);
        alert.setTitle("请选择城市");
        alert.setSelectedListiner(new SerachSelectDialog.Builder.OnSelectedListiner() {
            @Override
            public void onSelected(String info) {
                textView.setText(info);
            }
        });
        SerachSelectDialog mDialog = alert.show();
        //设置Dialog 尺寸
        mDialog.setDialogWindowAttr(0.9, 0.9, getActivity());
    }

    /**
     * //     * 初始化数据
     * //
     */
    private void initData() {
        mDatas = new ArrayList<>();
        String[] citys = {"武汉", "北京", "上海", "深圳", "兰州", "成都", "天津"};
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < citys.length; j++) {
                mDatas.add(citys[j] + i);
            }
        }
    }
}
