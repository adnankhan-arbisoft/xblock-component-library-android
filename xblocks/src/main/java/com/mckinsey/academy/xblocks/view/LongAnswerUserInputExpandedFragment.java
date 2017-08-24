package com.mckinsey.academy.xblocks.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mckinsey.academy.xblocks.R;

/**
 * View for the Long-Answer (Free Text) user input expanded form.
 */
public class LongAnswerUserInputExpandedFragment extends BottomSheetDialogFragment {

    private static final String TAG = LongAnswerUserInputExpandedFragment.class.getSimpleName();
    public static final String ARGS_KEY_USER_INPUT = "user_input";

    private EditText mUserInputEditText = null;
    private View mCollapseView = null;
    private Button mDoneButton = null;

    private View.OnClickListener mDoneActionClickListener = null;

    public static LongAnswerUserInputExpandedFragment getInstance(String userInput) {
        LongAnswerUserInputExpandedFragment dialogFragment = new LongAnswerUserInputExpandedFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_USER_INPUT, userInput);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        // super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.fragment_xblock_long_answer_user_input_expanded, null);
        // Find the edit field and populate it with the already added user answer
        mUserInputEditText = (EditText) inflatedView.findViewById(R.id.view_user_answer);
        Bundle args = getArguments();
        if(args != null) {
            mUserInputEditText.setText(args.getString(ARGS_KEY_USER_INPUT, ""));
            mUserInputEditText.setSelection(mUserInputEditText.getText().length());// setting cursor to end of text
        }

        mDoneButton = (Button) inflatedView.findViewById(R.id.btn_done);
        mCollapseView = inflatedView.findViewById(R.id.btn_collapse);

        mDoneActionClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                passBackUserAnswer(true);
            }
        };

        mDoneButton.setEnabled(mUserInputEditText.getText().length() > 0);

        mDoneButton.setOnClickListener(mDoneActionClickListener);
        mCollapseView.setOnClickListener(mDoneActionClickListener);

        mUserInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDoneButton != null) {
                    mDoneButton.setEnabled(s.length() > 0);
                }
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.setContentView(inflatedView);

        // Adjust the height to make the bottom sheet fragment appear on full screen height
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int topMargin = (int) getActivity().getResources().getDimension(R.dimen.long_answer_user_input_expanded_fragment_margin_top);
        int screenHeight = displayMetrics.heightPixels - topMargin;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        // Bottom Sheet Behavior Callback
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState) {
                        case BottomSheetBehavior.STATE_COLLAPSED:
                            passBackUserAnswer(false);
                            break;
                        case BottomSheetBehavior.STATE_HIDDEN:
                            passBackUserAnswer(true);
                        default:
                            break;
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }

        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.setMargins(0, 0, 0, (int) getActivity().getResources().getDimension(R.dimen.long_answer_user_input_expanded_fragment_layout_margin));
        parent.setLayoutParams(params);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Returns User Answer to the calling fragment which is {@code {@link LongAnswerXBlockFragment}}
     * in this case.
     * @param hasToDismissSelf - true explicitly dismiss the fragment
     */
    private void passBackUserAnswer(boolean hasToDismissSelf) {
        Fragment targetFragment = getTargetFragment();
        if (mUserInputEditText != null && targetFragment != null) {
            Intent toReturn = new Intent();
            toReturn.setAction(null);
            toReturn.putExtra(ARGS_KEY_USER_INPUT, mUserInputEditText.getText().toString());
            targetFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, toReturn);
        }

        if (hasToDismissSelf) {
            this.dismiss();
        }
    }

}
