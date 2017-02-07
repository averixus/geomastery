package com.jj.jjmod.render.projectile;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSpearFactory implements IRenderFactory<EntityArrow> {

    public static final RenderSpearFactory SPEAR_COPPER = new RenderSpearFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_copper.png"));
    public static final RenderSpearFactory SPEAR_BRONZE = new RenderSpearFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_bronze.png"));
    public static final RenderSpearFactory SPEAR_FLINT = new RenderSpearFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_flint.png"));
    public static final RenderSpearFactory SPEAR_STEEL = new RenderSpearFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_steel.png"));
    public static final RenderSpearFactory SPEAR_WOOD = new RenderSpearFactory(new ResourceLocation("jjmod:textures/entity/projectiles/spear_wood.png"));
    
protected ResourceLocation texture;
    
    private RenderSpearFactory(ResourceLocation texture) {
        
        this.texture = texture;
    }
    
    @Override
    public Render<? super EntityArrow> createRenderFor(
            RenderManager manager) {

        return new RenderArrow(manager, this.texture);
    }
}
