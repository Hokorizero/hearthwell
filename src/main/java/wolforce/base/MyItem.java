package wolforce.base;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wolforce.Util;

public class MyItem extends Item {

	private String[] lore;

	public MyItem(String name, String... lore) {
		Util.setReg(this, name);
		// setMaxStackSize(64);
		this.lore = lore;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.addAll(Arrays.asList(lore));
	}
}
