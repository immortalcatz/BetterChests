/**
 * The code of BetterChests and all related materials like textures is copyrighted material.
 * It may only be redistributed or used for Commercial purposes with the permission of Aroma1997.
 * 
 * All Rights reserved (c) by Aroma1997
 * 
 * See https://github.com/Aroma1997/BetterChests/blob/master/LICENSE.md for more information.
 */

package aroma1997.betterchests.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import aroma1997.betterchests.BagInventory;
import aroma1997.betterchests.InventoryFilter.BCFilterFilter;
import aroma1997.betterchests.api.IBetterChest;
import aroma1997.core.util.InvUtil;

public class Resupply extends BasicUpgrade {

	@Override
	public void updateChest(IBetterChest chest, int tick, World world,
			ItemStack item) {
		if (tick % 8 != 2) {
			return;
		}
		if (chest != null && chest instanceof BagInventory) {
			BagInventory inv = (BagInventory) chest;
			EntityPlayer player = (EntityPlayer) inv.getEntity();
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack itemStack = player.inventory.getStackInSlot(i);
				if (itemStack == null) {
					continue;
				}
				ItemStack get = InvUtil.getFirstItem(inv, itemStack, true,
						null,
						new BCFilterFilter(inv.getFiltersForUpgrade(item)));
				if (get == null) {
					continue;
				}

				if (itemStack.stackSize + get.stackSize > (int) (itemStack
						.getMaxStackSize() / 2.0F + 0.5F)) {
					int over = (int) (itemStack.getMaxStackSize() / 2.0F + 0.5F)
							- itemStack.stackSize;
					if (over > 0) {
						get.stackSize -= over;
						itemStack.stackSize += over;
					}
				} else {
					itemStack.stackSize += get.stackSize;
					InvUtil.getFirstItem(inv, itemStack, false);
				}
			}
		}
	}

}
