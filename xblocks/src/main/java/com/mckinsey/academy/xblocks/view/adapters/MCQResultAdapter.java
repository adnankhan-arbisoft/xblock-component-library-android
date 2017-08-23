package com.mckinsey.academy.xblocks.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.model.MCQResult;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * MCQ and MQR selected options result will be displayed using this adapter
 */

public class MCQResultAdapter extends RecyclerView.Adapter<MCQResultAdapter.MCQResultViewHolder> {

    private HashMap<Integer, MCQResult> mResult;
    private List<MCQOption> arrOptions;
    private Context mContext;

    public void setResult(HashMap<Integer, MCQResult> result) {
        this.mResult = result;
    }

    public void setOptions(List<MCQOption> options) {
        this.arrOptions = options;
    }

    @Override
    public MCQResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MCQResultViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mcq_option_result, parent, false));
    }

    @Override
    public void onBindViewHolder(MCQResultViewHolder holder, int position) {
        int optionIndex = new ArrayList<>(mResult.keySet()).get(position);
        MCQResult mcqResult = mResult.get(optionIndex);
        MCQOption option = arrOptions.get(optionIndex);
        holder.optionState.setImageResource(mcqResult.isCorrect() ?
                R.drawable.ic_status_correct :
                R.drawable.ic_status_incorrect);
        holder.optionText.setText(option.getContent());
        holder.resultReason.setText(XBlockUtils.getTextFromHTML(mcqResult.getMessage()));
        holder.resultReason.setTextColor(ContextCompat.getColor(mContext,
                mcqResult.isCorrect() ? R.color.color_right_answer_reason
                        : R.color.color_wrong_answer_reason));
        holder.reasonContainer.setBackgroundColor(ContextCompat.getColor(mContext,
                mcqResult.isCorrect() ? R.color.color_right_answer :
                        R.color.color_wrong_answer));
        holder.reasonIcon.setImageResource(mcqResult.isCorrect() ?
                R.drawable.ic_results_correct_green :
                R.drawable.ic_warning_red_18dp);
    }

    @Override
    public int getItemCount() {
        return (mResult != null ? mResult.size() : 0);
    }

    class MCQResultViewHolder extends RecyclerView.ViewHolder {
        private final ImageView optionState;
        private final ImageView reasonIcon;
        private final LinearLayout reasonContainer;
        private final TextView optionText;
        private final TextView resultReason;

        MCQResultViewHolder(View itemView) {
            super(itemView);
            optionState = (ImageView) itemView.findViewById(R.id.option_state);
            optionText = (TextView) itemView.findViewById(R.id.option_text);
            resultReason = (TextView) itemView.findViewById(R.id.reason);
            reasonIcon = (ImageView) itemView.findViewById(R.id.reason_icon);
            reasonContainer = (LinearLayout) itemView.findViewById(R.id.reason_container);
        }
    }
}
