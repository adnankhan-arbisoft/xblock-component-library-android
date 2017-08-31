package com.xblock.sample;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mckinsey.academy.xblocks.XBlock;
import com.mckinsey.academy.xblocks.callbacks.LongAnswerXBlockCallback;
import com.mckinsey.academy.xblocks.callbacks.XBlockComponentFragment;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockInfoBuilder;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;

/**
 * Sample fragment for Long Answer XBlock
 */
public class XBlockLongAnswerSampleFragment extends Fragment implements LongAnswerXBlockCallback {
    private static final String ARG_TITLE = "param_title";
    private static final String ARG_DESC = "param_description";

    private String mParamTitle;
    private String mParamDesc;
    private Button btnSubmit = null;

    public XBlockLongAnswerSampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment XBlockLongAnswerSampleFragment.
     */
    public static XBlockLongAnswerSampleFragment newInstance(String title, String description) {
        XBlockLongAnswerSampleFragment fragment = new XBlockLongAnswerSampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamTitle = getArguments().getString(ARG_TITLE);
            mParamDesc = getArguments().getString(ARG_DESC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xblock_long_answer_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Click listener to demonstrate pulling data from XBlock Long Answer component
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find the fragment in xblock_container frame
                Fragment frag = getChildFragmentManager().findFragmentById(R.id.xblock_container);
                if (frag != null && frag instanceof XBlockComponentFragment) {
                    XBlockUserAnswer<String> freeTextAnswer = ((XBlockComponentFragment) frag).getUserAnswer();
                    Toast.makeText(getActivity(), freeTextAnswer.getUserAnswer(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // XBlock integration
        XBlockInfo xBlockInfo = XBlockInfoBuilder.buildLongAnswerXBlockInfo()
                .setTitle(mParamTitle)
                .setDescription(mParamDesc)
                .build();

        final XBlock xBlock = XBlock.with(getChildFragmentManager(), R.id.xblock_container, xBlockInfo, this/*call back*/);
        xBlock.injectXBlock();
    }

    @Override
    public void onAnswerFieldUpdate(boolean isEmpty) {
        if (btnSubmit != null) {
            if (isEmpty && btnSubmit.isEnabled()) {
                btnSubmit.setEnabled(false);
            } else if(!isEmpty && !btnSubmit.isEnabled()) {
                btnSubmit.setEnabled(true);
            }
        }
    }

    @Override
    public void onAnswerUpdate(String userAnswer) {
        // TODO handle userAnswer if needed
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
