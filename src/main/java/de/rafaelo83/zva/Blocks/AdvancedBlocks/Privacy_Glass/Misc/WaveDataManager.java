package de.rafaelo83.zva.Blocks.AdvancedBlocks.Privacy_Glass.Misc;

import de.rafaelo83.zva.Blocks.AdvancedBlocks.Privacy_Glass.PrivacyGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

import static de.rafaelo83.zva.Blocks.AdvancedBlocks.Privacy_Glass.PrivacyGlassBlock.POWERED;

public class WaveDataManager {
    private static final Map<ServerWorld, Map<PrivacyGlassBlock, WaveState>> waves = new HashMap<>();

    public static void startWave(ServerWorld world, PrivacyGlassBlock block, Deque<BlockPos> waveFrontier, Set<BlockPos> visited, int currentStep, boolean startAction) {
        WaveState waveState = new WaveState(waveFrontier, visited, currentStep, startAction);
        waves.computeIfAbsent(world, w -> new HashMap<>()).put(block, waveState);
    }

    public static void continueWave(ServerWorld world, PrivacyGlassBlock block) {
        WaveState waveState = waves.getOrDefault(world, Collections.emptyMap()).get(block);
        if (waveState == null) return;

        Deque<BlockPos> frontier = waveState.frontier;
        Set<BlockPos> visited = waveState.visited;
        int step = waveState.step;
        boolean action = waveState.action;

        if (frontier.isEmpty() || step > PrivacyGlassBlock.MAX_WAVE_STEPS) {
            // End of Wave
            waves.get(world).remove(block);
            return;
        }

        Deque<BlockPos> nextWaveFrontier = new ArrayDeque<>();
        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.poll();
            // ,_-> check every block in every cardinal direction + up/down
            for (Direction dir : Direction.values()) {
                BlockPos nextPos = currentPos.offset(dir);
                if (visited.contains(nextPos)) continue;
                BlockState nextState = world.getBlockState(nextPos);
                if (nextState.getBlock() instanceof PrivacyGlassBlock) {
                    world.setBlockState(nextPos, nextState.with(POWERED, !action), 3);
                    visited.add(nextPos);
                    nextWaveFrontier.add(nextPos);
                    // schedule its tick for it to propagate
                    world.scheduleBlockTick(nextPos, block, 2);
                }
            }
        }
        waveState.frontier = nextWaveFrontier;
        waveState.step = step + 1;
    }

    private static class WaveState {
        Deque<BlockPos> frontier;
        Set<BlockPos> visited;
        int step;
        boolean action; // true: turn on | false: turn off
        WaveState(Deque<BlockPos> f, Set<BlockPos> v, int s, boolean pAction) {
            this.frontier = f;
            this.visited = v;
            this.step = s;
            this.action = pAction;
        }
    }
}
