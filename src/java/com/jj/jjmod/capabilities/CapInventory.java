package com.jj.jjmod.capabilities;

import com.jj.jjmod.main.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapInventory {

    @CapabilityInject(ICapInventory.class)
    public static final Capability<ICapInventory> CAP_INVENTORY = null;

    public static final ResourceLocation ID =
            new ResourceLocation(Main.MODID, "CapabilityInventory");
}
