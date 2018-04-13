package com.whieenz.searchselect;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heziwen on 2017/7/18.
 */

public class SerachSelectDialog extends Dialog {

    public SerachSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置 Dialog的大小
     * @param x 宽比例
     * @param y  高比例
     */
    public  void setDialogWindowAttr(double x, double y, Activity activity){
        if (x<0||x>1||y<0||y>1){
            return;
        }
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (width * x);
        lp.height = (int) (height * y);
        this.getWindow().setAttributes(lp);
    }


    public static class Builder {
        private String title;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private List<String> listData;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private View layout;
        private Context context;
        private SerachSelectDialog dialog;
        private OnSelectedListiner selectedListiner;

        ListView listView;
        //SearchView searchView ;
        SearchView searchView;
        ImageButton searchBtn;
        ImageButton closeBtn;
        TextView titleView;
        private boolean state = false;

        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            this.context = context;
            dialog = new SerachSelectDialog(context,R.style.selectDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_select_search, null);
            listView = (ListView)layout.findViewById(R.id.listview);
            //searchView = (SearchView) layout.findViewById(R.id.searchView);
            searchView = (SearchView) layout.findViewById(R.id.searchView);
            searchBtn = (ImageButton) layout.findViewById(R.id.btn_dialog_select_search);
            closeBtn = (ImageButton) layout.findViewById(R.id.imb_dialog_select_close);
            titleView = (TextView) layout.findViewById(R.id.tv_dialog_select_title);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void setListData(List<String> listData) {
            this.listData = listData;
        }

        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private SerachSelectDialog create() {
            titleView.setText(title);
            final SearchSelectAdapter sa = new SearchSelectAdapter(context,listData);
            listView.setAdapter(sa);
            listView.invalidate();
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!state){
                        searchView.setVisibility(View.VISIBLE);
                        state = true;
                    }else {
                        searchView.setVisibility(View.GONE);
                        state = false;
                    }
                }
            });
            searchView.setSearchViewListener(new SearchView.onSearchViewListener() {

                @Override
                public boolean onQueryTextChange(String text) {
                    updateLayout(searchItem(text));
                    return false;
                }
            });
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedListiner.onSelected(sa.getItem(position));
                    dialog.dismiss();
                }
            });
            dialog.setContentView(layout);
            //用户可以点击手机Back键取消对话框显示
            dialog.setCancelable(true);
            //用户不能通过点击对话框之外的地方取消对话框显示
            dialog.setCanceledOnTouchOutside(false);
            return  dialog;

        }
        public List<String> searchItem(String name) {
            ArrayList<String> mSearchList = new ArrayList<String>();
            for (int i = 0; i < listData.size(); i++) {
                int index = listData.get(i).indexOf(name);
                // 存在匹配的数据
                if (index != -1) {
                    mSearchList.add(listData.get(i));
                }
            }
            return mSearchList;
        }

        public void updateLayout(List<String> newList) {
            final SearchSelectAdapter sa = new SearchSelectAdapter(context,newList);
            listView.setAdapter(sa);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedListiner.onSelected(sa.getItem(position));
                    dialog.dismiss();
                }
            });
        }



        public void setSelectedListiner(SerachSelectDialog.Builder.OnSelectedListiner selectedListiner) {
            this.selectedListiner = selectedListiner;
        }

        public static abstract class OnSelectedListiner{
            public abstract void onSelected(String String);
        }

        public SerachSelectDialog show() {
            create();
            dialog.show();
            return dialog;
        }

    }
}
