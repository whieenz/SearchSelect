package com.whieenz.searchselect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
//    private List<String> mDatas;
//    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
//        textView = (TextView) findViewById(R.id.tv_result);
//        initData();
        Fragment fragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

//    public void doSelect(View view) {
//        SerachSelectDialog.Builder alert = new SerachSelectDialog.Builder(this);
//        alert.setListData(mDatas);
//        alert.setTitle("请选择城市");
//        alert.setSelectedListiner(new SerachSelectDialog.Builder.OnSelectedListiner() {
//            @Override
//            public void onSelected(String info) {
//                textView.setText(info);
//            }
//        });
//        SerachSelectDialog mDialog = alert.show();
//        //设置Dialog 尺寸
//        mDialog.setDialogWindowAttr(0.9, 0.9, this);
//    }

    public void openFuzzMatch(View view) {
        Fragment fragment = new Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
}
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        mDatas = new ArrayList<>();
//        String[] citys = {"武汉", "北京", "上海", "深圳", "兰州", "成都", "天津"};
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < citys.length; j++) {
//                mDatas.add(citys[j] + i);
//            }
//        }
//    }

//}
