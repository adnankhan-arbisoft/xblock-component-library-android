package com.mckinsey.academy.xblocks.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.mckinsey.academy.xblocks.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseRecyclerAdapter<M, VH extends BaseRecyclerAdapter.BaseViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<M> mData;
    protected Context mContext;
    protected LayoutInflater mInflater;
    @Nullable
    private OnItemClickListener<M> mOnItemClickListener;
    @Nullable
    private OnItemLongClickListener<M> mOnItemLongClickListener;

    public BaseRecyclerAdapter(Context context) {
        this(context, null);
    }

    public BaseRecyclerAdapter(Context context, @Nullable List<M> data) {
        mContext = context;
        mData = data == null ? new ArrayList<M>() : data;
        mInflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<M> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener<M>
                                                   onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @NonNull
    public final List<M> getData() {
        return mData;
    }

    public M getItem(int position) {
        return position >= 0 && position < mData.size() ? mData.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return getActualSize();
    }

    public int getActualSize() {
        return mData.size();
    }

    public void swap(@NonNull List<M> data) {
        mData = data;
    }

    public void setData(@NonNull List<M> data) {
        clear();
        addAll(data);
    }

    public void clear() {
        mData.clear();
    }

    public void addAll(@NonNull List<M> data) {
        mData.addAll(data);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                Object tag = view.getTag(R.id.position);
                if (tag != null) {
                    int position = (int) tag;
                    mOnItemClickListener.onItemClick(BaseRecyclerAdapter.this,
                            position, getItem(position));
                }
            }
        }
    };

    private View.OnLongClickListener onItemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (mOnItemLongClickListener != null) {
                Object tag = view.getTag(R.id.position);
                if (tag != null) {
                    int position = (int) tag;
                    return mOnItemLongClickListener.onItemLongClick(BaseRecyclerAdapter.this,
                            position, getItem(position));
                }
            }
            return false;
        }
    };

    public interface OnItemClickListener<M> {
        void onItemClick(BaseRecyclerAdapter<M, ?> adapter, int position, M item);
    }

    public interface OnItemLongClickListener<M> {
        boolean onItemLongClick(BaseRecyclerAdapter<M, ?> adapter, int position, M item);
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(BaseRecyclerAdapter adapter, View root) {
            super(root);
            if (adapter.mOnItemClickListener != null) {
                itemView.setOnClickListener(adapter.onItemClickListener);
            }
            if (adapter.mOnItemLongClickListener != null) {
                itemView.setOnLongClickListener(adapter.onItemLongClickListener);
            }
        }
    }
}