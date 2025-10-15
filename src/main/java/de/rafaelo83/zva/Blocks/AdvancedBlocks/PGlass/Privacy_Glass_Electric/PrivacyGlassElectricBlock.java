package de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.Privacy_Glass_Electric;

import de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.AbstractPrivacyGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class PrivacyGlassElectricBlock extends AbstractPrivacyGlass {
    public static final BooleanProperty CONTROLLER = BooleanProperty.of("controller");

    public PrivacyGlassElectricBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(CONTROLLER,false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONTROLLER);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(world.isClient || !state.get(CONTROLLER)) return;
        boolean powered = world.isReceivingRedstonePower(pos);
        boolean wasPowered = state.get(POWERED);

        if(powered != wasPowered){
            startWave((ServerWorld) world,pos);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean currentlyController = state.get(CONTROLLER);
        if(player.getMainHandStack().getItem().equals(Items.REDSTONE) && !state.get(POWERED)){
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.75F, 1.0F );
            world.setBlockState(pos, state.with(CONTROLLER, !currentlyController));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
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

        WaveDataManager.startWave(world, origin, frontier, visited, 1, !state1.get(POWERED));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.isClient) return;
        WaveDataManager.continueWave(world, pos);
    }
}
