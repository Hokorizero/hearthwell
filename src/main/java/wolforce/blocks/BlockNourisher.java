package wolforce.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.base.BlockEnergyConsumer;
import wolforce.base.BlockMachineBase;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TileNourisher;

public class BlockNourisher extends BlockMachineBase implements HasTE, BlockEnergyConsumer {

	public BlockNourisher(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileNourisher();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Makes crops around it grow faster.", "Consumes " + getEnergyConsumption() + " per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.nourisherConsumption;
	}
}
