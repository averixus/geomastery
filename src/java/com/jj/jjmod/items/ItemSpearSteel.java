package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntitySpearSteel;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemSpearSteel extends ItemSpearAbstract {

    public ItemSpearSteel() {

        super("spear_steel", EquipMaterial.STEEL_TOOL);
    }

    @Override
    public void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearSteel thrown =
                new EntitySpearSteel(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntity(thrown);
    }
}
