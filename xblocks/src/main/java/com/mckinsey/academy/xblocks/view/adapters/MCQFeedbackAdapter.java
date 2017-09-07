package com.mckinsey.academy.xblocks.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.model.MCQFeedback;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

import java.util.HashMap;

/**
 * MCQ and MQR selected options result will be displayed using this adapter
 */

public class MCQFeedbackAdapter extends BaseRecyclerAdapter<MCQOption, MCQFeedbackAdapter.ViewHolder> {

    private HashMap<String, MCQFeedback> mFeedbackMap;
    private boolean isMultiSelectEnable = false;
    private int mCardRadius;

    public MCQFeedbackAdapter(Context context) {
        super(context);
        mCardRadius = mContext.getResources()
                .getDimensionPixelSize(R.dimen.mcq_item_list_feedback_card_radius);
    }

    public void setFeedback(HashMap<String, MCQFeedback> feedbackMap) {
        mFeedbackMap = feedbackMap;
    }

    public void setMultiSelectEnable(boolean isMultiSelectEnable) {
        this.isMultiSelectEnable = isMultiSelectEnable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(this, mInflater.inflate(R.layout.item_mcq_option_feedback, parent, false),
                isMultiSelectEnable);
        holder.cardView.setRadius(mCardRadius);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MCQOption option = arrData.get(position);
        MCQFeedback feedback = mFeedbackMap.get(option.getValue());
        holder.optionCheckbox.setChecked(option.isSelected());
        holder.optionText.setText(XBlockUtils.getTextFromHTML(option.getContent()));

        holder.feedbackView.setBackgroundColor(ContextCompat.getColor(mContext,
                feedback.isCorrect() ? R.color.mcqFeedbackCorrectAnswerBackground :
                        R.color.mcqFeedbackIncorrectAnswerBackground));
        holder.feedbackIcon.setImageResource(feedback.isCorrect() ?
                R.drawable.ic_mcq_feedback_list_correct_green :
                R.drawable.ic_mcq_feedback_list_error_red);
        holder.feedbackText.setTextColor(ContextCompat.getColor(mContext,
                feedback.isCorrect() ? R.color.mcqFeedbackCorrectAnswerTextColor
                        : R.color.mcqFeedbackIncorrectAnswerTextColor));
        if(!TextUtils.isEmpty(feedback.getMessage())) {
            holder.feedbackText.setText(XBlockUtils.getTextFromHTML(feedback.getMessage()));
            holder.feedbackView.setVisibility(View.VISIBLE);
        } else {
            holder.feedbackView.setVisibility(View.GONE);
        }
    }

    static class ViewHolder extends MCQOptionsAdapter.ViewHolder {

        private final ViewGroup feedbackView;
        private final ImageView feedbackIcon;
        private final TextView feedbackText;

        ViewHolder(BaseRecyclerAdapter adapter, View root, boolean isMultiSelectEnable) {
            super(adapter, root, isMultiSelectEnable);
            optionView.setEnabled(false);
            optionCheckbox.setEnabled(false);
            feedbackView = itemView.findViewById(R.id.feedback_view);
            feedbackIcon = itemView.findViewById(R.id.feedback_icon);
            feedbackText = itemView.findViewById(R.id.feedback_text);
        }
    }
}