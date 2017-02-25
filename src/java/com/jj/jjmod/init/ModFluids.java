package com.jj.jjmod.init;

import com.jj.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFluids {
    
    public static Fluid tarFluid;
    
    public static void preInit() {

        FluidRegistry.registerFluid(tarFluid = new Fluid("fluid_tar",
                new ResourceLocation("jjmod:blocks/liquids/tar_still"),
                new ResourceLocation("jjmod:blocks/liquids/tar_flowing"))
                .setViscosity(10000));
    }
}
