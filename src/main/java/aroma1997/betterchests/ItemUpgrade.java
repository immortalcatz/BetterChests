/**
 * The code of BetterChests and all related materials like textures is copyrighted material.
 * It may only be redistributed or used for Commercial purposes with the permission of Aroma1997.
 * 
 * All Rights reserved (c) by Aroma1997
 * 
 * See https://github.com/Aroma1997/BetterChests/blob/master/LICENSE.md for more information.
 */

package aroma1997.betterchests;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import aroma1997.betterchests.api.IBetterChest;
import aroma1997.betterchests.api.IUpgrade;
import aroma1997.core.client.util.Colors;
import aroma1997.core.inventories.ContainerBasic;
import aroma1997.core.items.AromicItem;

public class ItemUpgrade extends AromicItem implements IUpgrade {

	public ItemUpgrade() {
		super();
		setCreativeTab(BetterChests.creativeTabBC);
		setUnlocalizedName("betterchests:upgrades");
		setHasSubtypes(true);
		registerModels();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		Upgrade upgrade = Upgrade.values()[par1ItemStack.getItemDamage()];
		if (upgrade.requiresPower())
			par3List.add(Colors.YELLOW
					+ StatCollector
							.translateToLocalFormatted("info.betterchests:tooltip.requirespower"));
		if (upgrade.getRequirement() != null) {
			par3List.add(StatCollector.translateToLocalFormatted(
					"info.betterchests:tooltip.requires", Colors.YELLOW
							+ upgrade.getRequirement().getItem()
									.getDisplayName()));
		}

		if (upgrade.canUseFilter()) {
			par3List.add(StatCollector
					.translateToLocal("info.betterchests:tooltip.supportsfilter"));
		}

		if (upgrade.getMaxAmount() != 0) {
			par3List.add(StatCollector.translateToLocalFormatted(
					"info.betterchests:tooltip.maxamount",
					upgrade.getMaxAmount()));
		}
		if (!upgrade.canBagTakeUpgrade() && !upgrade.canChestTakeUpgrade()) {
		} else if (!upgrade.canChestTakeUpgrade()) {
			par3List.add(StatCollector
					.translateToLocal("info.betterchests:tooltip.nochest"));
		} else if (!upgrade.canBagTakeUpgrade()) {
			par3List.add(StatCollector
					.translateToLocal("info.betterchests:tooltip.nobag"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int i = 0; i < Upgrade.values().length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	// @Override
	// public String getItemStackDisplayName(ItemStack par1ItemStack) {
	// return Upgrade.values()[par1ItemStack.getItemDamage()].getName();
	// }

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.betterchests:upgrade."
				+ Upgrade.values()[stack.getItemDamage()].toString()
						.toLowerCase();
	}

	@Override
	public boolean canChestTakeUpgrade(ItemStack item) {
		if (item == null) {
			return false;
		}
		return Upgrade.values()[item.getItemDamage()].canChestTakeUpgrade();
	}

	@Override
	public boolean canBagTakeUpgrade(ItemStack item) {
		if (item == null) {
			return false;
		}
		Upgrade upgrade = Upgrade.values()[item.getItemDamage()];
		return upgrade.canBagTakeUpgrade();
	}

	@Override
	public List<ItemStack> getRequiredUpgrade(ItemStack item) {
		if (item == null) {
			return null;
		}
		Upgrade upgrade = Upgrade.values()[item.getItemDamage()];
		if (upgrade.getRequirement() == null) {
			return null;
		}
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(upgrade.getRequirement().getItem());
		return list;
	}

	@Override
	public void update(IBetterChest chest, int tick, World world, ItemStack item) {
		if (!UpgradeHelper.isUpgrade(item)) {
			return;
		}
		Upgrade.values()[item.getItemDamage()].getUpgrade().updateChest(chest,
				tick, world, item);
	}

	@Override
	public int getMaxUpgrades(ItemStack item) {
		if (item == null) {
			return 0;
		}
		return Upgrade.values()[item.getItemDamage()].getMaxAmount();
	}

	@Override
	public void onUpgradeInstalled(ItemStack item, IBetterChest chest) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawGuiContainerForegroundLayer(GuiContainer gui,
			Container container, int par1, int par2, ItemStack item) {
		Upgrade.values()[item.getItemDamage()].getUpgrade().draw(gui,
				(ContainerBasic) container, par1, par2, item);
	}

	@Override
	public boolean canBeDisabled(ItemStack stack) {
		return Upgrade.values()[stack.getItemDamage()].canBeDisabled();
	}

	@Override
	public boolean supportsFilter(ItemStack stack, boolean inverted) {
		return Upgrade.values()[stack.getItemDamage()].canUseFilter();
	}

}
