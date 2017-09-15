package com.mckinsey.academy.xblocks.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mckinsey.academy.xblocks.R;
import com.mckinsey.academy.xblocks.utils.XBlockUtils;

/**
 * View for the Long-Answer (Free Text) user input expanded form.
 */
public class LongAnswerUserInputExpandedFragment extends BottomSheetDialogFragment implements DialogInterface.OnShowListener {

    private static final String TAG = LongAnswerUserInputExpandedFragment.class.getSimpleName();
    public static final String ARGS_KEY_USER_INPUT = "user_input";

    private View mContentView;
    private EditText mUserInputEditText = null;
    private Button mDoneButton = null;

    public static LongAnswerUserInputExpandedFragment getInstance(String userInput) {
        LongAnswerUserInputExpandedFragment dialogFragment = new LongAnswerUserInputExpandedFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_USER_INPUT, userInput);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xblock_long_answer_user_input_expanded, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_DarkActionBar);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentView = view;

        // Find the edit field and populate it with the already added user answer
        mUserInputEditText = mContentView.findViewById(R.id.view_user_answer);
        Bundle args = getArguments();
        if (args != null) {
            mUserInputEditText.setText(args.getString(ARGS_KEY_USER_INPUT, ""));
            mUserInputEditText.setSelection(mUserInputEditText.getText().length());// setting cursor to end of text
        }

        View.OnClickListener onDoneActionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passBackUserAnswer(true);
            }
        };
        mDoneButton = mContentView.findViewById(R.id.btn_done);
        mDoneButton.setEnabled(mUserInputEditText.getText().length() > 0);
        mDoneButton.setOnClickListener(onDoneActionClickListener);
        mContentView.findViewById(R.id.btn_collapse).setOnClickListener(onDoneActionClickListener);

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
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        View parent = (View) mContentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        behavior.setSkipCollapsed(true);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        passBackUserAnswer(false);
        super.onDismiss(dialog);
    }

    /**
     * Returns User Answer to the calling fragment which is {@code {@link LongAnswerXBlockFragment}}
     * in this case.
     *
     * @param hasToDismissSelf - true explicitly dismiss the fragment
     */
    private void passBackUserAnswer(boolean hasToDismissSelf) {
        XBlockUtils.hideSoftInput(getContext(), mUserInputEditText);
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