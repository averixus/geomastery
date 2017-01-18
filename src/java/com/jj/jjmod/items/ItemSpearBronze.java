package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntitySpearBronze;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemSpearBronze extends ItemSpearAbstract {

    public ItemSpearBronze() {

        super("spear_bronze", EquipMaterial.BRONZE_TOOL);
    }

    @Override
    public void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearBronze thrown =
                new EntitySpearBronze(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntityInWorld(thrown);
    }
}
