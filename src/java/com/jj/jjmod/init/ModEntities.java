package com.jj.jjmod.init;

import com.jj.jjmod.entities.projectile.EntityArrowBronze;
import com.jj.jjmod.entities.projectile.EntityArrowCopper;
import com.jj.jjmod.entities.projectile.EntityArrowFlint;
import com.jj.jjmod.entities.projectile.EntityArrowSteel;
import com.jj.jjmod.entities.projectile.EntityArrowWood;
import com.jj.jjmod.entities.projectile.EntitySpearBronze;
import com.jj.jjmod.entities.projectile.EntitySpearCopper;
import com.jj.jjmod.entities.projectile.EntitySpearFlint;
import com.jj.jjmod.entities.projectile.EntitySpearSteel;
import com.jj.jjmod.entities.projectile.EntitySpearWood;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.render.projectile.RenderArrowBronze;
import com.jj.jjmod.render.projectile.RenderArrowCopper;
import com.jj.jjmod.render.projectile.RenderArrowFlint;
import com.jj.jjmod.render.projectile.RenderArrowSteel;
import com.jj.jjmod.render.projectile.RenderArrowWood;
import com.jj.jjmod.render.projectile.RenderSpearBronze;
import com.jj.jjmod.render.projectile.RenderSpearCopper;
import com.jj.jjmod.render.projectile.RenderSpearFlint;
import com.jj.jjmod.render.projectile.RenderSpearSteel;
import com.jj.jjmod.render.projectile.RenderSpearWood;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static int entityID = 0;

    public static void preInit() {

        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_wood"), EntitySpearWood.class,
                "spear_wood", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_flint"), EntitySpearFlint.class,
                "spear_flint", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_copper"), EntitySpearCopper.class,
                "spear_copper", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_bronze"), EntitySpearBronze.class,
                "spear_bronze", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "spear_steel"), EntitySpearSteel.class,
                "spear_steel", entityID++, Main.instance, 80, 3, true);

        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_wood"), EntityArrowWood.class,
                "arrow_wood", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_flint"), EntityArrowFlint.class,
                "arrow_flint", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_copper"), EntityArrowCopper.class,
                "arrow_copper", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_bronze"), EntityArrowBronze.class,
                "arrow_bronze", entityID++, Main.instance, 80, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("jjmod",
                "arrow_steel"), EntityArrowSteel.class,
                "arrow_steel", entityID++, Main.instance, 80, 3, true);

    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearWood.class, RenderSpearWood::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearFlint.class, RenderSpearFlint::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearCopper.class, RenderSpearCopper::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearBronze.class, RenderSpearBronze::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpearSteel.class, RenderSpearSteel::new);

        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowWood.class, RenderArrowWood::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowFlint.class, RenderArrowFlint::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowCopper.class, RenderArrowCopper::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowBronze.class, RenderArrowBronze::new);
        RenderingRegistry.registerEntityRenderingHandler(
                EntityArrowSteel.class, RenderArrowSteel::new);
    }
}
