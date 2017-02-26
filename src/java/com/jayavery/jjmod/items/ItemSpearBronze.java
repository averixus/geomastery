package com.jayavery.jjmod.items;

import com.jayavery.jjmod.entities.projectile.EntitySpearBronze;
import com.jayavery.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** Bronze spear tool item. */
public class ItemSpearBronze extends ItemSpearAbstract {

    public ItemSpearBronze() {

        super("spear_bronze", EquipMaterial.BRONZE_TOOL);
    }

    @Override
    protected void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearBronze thrown = new EntitySpearBronze(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntity(thrown);
    }
}
