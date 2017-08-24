package com.mckinsey.academy.xblocks.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.MCQXBlockCallback;
import com.mckinsey.academy.xblocks.info.MCQXBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;
import com.mckinsey.academy.xblocks.listener.RecyclerViewItemSelectListener;
import com.mckinsey.academy.xblocks.model.MCQResult;
import com.mckinsey.academy.xblocks.model.OptionState;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;
import com.mckinsey.academy.xblocks.view.adapters.MCQOptionsAdapter;
import com.mckinsey.academy.xblocks.view.adapters.MCQResultAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import static com.mckinsey.academy.xblocks.common.Constants.EXTRA_XBLOCK_INFO;

/**
 * MCQ and MRQ XBlock Fragment. Activity/Fragment in which this functionality is required
 * needs to add that this fragment as there child fragment
 */

public class MCQXBlockFragment extends LifecycleOwnerFragment
        implements RecyclerViewItemSelectListener, MCQXBlockCallback {

    private static final String TAG = VideoXBlockFragment.class.getSimpleName();
    private TextView mQuestionTitle;
    private RecyclerView mOptionsRecyclerView;
    private MCQOptionsAdapter mMcqOptionsAdapter;
    private MCQXBlockInfo mXBlockInfo;

    public static MCQXBlockFragment newInstance(XBlockInfo xBlockInfo) {
        MCQXBlockFragment fragment = new MCQXBlockFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_XBLOCK_INFO, xBlockInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mXBlockInfo = (MCQXBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);
        mQuestionTitle.setText(XBlockUtils.getTextFromHTML(mXBlockInfo.getQuestion()));
        mMcqOptionsAdapter = new MCQOptionsAdapter();
        mMcqOptionsAdapter.setItemSelectListener(this);
        mOptionsRecyclerView.setAdapter(mMcqOptionsAdapter);
        mMcqOptionsAdapter.setMultiSelectEnable(mXBlockInfo.isMultiSelectEnable());
        mMcqOptionsAdapter.setOptions(mXBlockInfo.getOptions());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xblock_mcq, container, false);
        mQuestionTitle = (TextView) view.findViewById(R.id.question_title);
        mOptionsRecyclerView = (RecyclerView) view.findViewById(R.id.options_list);
        mOptionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void setCallback(Callback callback) {
        // Nothing is required to implement in this callback
    }

    @Override
    public void update(Observable o, Object arg) {
        // No implementation required here
    }

    @Override
    public void onItemSelect(int position) {
        if (mXBlockInfo.isMultiSelectEnable()) {
            mXBlockInfo.getOptions().get(position).toggleState();
        } else {
            mXBlockInfo.resetOptionState();
            mXBlockInfo.getOptions().get(position).setOptionState(OptionState.SELECTED);
        }
        mMcqOptionsAdapter.setOptions(mXBlockInfo.getOptions());
    }

    /***
     * Receive results about the selected options i.e either the option is correct or incorrect
     * from the app using the XBlock
     * @param result SparseBooleanArray store result against the options position
     */
    @Override
    public void onReceiveResult(HashMap<Integer, MCQResult> result) {
        mOptionsRecyclerView.setPadding(0, 0, 0, 0);
        MCQResultAdapter mcqResultAdapter = new MCQResultAdapter();
        mcqResultAdapter.setOptions(mXBlockInfo.getOptions());
        mcqResultAdapter.setResult(result);
        mOptionsRecyclerView.setAdapter(mcqResultAdapter);
    }

    /***
     * Call this function if the user wants to retry a question, it resets the options state to
     * UnSelected and updates the adapter
     */
    @Override
    public void onRetry() {
        int padding = (int) getResources().getDimension(R.dimen.option_list_padding);
        mOptionsRecyclerView.setPadding(padding, padding, padding, padding);
        mMcqOptionsAdapter.setOptions(mXBlockInfo.getOptions());
        mXBlockInfo.resetOptionState();
        mOptionsRecyclerView.setAdapter(mMcqOptionsAdapter);
    }

    @Override
    public void onInit() {
        // Nothing is required to do in this function
    }

    @Override
    public void onComplete() {
        // Nothing is required to do in this function
    }

    /***
     * Called by the parent activity or fragment in order to get the selected options
     * @return List of selected option's position wrapped inside {@link XBlockUserAnswer}
     */
    @Override
    public XBlockUserAnswer getUserAnswer() {
        XBlockUserAnswer<List<Integer>> xBlockUserAnswer = new XBlockUserAnswer<>();
        xBlockUserAnswer.setUserAnswer(mXBlockInfo.getSelectedOptions());
        return xBlockUserAnswer;
    }
}
