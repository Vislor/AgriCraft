package com.infinityraider.agricraft.api.v3;

import com.infinityraider.agricraft.api.v3.stat.IStatCalculator;
import com.infinityraider.agricraft.api.v3.requirment.IGrowthRequirement;
import com.infinityraider.agricraft.api.v3.requirment.IGrowthRequirementBuilder;
import com.infinityraider.agricraft.api.v3.crop.IAgriCrop;
import com.infinityraider.agricraft.api.v3.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v3.stat.IAgriStat;
import com.infinityraider.agricraft.api.v3.util.BlockWithMeta;
import com.infinityraider.agricraft.api.v3.mutation.IMutationRegistry;
import com.infinityraider.agricraft.api.v3.plant.IPlantRegistry;
import com.infinityraider.agricraft.api.APIBase;
import com.infinityraider.agricraft.api.v3.fertiliser.IFertilizerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * AgriCraft API.
 *
 * This is the third incarnation of the AgriCraft API. This version is
 * currently under a development, and is not to be considered stable unless
 * otherwise notified by an official AgriCraft Developer.
 *
 * @author AgriCraft Team
 * @version 3.0.0
 *
 */
@SuppressWarnings("unused")
public interface APIv3 extends APIBase {

	/**
	 * Return true if AgriCraft is active in the given dimension. If the
	 * parameter is null, it will return true if AgriCraft is active in any
	 * dimension.
	 *
	 * If this returns false, you can completely ignore AgriCraft (for the given
	 * dimension).
	 *
	 * This may return false if AgriCraft is disabled (e.g. it is only included
	 * in a modpack for some items), or if a dimension is blacklisted.
	 *
	 * @param world An optional world
	 */
	boolean isActive(World world);

	/**
	 * Retrieves the mutation registry for managing mutations.
	 *
	 * @return the instance of IMutationRegistry associated with this mod.
	 */
	IMutationRegistry getMutationRegistry();

	/**
	 * Retrieves the plant registry for managing plants.
	 *
	 * @return the instance of IPlantRegistry associated with this mod.
	 */
	IPlantRegistry getPlantRegistry();
	
	/**
	 * Retrieves the fertilizer registry for managing plants.
	 *
	 * @return the instance of IFertilizerRegistry associated with this mod.
	 */
	IFertilizerRegistry getFertilizerRegistry();

	/**
	 * Checks if AgriCraft is configured to prevent the given seed to be used in
	 * its normal way. Note: This method may ignore its parameter if AgriCraft
	 * is configured to prevent all native planting it can prevent.
	 *
	 * @param seed Any ItemStack that is a seed.
	 * @return True if the seed must be handled by AgriCraft.
	 */
	boolean isNativePlantingDisabled(ItemStack seed);

	/**
	 * Checks if the given seed can be handled by AgriCraft, i.e. can be planted
	 * in crops.
	 *
	 * @param seed Any ItemStack that is a seed.
	 * @return True if the seed can be planted in crops.
	 */
	boolean isHandledByAgricraft(ItemStack seed);

	/**
	 * Gives the statistics for the given seeds.
	 *
	 * @param seed Any ItemStack that is a seed.
	 * @return An ISeedStats object that describes the given seeds, or null if
	 * the given item was no seed.
	 */
	IAgriStat getStats(ItemStack seed);

	/**
	 * Register a default soil that any crop that doesn't require a specific
	 * soil can grow on
	 *
	 * @return true if the soil was successfully registered
	 */
	boolean registerDefaultSoil(BlockWithMeta soil);

	/**
	 * Checks if the given position contains crops.
	 *
	 * @param world World object
	 * @param pos the block position
	 * @return True if there are crops.
	 */
	boolean isCrop(World world, BlockPos pos);

	/**
	 * Fetches a crop instance from a world. Calling this method once and then
	 * calling needed methods on the ICrop object is much more efficient then
	 * calling the methods separately via the API. This only requires 1 call to
	 * world.getTileEntity(x, y, z) instead of multiple api.someMethod(world, x,
	 * y, z) and then api.someOtherMethod(world, x, y, z)
	 *
	 * @param world the world to look in.
	 * @param pos the location of the crop.
	 * @return the crop at the location or null if not found.
	 */
	IAgriCrop getCrop(World world, BlockPos pos);

