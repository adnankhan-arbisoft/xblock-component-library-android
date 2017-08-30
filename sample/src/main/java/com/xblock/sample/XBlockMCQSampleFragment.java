package com.xblock.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mckinsey.academy.xblocks.XBlock;
import com.mckinsey.academy.xblocks.callbacks.MCQResponseCallback;
import com.mckinsey.academy.xblocks.callbacks.XBlockComponentFragment;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfoBuilder;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;
import com.mckinsey.academy.xblocks.model.MCQFeedback;
import com.mckinsey.academy.xblocks.model.MCQOption;
import com.mckinsey.academy.xblocks.view.MCQXBlockFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Sample Fragment in which {@link com.mckinsey.academy.xblocks.view.MCQXBlockFragment} is
 * injected.
 */

public class XBlockMCQSampleFragment extends Fragment {
    private static final String ARG_QUESTION = "questions";
    private static final String ARG_OPTIONS = "options";
    private static final String ARG_MULTI_SELECT_ENABLE = "isMultiSelectEnable";
    private MCQResponseCallback mcqResponseCallback;
    private static final String TITLE = "MCQ XBlock Example";

    private String question;
    private List<MCQOption> options;
    private boolean isMultiSelectEnable;


    public XBlockMCQSampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question Parameter 1.
     * @param options  Parameter 2.
     * @return A new instance of fragment XBlockSampleFragment.
     */
    public static XBlockMCQSampleFragment newInstance(String question, List<MCQOption> options,
                                                      boolean isMultiSelectEnable) {
        XBlockMCQSampleFragment fragment = new XBlockMCQSampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, question);
        args.putSerializable(ARG_OPTIONS, (Serializable) options);
        args.putSerializable(ARG_MULTI_SELECT_ENABLE, isMultiSelectEnable);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            options = (List<MCQOption>) getArguments().getSerializable(ARG_OPTIONS);
            isMultiSelectEnable = (boolean) getArguments().getSerializable(ARG_MULTI_SELECT_ENABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xblock_mq_sample, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //XBlock integration code is here
        XBlockInfo xBlockInfo = XBlockInfoBuilder.buildMCQXBlockInfo()
                .setTitle(TITLE)
                .setQuestion(question)
                .setOptions(options)
                .setMultiSelectEnable(isMultiSelectEnable)
                .build();

        final XBlock xBlock = XBlock.with(getChildFragmentManager(),
                R.id.xblock_container, xBlockInfo);
        xBlock.injectXBlock();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                setCallBackReference();
                Fragment frag = getChildFragmentManager().findFragmentById(R.id.xblock_container);
                if (frag != null && frag instanceof MCQXBlockFragment) {
                    XBlockUserAnswer<List<Integer>> mcqAnswers = ((XBlockComponentFragment) frag)
                            .getUserAnswer();
                    HashMap<String, MCQFeedback> mcqResultHashMap = new HashMap<>();
                    List<Integer> selectedOption = mcqAnswers.getUserAnswer();
                    for (Integer position : selectedOption) {
                        mcqResultHashMap.put(options.get(position).getValue(),
                                new MCQFeedback(position % 2 == 0, String.format("Reason %s", position)));
                    }
                    mcqResponseCallback.onFeedbackReceived("An “!” shows that you were not right, either incorrectly selecting the item or incorrectly excluding it.",
                            mcqResultHashMap);
                }

            }
        });
    }

    private void setCallBackReference() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.xblock_container);
        if (fragment instanceof MCQResponseCallback) {
            mcqResponseCallback = (MCQResponseCallback) fragment;
        }
    }
}
