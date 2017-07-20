/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.projectile;

import jayavery.geomastery.main.Geomastery;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/** Factories for custom arrow rendering. */
public class RenderArrowFactory implements IRenderFactory<EntityArrow> {
    
    /** Factor for copper arrow renderers. */
    public static final RenderArrowFactory ARROW_COPPER = new RenderArrowFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/arrow_copper.png"));
    /** Factory for bronze arrow renderers. */
    public static final RenderArrowFactory ARROW_BRONZE = new RenderArrowFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/arrow_bronze.png"));
    /** Factory for flint arrow renderers. */
    public static final RenderArrowFactory ARROW_FLINT = new RenderArrowFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/arrow_flint.png"));
    /** Factory for steel arrow renderers. */
    public static final RenderArrowFactory ARROW_STEEL = new RenderArrowFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/arrow_steel.png"));
    /** Factor for wood arrow renderers. */
    public static final RenderArrowFactory ARROW_WOOD = new RenderArrowFactory(new ResourceLocation(Geomastery.MODID, "textures/entity/projectiles/arrow_wood.png"));

    /** Texture of this render. */
    private ResourceLocation texture;
    
    private RenderArrowFactory(ResourceLocation texture) {
        
        this.texture = texture;
    }
    
    @Override
    public Render<? super EntityArrow> createRenderFor(
            RenderManager manager) {

        return new RenderArrow(manager, this.texture);
    }
}
