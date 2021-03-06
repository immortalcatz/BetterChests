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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import aroma1997.betterchests.BagInventory;
import aroma1997.betterchests.api.IBetterChest;

public class PotionUpgrade extends BasicUpgrade {

	private final Potion effect;

	protected PotionUpgrade(Potion effect) {
		this.effect = effect;
	}

	@Override
	public void updateChest(IBetterChest chest, int tick, World world,
			ItemStack item) {
		if (tick == 52 && chest instanceof BagInventory) {
			BagInventory b = (BagInventory) chest;
			((EntityPlayer) b.getEntity()).addPotionEffect(new PotionEffect(
					effect.id, 200, 1));
		}

	}

}
