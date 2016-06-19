package com.infinityraider.agricraft.api.v3.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.infinityraider.agricraft.api.v3.crop.IAgriCrop;

/**
 * Implement in tools that should have rake functionality
 */
public interface IRake {
    /**
     * This is called when a tool implementing this interface is used on crop sticks with weeds in them
     *
     * @param world the World object for the crop sticks
     * @param pos the block position for the crop sticks
     * @param state the block state for the crop sticks
     * @param crop the ICrop object for the crop sticks
     * @param rake the ItemStack containing this tool
     * @return true to prevent further processing
     */
    boolean removeWeeds(World world, BlockPos pos, IBlockState state, IAgriCrop crop, ItemStack rake);
}
