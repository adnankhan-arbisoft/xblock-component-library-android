package com.mckinsey.academy.xblocks.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.callbacks.LongAnswerXBlockCallback;
import com.mckinsey.academy.xblocks.info.XBlockInfo;
import com.mckinsey.academy.xblocks.info.XBlockUserAnswer;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

import static com.mckinsey.academy.xblocks.view.LongAnswerUserInputExpandedFragment.ARGS_KEY_USER_INPUT;

/**
 * UI Component Fragment for XBlock Long-Answer Problem Builder
 */

public class LongAnswerXBlockFragment extends LifecycleOwnerFragment<LongAnswerXBlockCallback, String> {

    private static final String TAG = LongAnswerXBlockFragment.class.getSimpleName();
    private static final String EXTRA_XBLOCK_INFO = "xblock_info";

    public static final int REQ_CODE_LONG_ANSWER_USER_INPUT = 100;

    private EditText mUserAnswerEditText = null;
    private TextView mTitleTextView = null;
    private TextView mDescTextView = null;
    private ImageView mExpandInputFieldImageView = null;
    private View mUserInputContainerView = null;

    private BottomSheetBehavior mBottomSheetBehavior;
    private XBlockInfo mXBlockInfo;

    /**
     * Creates a new instance of the {@code {@link LongAnswerXBlockFragment}}, set the arguments
     * bundle and returns the instance to the caller.
     * @param xBlockInfo {@code {@link XBlockInfo}}
     * @return
     */
    public static LongAnswerXBlockFragment getInstance(XBlockInfo xBlockInfo) {
        LongAnswerXBlockFragment fragment = new LongAnswerXBlockFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_XBLOCK_INFO, xBlockInfo);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public LongAnswerXBlockCallback getNullCallback() {
        return LongAnswerXBlockCallback.NULL_CALLBACK;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xblock_long_answer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = (TextView)view.findViewById(R.id.view_title);
        mDescTextView = (TextView)view.findViewById(R.id.view_description);
        mUserAnswerEditText = (EditText) view.findViewById(R.id.view_user_answer);
        mExpandInputFieldImageView = (ImageView) view.findViewById(R.id.btn_expand);
        mUserInputContainerView = view.findViewById(R.id.user_answer_field_container);

        Bundle args = getArguments();
        if (args != null) {
            mXBlockInfo = (XBlockInfo) args.getSerializable(EXTRA_XBLOCK_INFO);
            if (mXBlockInfo != null) {
                mTitleTextView.setText(mXBlockInfo.getTitle());
                mDescTextView.setText(XBlockUtils.getTextFromHTML(mXBlockInfo.getDetails()));
            }
        }

        // hiding keyboard
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        // Click listener to expand the user input view
        mExpandInputFieldImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LongAnswerUserInputExpandedFragment frag = LongAnswerUserInputExpandedFragment.getInstance(mUserAnswerEditText.getText().toString());
                frag.setTargetFragment(LongAnswerXBlockFragment.this, REQ_CODE_LONG_ANSWER_USER_INPUT);
                frag.show(getFragmentManager(), frag.getTag());
            }
        });

        mUserAnswerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCallback != null) {
                    mCallback.onAnswerFieldUpdate(s.length() == 0);
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_LONG_ANSWER_USER_INPUT && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                String userAnswerFromExpandedState = data.getStringExtra(ARGS_KEY_USER_INPUT);
                if (userAnswerFromExpandedState != null) {
                    // not checking for length, should update parent view when the full screen closes.
                    // this can empty user answer also
                    setPreAddedUserAnswer(userAnswerFromExpandedState);
                }
            }
        }
    }

    private void setPreAddedUserAnswer(String userAnswer) {
        mUserAnswerEditText.setText(userAnswer);
        final int answerLength = mUserAnswerEditText.getText().length();
        mUserAnswerEditText.setSelection(answerLength);

        if (mCallback != null) {
            // update UI
            mCallback.onAnswerFieldUpdate(answerLength == 0);
            // pass user answer to callback consumer in case required
            mCallback.onAnswerUpdate(mUserAnswerEditText.getText().toString());
        }
    }

    @Override
    public XBlockUserAnswer<String> getUserAnswer() {
        XBlockUserAnswer<String> freeTextUserAnser = new XBlockUserAnswer<>();
        if (mUserAnswerEditText != null) {
            freeTextUserAnser.setUserAnswer(mUserAnswerEditText.getText().toString());
        }
        return freeTextUserAnser;
    }
}
