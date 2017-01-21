package com.jj.jjmod.init;

import com.jj.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModLiquids {
    
    public static Fluid tarFluid;
    public static BlockFluidClassic tarBlock;
    
    public static final ResourceLocation TAR_LOCATION = new ResourceLocation("jjmod:block_tar");
    public static final ResourceLocation TAR_TEXTURE = new ResourceLocation("jjmod:blocks/liquids/tar");
    
    public static void preInit() {
        
        tarFluid = new Fluid("fluid_tar", TAR_TEXTURE, TAR_TEXTURE);
        FluidRegistry.registerFluid(tarFluid);
        tarBlock = (BlockFluidClassic) new BlockFluidClassic(tarFluid, BlockMaterial.TAR).setRegistryName("block_tar").setUnlocalizedName("block_tar");
        GameRegistry.register(tarBlock);
        tarBlock.setCreativeTab(CreativeTabs.MISC);
        
        Item item = Item.getItemFromBlock(tarBlock);

        ModelBakery.registerItemVariants(item);

        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(TAR_LOCATION, "normal");

        ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

        ModelLoader.setCustomStateMapper(tarBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return modelResourceLocation;
            }
        });
        
    }

}
