package cn.mcmod.sakura.block;

import cn.mcmod.sakura.item.ItemLoader;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRedBeanCrop extends BlockCrops {

	@Override
	protected Item getCrop() {
		return ItemLoader.RED_BEAN;
	}
	@Override
	protected Item getSeed() {
		return ItemLoader.RED_BEAN;
	}
}