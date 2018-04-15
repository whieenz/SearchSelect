package com.whieenz.searchselect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whieenz on 2018/4/13.
 * 作用：
 */

public class FuzzMatchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvAge;
    private LinearLayout llSearchListView;

    private FuzzMatchPersonAdapter adapter;

    private PersonBean person = null;
    private boolean isShowSearchListView;

    private List<PersonBean> personBeans;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuzz_match, container, false);
        tvName = view.findViewById(R.id.tv_name);
        tvSex = view.findViewById(R.id.tv_sex);
        tvAge = view.findViewById(R.id.tv_age);
        llSearchListView = view.findViewById(R.id.ll_search_list);
        recyclerView = view.findViewById(R.id.rv_search);
        searchView = view.findViewById(R.id.sv_input);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        this.initSearchView();
        this.initTextView();
        this.initRecycleView();
    }

    private void initTextView() {
        llSearchListView.setVisibility(View.GONE);
        llSearchListView.setOnClickListener(this);
        isShowSearchListView = false;
    }

    private void initSearchView() {
        searchView.setSearchViewListener(new SearchView.onSearchViewListener() {
            @Override
            public boolean onQueryTextChange(String text) {
                if (!isShowSearchListView) {
                    llSearchListView.setVisibility(View.VISIBLE);
                    isShowSearchListView = true;
                }
                updateSelectList(text);
                return false;
            }
        });
    }

    private void initRecycleView() {
        adapter = new FuzzMatchPersonAdapter(getContext(), new ArrayList<PersonBean>());
        //设置recycleView 的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置recycleView item之间的分割线
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.parseColor("#f3f3f3")).size(3).build());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                person = adapter.getItemData(position);
                updateView();
                hideSearchViewResult();
            }
        });
    }


    private void updateView() {
        tvName.setText(person.getName());
        tvAge.setText(String.valueOf(person.getAge()));
        tvSex.setText(person.getSex());
    }

    private void updateSelectList(String text) {
        adapter.removeAllItems();
        List<PersonBean> personBeans = searchItems(text);
        adapter.addItems(personBeans);
    }

    public void hideSearchViewResult() {
        llSearchListView.setVisibility(View.GONE);
        isShowSearchListView = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search_list:
                hideSearchViewResult();
                break;
            default:
                break;
        }

    }

    private void initData() {
        personBeans = new ArrayList<>();
        String[] names = {"张三", "李四", "王五"};
        String[] sex = {"男", "女"};

        for (int i = 0; i < 100; i++) {
            PersonBean personBean = new PersonBean(names[i % 3] + i, sex[i % 2], i);
            personBeans.add(personBean);
        }
    }

    public List<PersonBean> searchItems(String name) {
        ArrayList<PersonBean> mSearchList = new ArrayList<>();
        for (int i = 0; i < personBeans.size(); i++) {
            int index = personBeans.get(i).getName().indexOf(name);
            // 存在匹配的数据
            if (index != -1) {
                mSearchList.add(personBeans.get(i));
            }
        }
        return mSearchList;
    }
}
