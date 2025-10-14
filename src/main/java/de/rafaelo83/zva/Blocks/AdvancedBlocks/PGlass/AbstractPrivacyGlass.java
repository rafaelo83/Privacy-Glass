package de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public abstract class AbstractPrivacyGlass extends TransparentBlock {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final int MAX_WAVE_STEPS = 10;

    public AbstractPrivacyGlass(Settings settings) {
        super(settings.nonOpaque().strength(0.3F));
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    } //Add 'powered' as block property

    @Override
    protected int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return state.get(POWERED) ? 255 : 0;
    } //Change opacity depending on block state
}
