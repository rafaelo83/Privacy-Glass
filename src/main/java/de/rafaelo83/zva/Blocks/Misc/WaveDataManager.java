package de.rafaelo83.zva.Blocks.Misc;

import de.rafaelo83.zva.Blocks.AdvancedBlocks.PrivacyGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

import static de.rafaelo83.zva.Blocks.AdvancedBlocks.PrivacyGlassBlock.POWERED;

public class WaveDataManager {
    private static final Map<ServerWorld, Map<PrivacyGlassBlock, WaveState>> waves = new HashMap<>();

    public static void startWave(ServerWorld world, PrivacyGlassBlock block, Deque<BlockPos> frontier, Set<BlockPos> visited, int currentStep, boolean startAction) {
        WaveState ws = new WaveState(frontier, visited, currentStep, startAction);
        waves.computeIfAbsent(world, w -> new HashMap<>()).put(block, ws);
    }

    public static void continueWave(ServerWorld world, PrivacyGlassBlock block) {
        WaveState ws = waves.getOrDefault(world, Collections.emptyMap()).get(block);
        if (ws == null) return;

        Deque<BlockPos> frontier = ws.frontier;
        Set<BlockPos> visited = ws.visited;
        int step = ws.step;
        boolean action = ws.action;

        if (frontier.isEmpty() || step > PrivacyGlassBlock.MAX_WAVE_STEPS) {
            // wave ended
            waves.get(world).remove(block);
            return;
        }

        Deque<BlockPos> nextFrontier = new ArrayDeque<>();
        while (!frontier.isEmpty()) {
            BlockPos p = frontier.poll();
            // for each neighbor (6 directions)
            for (Direction dir : Direction.values()) {
                BlockPos np = p.offset(dir);
                if (visited.contains(np)) continue;
                BlockState ns = world.getBlockState(np);
                if (ns.getBlock() instanceof PrivacyGlassBlock) {
                    world.setBlockState(np, ns.with(POWERED, !action), 3);
                    visited.add(np);
                    nextFrontier.add(np);
                    // schedule its tick so propagation continues from it
                    world.scheduleBlockTick(np, block, 2);
                }
            }
        }
        ws.frontier = nextFrontier;
        ws.step = step + 1;
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
