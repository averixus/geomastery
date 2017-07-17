/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.utilities.BlockMaterial;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

/** Tar fluid block. */
public class BlockTar extends BlockFluidClassic {

    public BlockTar() {
        
        super(GeoBlocks.tarFluid, Material.WATER);
        BlockNew.setupBlock(this, "tar", null, -1, null);
        this.setQuantaPerBlock(3);        
    }
}
