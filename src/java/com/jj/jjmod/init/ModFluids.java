package com.jj.jjmod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    
    public static Fluid tarFluid;
    
    public static void preInit() {

        FluidRegistry.registerFluid(tarFluid = new Fluid("fluid_tar",
                new ResourceLocation("jjmod:blocks/liquids/tar_still"),
                new ResourceLocation("jjmod:blocks/liquids/tar_flowing"))
                .setViscosity(10000));
    }
}
