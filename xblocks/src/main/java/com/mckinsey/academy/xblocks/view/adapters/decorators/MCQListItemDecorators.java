package com.mckinsey.academy.xblocks.view.adapters.decorators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mckinsey.academy.xblocks.R;

public class MCQListItemDecorators {

    private Context mContext;
    private RecyclerView.ItemDecoration mLineDivider;
    private RecyclerView.ItemDecoration mSpace;
    private RecyclerView.ItemDecoration mFeedbackDecorator;

    public MCQListItemDecorators(Context context) {
        mContext = context;
    }

    public RecyclerView.ItemDecoration getLineDivider() {
        if (mLineDivider == null) {
            mLineDivider = new LineDivider();
        }
        return mLineDivider;
    }

    public RecyclerView.ItemDecoration getSpace() {
        if (mSpace == null) {
            mSpace = new Space();
        }
        return mSpace;
    }

    public RecyclerView.ItemDecoration getFeedbackDecorator() {
        if (mFeedbackDecorator == null) {
            mFeedbackDecorator = new FeedbackDecorator();
        }
        return mFeedbackDecorator;
    }

    private class LineDivider extends RecyclerView.ItemDecoration {

        private final Drawable divider;

        private LineDivider() {
            divider = ContextCompat.getDrawable(mContext, R.drawable.mcq_list_line_divider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = divider.getIntrinsicHeight();
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                left += params.leftMargin;
                right -= params.rightMargin;
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    private class Space extends RecyclerView.ItemDecoration {
        private final int marginVertical;

        private Space() {
            marginVertical = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.mcq_item_list_feedback_margin_top);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == RecyclerView.NO_POSITION) {
                return;
            }
            final int itemCount = state.getItemCount();
            /** first position */
            if (itemPosition == 0) {
                outRect.top = marginVertical;
            }
            /** last position */
            else if (itemCount > 0 && itemPosition == itemCount - 1) {
                outRect.bottom = marginVertical;
            }
        }
    }

    private class FeedbackDecorator extends RecyclerView.ItemDecoration {
        private final int marginBottom;

        private FeedbackDecorator() {
            marginBottom = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.mcq_item_list_feedback_margin_bottom);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = marginBottom;
            }
        }
    }
}