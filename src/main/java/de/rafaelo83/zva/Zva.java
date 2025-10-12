package de.rafaelo83.zva;

import de.rafaelo83.zva.Blocks.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zva implements ModInitializer {
    public static final String MOD_ID = "zva";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Adding massive amounts of RAM");
        LOGGER.info("(In your dreams, actually initializing 'Privacy Glass & more')");
        ModBlocks.init();
        //ZvaCreativeTab.init();
        registerVanillaCreativeTabs();

    }

    public static void registerVanillaCreativeTabs() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(ModBlocks.PRIVACY_GLASS);
        });
    }
}
