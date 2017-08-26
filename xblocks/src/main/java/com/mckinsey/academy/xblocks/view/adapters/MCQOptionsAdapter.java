package com.mckinsey.academy.xblocks.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.listener.RecyclerViewItemSelectListener;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

import java.util.List;

import static com.mckinsey.academy.xblocks.model.MCQOption.SELECTED;
import static com.mckinsey.academy.xblocks.model.MCQOption.UNSELECTED;

/**
 * MCQ and MRQ list of options will be displayed to the user using this adapter
 */

public class MCQOptionsAdapter extends
        RecyclerView.Adapter<MCQOptionsAdapter.MCQOptionsViewHolder> {

    private List<MCQOption> arrOptions;
    private RecyclerViewItemSelectListener mItemSelectListener;
    private boolean isMultiSelectEnable = false;

    public void setItemSelectListener(RecyclerViewItemSelectListener itemSelectListener) {
        this.mItemSelectListener = itemSelectListener;
    }

    public void setOptions(List<MCQOption> options) {
        this.arrOptions = options;
        notifyDataSetChanged();
    }

    public void setMultiSelectEnable(boolean isMultiSelectEnable) {
        this.isMultiSelectEnable = isMultiSelectEnable;
    }

    @Override
    public MCQOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MCQOptionsViewHolder holder =
                new MCQOptionsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mcq_option, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemSelectListener.onItemSelect(holder.getAdapterPosition());
            }
        });
        holder.optionStateCheckbox.setButtonDrawable(isMultiSelectEnable ?
                R.drawable.selector_mrq_options :
                R.drawable.selector_mcq_options);
        return holder;
    }

    @Override
    public void onBindViewHolder(MCQOptionsViewHolder holder, int position) {
        MCQOption mcqOption = arrOptions.get(position);
        holder.optionText.setText(XBlockUtils.getTextFromHTML(mcqOption.getContent()));
        holder.optionStateCheckbox.setChecked(mcqOption.getOptionState() == SELECTED);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (arrOptions != null ? arrOptions.size() : 0);
    }

    class MCQOptionsViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox optionStateCheckbox;
        private final TextView optionText;

        MCQOptionsViewHolder(View view) {
            super(view);
            optionStateCheckbox = (CheckBox) view.findViewById(R.id.option_state);
            optionText = (TextView) view.findViewById(R.id.option_text);
        }
    }
}
