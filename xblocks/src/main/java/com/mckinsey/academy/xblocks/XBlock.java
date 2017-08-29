package com.mckinsey.academy.xblocks;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mckinsey.academy.xblocks.callbacks.Callback;
import com.mckinsey.academy.xblocks.info.XBlockInfo;

import java.lang.ref.WeakReference;

/**
 * XBlock library provides modular UI components(XBlocks) which are easily injectable in any
 * {@link android.app.Activity Activity} and {@link Fragment Fragment}. XBlocks receive it's own
 * input and also have own lifecycle.
 * <p>
 * <p>
 * Use {@code XBlock.with()} to get an XBlock object. Use {@code XBlock.injectXBlock} to inject
 * it in any app.
 * </p>
 */

public class XBlock {

    private WeakReference<FragmentManager> fmWeakRef;
    private WeakReference<Callback> callbackWeakRef;
    private int containerViewId;
    private XBlockInfo xBlockInfo;
    private Fragment mXBlockFragment = null;

    private XBlock(FragmentManager fm, int containerViewId, XBlockInfo xBlockInfo,
                   Callback callback) {
        this.fmWeakRef = new WeakReference<>(fm);
        this.callbackWeakRef = new WeakReference<>(callback);
        this.containerViewId = containerViewId;
        this.xBlockInfo = xBlockInfo;
    }

    /**
     * Factory method to get instance of XBlock. Passing {@link Callback Callback} is not
     * required here. XBlock will get callback from  the {@link Fragment Fragment} or
     * {@link android.app.Activity Activity} where it is inserted. The Activity or Fragment
     * should implement the required Callback interface if call are needed from XBlock.
     * <p>
     * <p>
     * Use {@link com.mckinsey.academy.xblocks.info.XBlockInfoBuilder XBlockInfoBuilder} to
     * create {@link XBlockInfo XBlockInfo} object. Implement matching callback (e.g with
     * VideoXBlockInfo pass VideoXBlockCallback).
     * </p>
     *
     * @param fragmentManager Use to add XBlock to specified container.
     * @param containerViewId Id of the container where XBlock is need.
     * @param xBlockInfo      Required information for a XBlock {@link XBlockInfo}
     * @return Returns XBlock object
     */
    public static XBlock with(@NonNull FragmentManager fragmentManager,
                              @NonNull int containerViewId, @NonNull XBlockInfo xBlockInfo) {
        return with(fragmentManager, containerViewId, xBlockInfo, null);
    }

    /**
     * Factory method to get instance of XBlock.
     * <p>
     * Use {@link com.mckinsey.academy.xblocks.info.XBlockInfoBuilder XBlockInfoBuilder} to
     * create {@link XBlockInfo XBlockInfo} object. Pass matching callback (e.g with
     * VideoXBlockInfo pass VideoXBlockCallback).
     * </p>
     *
     * @param fragmentManager Use to add XBlock to specified container.
     * @param containerViewId Id of the container where XBlock is need.
     * @param xBlockInfo      Required information for a XBlock {@link XBlockInfo}
     * @param callback        interface object to get callbacks from XBlock.
     * @return Returns XBlock object
     */

    public static XBlock with(@NonNull FragmentManager fragmentManager,
                              @NonNull int containerViewId, @NonNull XBlockInfo xBlockInfo,
                              Callback callback) {
        return new XBlock(fragmentManager, containerViewId, xBlockInfo, callback);
    }

    /**
     * After creating XBlock object use this method to insert XBlock component in the specified
     * container.
     *
     * @return return XBlock object.
     */
    public XBlock injectXBlock() {
        if (callbackWeakRef != null && callbackWeakRef.get() != null) {
            mXBlockFragment = xBlockInfo.getViewComponent(callbackWeakRef.get());
        } else {
            mXBlockFragment = xBlockInfo.getViewComponent();
        }

        if (fmWeakRef != null && fmWeakRef.get() != null) {
            FragmentTransaction ft = fmWeakRef.get().beginTransaction();
            ft.replace(containerViewId, mXBlockFragment).commit();
        } else {
            throw new NullPointerException("FragmentManager can not be null");
        }

        return this;
    }

    /**
     * Returns the fragment that was injected into the container.
     * Use this method to get the reference of the Fragment whenever any interaction is
     * required with the Fragment Object.
     *
     * @return
     */
    public Fragment getXBlockFragment() {
        return mXBlockFragment;
    }

    /**
     * This method should used to change XBlock component at runtime.(e.g At runtime changing
     * different XBlock component are required then use this method and {@code setCallback()} to
     * achieve such behavior).
     * After calling this method, call {@code XBlock.refresh()} to view the changes.
     *
     * @return return XBlock object.
     */
    public XBlock setXBlockInfo(XBlockInfo xBlockInfo) {
        this.xBlockInfo = xBlockInfo;
        return this;
    }

    /**
     * This method must be used with {@code XBlockInfo()} if callbacks are required from XBlock.
     * <p>
     * Following calling this method, call {@code XBlock.refresh()} to view the changes.
     *
     * @return return XBlock object.
     */
    public XBlock setCallback(Callback callback) {
        this.callbackWeakRef = new WeakReference<>(callback);
        return this;
    }

    /**
     * To view the changes at runtime call this method after {@code setCallback()}.
     *
     * @return return XBlock object.
     */
    public XBlock refresh() {
        injectXBlock();
        return this;
    }

}
