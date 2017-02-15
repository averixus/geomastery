package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntitySpearCopper;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** Copper spear tool item. */
public class ItemSpearCopper extends ItemSpearAbstract {

    public ItemSpearCopper() {

        super("spear_copper", EquipMaterial.COPPER_TOOL);
    }

    @Override
    protected void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearCopper thrown = new EntitySpearCopper(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntity(thrown);
    }
}
