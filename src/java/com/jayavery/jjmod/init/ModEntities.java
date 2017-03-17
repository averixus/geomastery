package com.jayavery.jjmod.init;

import com.jayavery.jjmod.entities.FallingTreeBlock;
import com.jayavery.jjmod.entities.projectile.EntityArrowBronze;
import com.jayavery.jjmod.entities.projectile.EntityArrowCopper;
import com.jayavery.jjmod.entities.projectile.EntityArrowFlint;
import com.jayavery.jjmod.entities.projectile.EntityArrowSteel;
import com.jayavery.jjmod.entities.projectile.EntityArrowWood;
import com.jayavery.jjmod.entities.projectile.EntitySpearBronze;
import com.jayavery.jjmod.entities.projectile.EntitySpearCopper;
import com.jayavery.jjmod.entities.projectile.EntitySpearFlint;
import com.jayavery.jjmod.entities.projectile.EntitySpearSteel;
import com.jayavery.jjmod.entities.projectile.EntitySpearWood;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.render.RenderFallingTreeBlock;
import com.jayavery.jjmod.render.projectile.RenderArrowFactory;
import com.jayavery.jjmod.render.projectile.RenderSpearFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    /** ID counter. */
    private static int entityID = 0;

    public static void preInit() {

        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_wood"), EntitySpearWood.class,
                "spear_wood", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_flint"), EntitySpearFlint.class,
                "spear_flint", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_copper"), EntitySpearCopper.class,
                "spear_copper", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_bronze"), EntitySpearBronze.class,
                "spear_bronze", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_steel"), EntitySpearSteel.class,
                "spear_steel", entityID++, Jjmod.instance, 80, 3, true);

        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_wood"), EntityArrowWood.class,
                "arrow_wood", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_flint"), EntityArrowFlint.class,
                "arrow_flint", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_copper"), EntityArrowCopper.class,
                "arrow_copper", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_bronze"), EntityArrowBronze.class,
                "arrow_bronze", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_steel"), EntityArrowSteel.class,
                "arrow_steel", entityID++, Jjmod.instance, 80, 3, true);
        
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "falling_trunk"), FallingTreeBlock.Trunk.class,
                "falling_trunk", entityID++, Jjmod.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "falling_leaves"), FallingTreeBlock.Leaves.class,
                "falling_leaves", entityID++, Jjmod.instance, 80, 3, true);
    }
    
    public static void preInitClient() {
        
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearWood.class, RenderSpearFactory.SPEAR_WOOD);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearFlint.class, RenderSpearFactory.SPEAR_FLINT);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearCopper.class, RenderSpearFactory.SPEAR_COPPER);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearBronze.class, RenderSpearFactory.SPEAR_BRONZE);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearSteel.class, RenderSpearFactory.SPEAR_STEEL);

        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowWood.class, RenderArrowFactory.ARROW_WOOD);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowFlint.class, RenderArrowFactory.ARROW_FLINT);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowCopper.class, RenderArrowFactory.ARROW_COPPER);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowBronze.class, RenderArrowFactory.ARROW_BRONZE);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowSteel.class, RenderArrowFactory.ARROW_STEEL);
        RenderingRegistry.registerEntityRenderingHandler(
                FallingTreeBlock.Leaves.class, RenderFallingTreeBlock::new);
        RenderingRegistry.registerEntityRenderingHandler(
                FallingTreeBlock.Trunk.class, RenderFallingTreeBlock::new);
    }
}
