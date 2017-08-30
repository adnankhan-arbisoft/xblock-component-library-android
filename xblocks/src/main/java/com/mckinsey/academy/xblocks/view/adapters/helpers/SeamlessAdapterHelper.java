package com.mckinsey.academy.xblocks.view.adapters.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mckinsey.academy.xblocks.view.adapters.BaseRecyclerAdapter;

import java.util.List;

@SuppressWarnings("unused")
public class SeamlessAdapterHelper<M, A extends BaseRecyclerAdapter<M,
        ? extends BaseRecyclerAdapter.BaseViewHolder>> {

    private A mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public SeamlessAdapterHelper(@NonNull A mAdapter) {
        this.mAdapter = mAdapter;
    }

    public A getAdapter() {
        return mAdapter;
    }

    public void setAdapter(A adapter) {
        mAdapter = adapter;
    }

    public void initAdapter(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        if (manager == null) {
            manager = new LinearLayoutManager(mAdapter.getContext());
        }
        recyclerView.setLayoutManager(manager);
        initAdapter(recyclerView);
    }

    public void initAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = recyclerView.getLayoutManager();
        recyclerView.setAdapter(mAdapter);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setOnItemClickListener(@Nullable BaseRecyclerAdapter.OnItemClickListener<M> onItemClickListener) {
        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(@Nullable BaseRecyclerAdapter.OnItemLongClickListener<M> onItemLongClickListener) {
        mAdapter.setOnItemLongClickListener(onItemLongClickListener);
    }

    public void swap(@NonNull List<? extends M> data) {
        int oldSize = mAdapter.getActualSize();
        int newSize = data.size();
        //noinspection unchecked
        mAdapter.swap((List<M>) data);
        if (oldSize > newSize) {
            mAdapter.notifyItemRangeRemoved(newSize, oldSize - newSize);
        } else if (newSize > oldSize) {
            mAdapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
        }
        if (oldSize > 0) {
            mAdapter.notifyItemRangeChanged(0, oldSize);
        }
    }

    public void setData(@Nullable List<? extends M> data) {
        if (data == null) {
            clear();
            return;
        }
        int oldSize = mAdapter.getActualSize();
        int newSize = data.size();
        //noinspection unchecked
        mAdapter.setData((List<M>) data);
        if (oldSize > newSize) {
            mAdapter.notifyItemRangeRemoved(newSize, oldSize - newSize);
        } else if (newSize > oldSize) {
            mAdapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
        }
        if (oldSize > 0) {
            mAdapter.notifyItemRangeChanged(0, oldSize);
        }
    }

    public void clear() {
        int size = mAdapter.getActualSize();
        if (size > 0) {
            mAdapter.clear();
            mAdapter.notifyItemRangeRemoved(0, size);
        }
    }

    public void addAll(@Nullable List<? extends M> data) {
        if (data != null && !data.isEmpty()) {
            int size = mAdapter.getActualSize();
            //noinspection unchecked
            mAdapter.addAll((List<M>) data);
            mAdapter.notifyItemRangeInserted(size, data.size());
        }
    }
}