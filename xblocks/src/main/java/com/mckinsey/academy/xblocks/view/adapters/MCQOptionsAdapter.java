package com.mckinsey.academy.xblocks.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

/**
 * MCQ and MRQ list of options will be displayed to the user using this adapter
 */
public class MCQOptionsAdapter extends BaseRecyclerAdapter<MCQOption, MCQOptionsAdapter.ViewHolder> {

    private boolean isMultiSelectEnable = false;

    public MCQOptionsAdapter(Context context) {
        super(context);
    }

    public void setMultiSelectEnable(boolean isMultiSelectEnable) {
        this.isMultiSelectEnable = isMultiSelectEnable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this, mInflater.inflate(R.layout.item_mcq_option, parent, false),
                isMultiSelectEnable);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(R.id.position, position);
        MCQOption mcqOption = arrData.get(position);
        holder.optionText.setText(XBlockUtils.getTextFromHTML(mcqOption.getContent()));
        holder.optionCheckbox.setChecked(mcqOption.isSelected());
    }

    private static CompoundButton.OnCheckedChangeListener onOptionCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ((View) compoundButton.getParent()).performClick();
                }
            };

    static class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

        final CardView cardView;
        final ViewGroup optionView;
        final CheckBox optionCheckbox;
        final TextView optionText;

        ViewHolder(BaseRecyclerAdapter adapter, View root, boolean isMultiSelectEnable) {
            super(adapter, root);
            cardView = itemView.findViewById(R.id.card_view);
            optionView = itemView.findViewById(R.id.option_view);
            optionCheckbox = itemView.findViewById(R.id.option_checkbox);
            optionText = itemView.findViewById(R.id.option_text);
            optionCheckbox.setButtonDrawable(isMultiSelectEnable ?
                    R.drawable.selector_mrq_options :
                    R.drawable.selector_mcq_options);
            optionCheckbox.setOnCheckedChangeListener(onOptionCheckedChangeListener);
        }

    }
}