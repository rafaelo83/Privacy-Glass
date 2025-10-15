package de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.Privacy_Glass_Electric;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

import static de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.Privacy_Glass_Electric.PrivacyGlassElectricBlock.CONTROLLER;

public class WaveDataManager {
    private static final Map<ServerWorld, Map<BlockPos, WaveState>> waves = new HashMap<>();

    public static void startWave(
            ServerWorld world,
            BlockPos origin,
            Deque<BlockPos> waveFrontier,
            Set<BlockPos> visited,
            int currentStep,
            boolean startAction) {
        WaveState waveState = new WaveState(waveFrontier, visited, currentStep, startAction, origin);
        waves.computeIfAbsent(world, w -> new HashMap<>()).put(origin, waveState);
    }

    public static void continueWave(ServerWorld world, BlockPos origin) {
        WaveState waveState = waves.getOrDefault(world, Collections.emptyMap()).get(origin);
        if (waveState == null) return;

        Deque<BlockPos> frontier = waveState.frontier;
        Set<BlockPos> visited = waveState.visited;
        int step = waveState.step;
        boolean action = waveState.action;

        if (frontier.isEmpty() || step > PrivacyGlassElectricBlock.MAX_WAVE_STEPS) {
            waves.get(world).remove(origin);
            return;
        }

        Deque<BlockPos> nextWaveFrontier = new ArrayDeque<>();
        while (!frontier.isEmpty()) {
            BlockPos currentPos = frontier.poll();
            for (Direction dir : Direction.values()) {
                BlockPos nextPos = currentPos.offset(dir);
                if (visited.contains(nextPos)) continue;
                BlockState nextState = world.getBlockState(nextPos);
                if (nextState.getBlock() instanceof PrivacyGlassElectricBlock) {
                    if(!nextState.get(CONTROLLER)) {
                        world.setBlockState(nextPos, nextState.with(PrivacyGlassElectricBlock.POWERED, action), 3);
                    }
                    visited.add(nextPos);
                    nextWaveFrontier.add(nextPos);
                }
            }
        }

        waveState.frontier = nextWaveFrontier;
        waveState.step = step + 1;
        if (!nextWaveFrontier.isEmpty()) {
            world.scheduleBlockTick(origin, world.getBlockState(origin).getBlock(), 1);
        }
    }

    private static class WaveState {
        Deque<BlockPos> frontier;
        Set<BlockPos> visited;
        int step;
        boolean action; // true: turn on | false: turn off
        BlockPos origin;

        WaveState(Deque<BlockPos> f, Set<BlockPos> v, int s, boolean pAction, BlockPos pOrigin) {
            this.frontier = f;
            this.visited = v;
            this.step = s;
            this.action = pAction;
            this.origin = pOrigin;
        }
    }
}
