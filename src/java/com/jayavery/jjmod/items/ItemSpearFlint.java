package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntitySpearFlint;
import com.jayavery.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** Flint spear tool item. */
public class ItemSpearFlint extends ItemSpearAbstract {

    public ItemSpearFlint() {

        super("spear_flint", EquipMaterial.FLINT_TOOL);
    }

    @Override
    protected void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearFlint thrown = new EntitySpearFlint(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntity(thrown);
    }
}
