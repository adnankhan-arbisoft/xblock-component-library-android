package com.mckinsey.academy.xblocks.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.callbacks.VideoXBlockCallback;
import com.mckinsey.academy.xblocks.callbacks.XBlockComponentFragment;
import com.mckinsey.academy.xblocks.exception.CallbackCastException;

import java.util.Observer;

import static com.mckinsey.academy.xblocks.common.Constants.ERROR_MSG_CALLBACK_NOT_FOUND;
import static com.mckinsey.academy.xblocks.common.Constants.X_BLOCK_TAG;

/**
 * Fragment with {@link LifecycleRegistryOwner} implementation.
 */
public abstract class LifecycleOwnerFragment<C extends Callback, A> extends Fragment
        implements LifecycleRegistryOwner, XBlockComponentFragment<A> {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @NonNull
    protected C mCallback;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallback == null) {
            Fragment parent = getParentFragment();
            if (parent != null) {
                try {
                    //noinspection unchecked
                    mCallback = (C) parent;
                } catch (ClassCastException e) {
                    // Nothing to do here;
                }
            }
            if (mCallback == null) {
                try {
                    //noinspection unchecked
                    mCallback = (C) context;
                } catch (ClassCastException e) {
                    // Nothing to do here;
                }
            }
            if (mCallback == null) {
                mCallback = getNullCallback();
            }
        }
    }

    /**
     * Sets the {@link Callback} which is passed to {@link com.mckinsey.academy.xblocks.XBlock}
     * during integration.
     *
     * @param callback Callback to be set
     */
    public void setCallback(@NonNull C callback) {
        mCallback = callback;
    }

    /**
     * The method is expected to return empty implementation of {@link #mCallback} to implement
     * Null object pattern
     *
     * @return Empty implementation of generic callback
     */
    @NonNull
    public abstract C getNullCallback();
}