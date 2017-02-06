package com.jj.jjmod.items;

import com.jj.jjmod.entities.projectile.EntitySpearWood;
import com.jj.jjmod.utilities.EquipMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemSpearWood extends ItemSpearAbstract {

    public ItemSpearWood() {

        super("spear_wood", EquipMaterial.WOOD_TOOL);
    }

    @Override
    public void throwSpear(World world, EntityPlayer player, float velocity,
            int damage) {

        EntitySpearWood thrown =
                new EntitySpearWood(world, player, damage);
        thrown.setAim(player, player.rotationPitch,
                player.rotationYaw, 0.0F, velocity, 1.0F);
        world.spawnEntity(thrown);
    }
}
