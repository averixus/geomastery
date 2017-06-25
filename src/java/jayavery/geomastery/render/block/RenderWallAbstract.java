/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.render.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Abstract superclass for rendering walls which can connect to each other. */
@SideOnly(Side.CLIENT)
public abstract class RenderWallAbstract extends RenderDelayedBakingAbstract {
   
    public RenderWallAbstract(ResourceLocation block) {

        super(block);
    }

    /** @return Model for this wall's side with given properties. */
    protected abstract IModel getConnectedSide(boolean isTop,
            boolean isBottom);
    /** @return Offset rotation angle for this block's side models. */
    protected abstract int getSideAngle(EnumFacing facing);
    
    /** Loads a model whose name begins with this block's name.
     * @return The model or null if it failed to load. */
    protected IModel model(String name) {
        
        String path = this.block.toString().replace("block_", "block/");
        
        try {
            
            return ModelLoaderRegistry.getModel(
                    new ResourceLocation(path + name));
       
        } catch (Exception e) {
            
            return null;
        } 
    }
}