	/**
	 * Checks if AgriCraft is configured to require rakes to remove weeds.
	 *
	 * @return True if weeds cannot be removed by hand.
	 */
	boolean isRakeRequiredForWeeding();

	/**
	 * Tries to harvest the content of the crops at the given position. This
	 * equates to a player right-clicking the crops; it will return a harvest
	 * and leave the crops and the harvested plant in place. All world
	 * interaction will be handled by this method.
	 *
	 * <p>
	 * It will return a list of harvested items which may be empty for crops
	 * with a chance-based harvest result. If the harvest failed for any reason,
	 * the result will be null.
	 * </p>
	 *
	 * @param world World object
	 * @param pos the block position
	 * @return A harvest result or null.
	 */
	@Deprecated
	List<ItemStack> harvest(World world, BlockPos pos);

	/**
	 * Tries to destroy the crops at the given position. This equates to a
	 * player left-clicking the crops; it will return the broken crops, the seed
	 * and possibly a harvest and set the block to air. All world interaction
	 * will be handled by this method.
	 *
	 * <p>
	 * It will return a list of dropped items which may be empty. If the
	 * destroying failed for any reason, e.g. the position did not specify a
	 * crops, the result will be null.
	 * </p>
	 *
	 * @param world World object
	 * @param pos the block position
	 * @return A list of dropped items or null.
	 */
	@Deprecated
	List<ItemStack> destroy(World world, BlockPos pos);

	/**
	 * Register a soil that crop sticks can be placed on use this if you have
	 * your own ICropPlant which doesn't use IGrowthRequirement
	 *
	 * @return true if the soil was successfully registered
	 */
	boolean registerValidSoil(BlockWithMeta soil);

	/**
	 * @return the cap imposed to seed stats specified in the config
	 */
	short getStatCap();

	/**
	 * Analyzes the seed in the stack
	 *
	 * @param seed the stack
	 */
	void analyze(ItemStack seed);

	/**
	 * Method used to set custom stat calculation logic
	 *
	 * @param calculator the IStatCalculator Object to be used when calculating
	 * stats
	 */
	void setStatCalculator(IStatCalculator calculator);

	/**
	 * Gets a new IGrowthRequirementBuilder object used to create new
	 * IGrowthRequirement objects
	 *
	 * @return a new IGrowthRequirementBuilder instance
	 */
	IGrowthRequirementBuilder createGrowthRequirementBuilder();

	/**
	 * @return a new IGrowthRequirement object which has default vanilla
	 * behaviour, meaning no base block, soil is farmland and requires
	 * brightness
	 */
	IGrowthRequirement createDefaultGrowthRequirement();

	/**
	 * Checks if a seed is discovered in the journal
	 *
	 * @param journal an ItemStack holding the journal
	 * @param seed an ItemStack containing a seed
	 * @return if the seed is discovered in the journal
	 */
	boolean isPlantDiscovered(ItemStack journal, IAgriPlant plant);

	/**
	 * This adds an entry the journal, for example when a seed is analyzed in
	 * the seed analyzer this method is called. This internally checks if the
	 * seed is discovered already before adding to prevent duplicate entries
	 *
	 * @param journal an ItemStack holding the journal
	 * @param plant the plant to discover.
	 * @param isDiscovered whether or not the plant is discovered.
	 */
	void setPlantDiscovered(ItemStack journal, IAgriPlant plant, boolean isDiscovered);

	/**
	 * Gets an ArrayList containing all seeds discovered in this journal
	 *
	 * @param journal an ItemStack holding the journal
	 * @return an ArrayList containing an ItemStack for every discovered seed
	 * (the list may be empty but will never be null)
	 */
	List<IAgriPlant> getPlantsDiscovered(ItemStack journal);

}
