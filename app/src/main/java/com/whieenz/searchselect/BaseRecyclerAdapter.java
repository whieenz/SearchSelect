
package com.whieenz.searchselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by heziwen on 2017-08-19.
 *
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    protected Context context;
    protected List<T> datas;
    protected LayoutInflater inflater;
    protected OnItemClickListener onItemClickListener;
    protected OnItemViewClickListener onItemViewClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<T>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public BaseRecyclerAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, T[] datas) {
        this.context = context;
        this.datas = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Collections.addAll(this.datas, datas);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    /**
     * 更新数据，替换原有数据
     */
    public void replaceItems(List<T> items) {
        datas = items;
        notifyDataSetChanged();
    }

    /**
     * 插入一条数据
     */
    public void addItem(T item) {
        datas.add(0, item);
        notifyItemInserted(0);
    }

    /**
     * 插入一条数据
     */
    public void addItem(T item, int position) {
        position = Math.min(position, datas.size());
        datas.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 在列表尾添加一串数据
     */
    public void addItems(List<T> items) {
        int start = datas.size();
        datas.addAll(items);
        notifyItemRangeChanged(start, items.size());
        notifyDataSetChanged();
    }

    /**
     * 移除一条数据
     */
    public T removeItem(int position) {
        return removeData(position);
    }

    private T removeData(int position) {
        //保证列表有数据，并且最少有一条
        T removeData = null;
        if (datas.size() < 2 && datas.size() != 0) {
            removeData = datas.remove(0);
            notifyDataSetChanged();
        } else if (datas.size() == 0) {//当列表没有数据提示用户，免得造成系统崩溃
        } else {//更新列表
            removeData = datas.remove(position);
            notifyDataSetChanged();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, datas.size());
        }
        return removeData;
    }

    /**
     * 移除一条数据
     */
    public void removeItem(T item) {
        int position = 0;
        ListIterator<T> iterator = datas.listIterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == item) {
                iterator.remove();
                notifyItemRemoved(position);
            }
            position++;
        }
    }

    /**
     * 清除所有数据
     */
    public void removeAllItems() {
        datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取一列数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        return datas.get(position);
    }

    public List<T> getAllData() {
        return datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnItemViewClickListener {
        void onClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick((Integer) v.getTag());
        }
    }
}
