package de.rafaelo83.zva.Blocks.AdvancedBlocks;

import de.rafaelo83.zva.Blocks.Misc.WaveDataManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class PrivacyGlassBlock extends Block {
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final int MAX_WAVE_STEPS = 10;

    public PrivacyGlassBlock(Settings settings) {
        super(settings.nonOpaque().strength(0.3F));
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    protected int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return state.get(POWERED) ? 255 : 0;
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(POWERED);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        // Start the wave from this lamp
        startWave((ServerWorld) world, pos);
        world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.8f, 1.0f);
        return ActionResult.CONSUME;
    }

    private void startWave(ServerWorld world, BlockPos origin) {
        // Use a set to record positions already in wave
        Set<BlockPos> visited = new HashSet<>();
        Deque<BlockPos> frontier = new ArrayDeque<>();
        frontier.add(origin);
        visited.add(origin);

        // Immediately mark the origin lit
        BlockState state1 = world.getBlockState(origin);
        world.setBlockState(origin, state1.with(POWERED, !state1.get(POWERED)), 3);

        // Schedule the next wave tick
        world.scheduleBlockTick(origin, this, 1);
                  // schedule 1 tick later to continue wave

        WaveDataManager.startWave(world, this, frontier, visited, 1, state1.get(POWERED));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        WaveDataManager.continueWave(world, this);
    }
}
