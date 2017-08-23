package com.mckinsey.academy.xblocks.listener;

/**
 * RecyclerView on item Select listener to notify the fragment which item is selected or
 * unselected
 */

public interface RecyclerViewItemSelectListener {

    void onItemSelect(int position);
}
