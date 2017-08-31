package com.mckinsey.academy.xblocks.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.MCQResponseCallback;
import com.mckinsey.academy.xblocks.callbacks.MCQXBlockCallback;
import com.mckinsey.academy.xblocks.info.MCQXBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;
import com.mckinsey.academy.xblocks.model.MCQFeedback;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;
import com.mckinsey.academy.xblocks.view.adapters.BaseRecyclerAdapter;
import com.mckinsey.academy.xblocks.view.adapters.MCQOptionsAdapter;
import com.mckinsey.academy.xblocks.view.adapters.MCQFeedbackAdapter;
import com.mckinsey.academy.xblocks.view.adapters.decorators.MCQListItemDecorators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mckinsey.academy.xblocks.common.Constants.EXTRA_XBLOCK_INFO;

/**
 * MCQ and MRQ XBlock Fragment. Activity/Fragment in which this functionality is required
 * needs to add that this fragment as there child fragment
 */
public class MCQXBlockFragment extends LifecycleOwnerFragment<MCQXBlockCallback, List<MCQOption>>
        implements BaseRecyclerAdapter.OnItemClickListener<MCQOption>, MCQResponseCallback {

    private MCQXBlockInfo xBlockInfo;
    private MCQOptionsAdapter mcqOptionsAdapter;
    private ArrayList<Integer> arrSelectedOptions;
    private MCQListItemDecorators mcqListItemDecorators;

    private TextView questionView;
    private TextView feedbackMessage;
    private View feedbackDivider;
    private RecyclerView optionsView;

    public static MCQXBlockFragment newInstance(XBlockInfo xBlockInfo) {
        MCQXBlockFragment fragment = new MCQXBlockFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_XBLOCK_INFO, xBlockInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public MCQXBlockCallback getNullCallback() {
        return MCQXBlockCallback.NULL_CALLBACK;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            xBlockInfo = (MCQXBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);
        }
        if (xBlockInfo == null) {
            throw new RuntimeException("XBlockInfo cannot be null");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrSelectedOptions = new ArrayList<>();
        mcqListItemDecorators = new MCQListItemDecorators(getContext());

        questionView.setText(XBlockUtils.getTextFromHTML(xBlockInfo.getQuestion()));
        mcqOptionsAdapter = new MCQOptionsAdapter(getContext());
        mcqOptionsAdapter.setData(getAllOptions());
        mcqOptionsAdapter.setOnItemClickListener(this);
        mcqOptionsAdapter.setMultiSelectEnable(xBlockInfo.isMultiSelectEnable());
        optionsView.setAdapter(mcqOptionsAdapter);
        setupListItemQuizDecorators();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xblock_mcq, container, false);
        questionView = view.findViewById(R.id.question_view);
        feedbackMessage = view.findViewById(R.id.feedback_message);
        feedbackDivider = view.findViewById(R.id.feedback_divider);
        optionsView = view.findViewById(R.id.options_view);
        return view;
    }

    @Override
    public void onItemClick(BaseRecyclerAdapter adapter, int position, MCQOption item) {
        if (xBlockInfo.isMultiSelectEnable()) {
            item.toggleState();
        } else {
            if (item.isSelected()) {
                return;
            }
            unselectAllSelectedOptions();
            item.setSelected(true);
        }
        if (item.isSelected()) {
            arrSelectedOptions.add(position);
        } else {
            arrSelectedOptions.remove(Integer.valueOf(position));
        }
        mcqOptionsAdapter.notifyItemChanged(position);
        mCallback.onOptionSelectionChanged(position, item);
    }

    private void unselectAllSelectedOptions() {
        List<MCQOption> arrOptions = getAllOptions();
        for (Integer pos : arrSelectedOptions) {
            arrOptions.get(pos).setSelected(false);
            mcqOptionsAdapter.notifyItemChanged(pos);
        }
        arrSelectedOptions.clear();
    }

    public void unselectAllOptions() {
        List<MCQOption> arrOptions = getAllOptions();
        for (MCQOption option : arrOptions) {
            option.setSelected(false);
        }
        arrSelectedOptions.clear();
    }

    @Override
    public List<MCQOption> getAllOptions() {
        return xBlockInfo.getOptions();
    }

    @Override
    public void onFeedbackReceived(String message, HashMap<String, MCQFeedback> feedback) {
        feedbackMessage.setVisibility(View.VISIBLE);
        feedbackDivider.setVisibility(View.VISIBLE);
        feedbackMessage.setText(message);
        MCQFeedbackAdapter mcqFeedbackAdapter = new MCQFeedbackAdapter(getContext());
        mcqFeedbackAdapter.setMultiSelectEnable(xBlockInfo.isMultiSelectEnable());
        mcqFeedbackAdapter.setData(xBlockInfo.isMultiSelectEnable() ? getAllOptions() : getSelectedOptions());
        mcqFeedbackAdapter.setFeedback(feedback);
        setupListItemFeedbackDecorators();
        optionsView.setAdapter(mcqFeedbackAdapter);
    }

    @Override
    public void onRetakeQuiz() {
        feedbackMessage.setVisibility(View.GONE);
        feedbackDivider.setVisibility(View.GONE);
        unselectAllOptions();
        setupListItemQuizDecorators();
        mcqOptionsAdapter.notifyDataSetChanged();
        optionsView.setAdapter(mcqOptionsAdapter);
    }

    /***
     * Called by the parent activity or fragment in order to get the selected options
     * @return List of selected option's position wrapped inside {@link XBlockUserAnswer}
     */
    @Override
    public XBlockUserAnswer<List<MCQOption>> getUserAnswer() {
        XBlockUserAnswer<List<MCQOption>> xBlockUserAnswer = new XBlockUserAnswer<>();
        xBlockUserAnswer.setUserAnswer(getSelectedOptions());
        return xBlockUserAnswer;
    }

    /***
     * Filter out the {@link MCQOption} who's status is  is SELECTED
     * @return return the list of the Selected Options
     */
    public List<MCQOption> getSelectedOptions() {
        List<MCQOption> arrSelected = new ArrayList<>();
        List<MCQOption> arrOptions = getAllOptions();
        for (MCQOption option : arrOptions) {
            if (option.isSelected()) {
                arrSelected.add(option);
            }
        }
        return arrSelected;
    }

    public void setupListItemQuizDecorators() {
        optionsView.removeItemDecoration(mcqListItemDecorators.getFeedbackDecorator());
        optionsView.addItemDecoration(mcqListItemDecorators.getLineDivider());
        optionsView.addItemDecoration(mcqListItemDecorators.getSpace());
    }

    public void setupListItemFeedbackDecorators() {
        optionsView.removeItemDecoration(mcqListItemDecorators.getLineDivider());
        optionsView.removeItemDecoration(mcqListItemDecorators.getSpace());
        optionsView.addItemDecoration(mcqListItemDecorators.getFeedbackDecorator());
    }
}