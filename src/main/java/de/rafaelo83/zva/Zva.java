package de.rafaelo83.zva;

import de.rafaelo83.zva.Blocks.ModBlocks;
import de.rafaelo83.zva.Menus.ZvaCreativeTab;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Zva implements ModInitializer {
    public static final String MOD_ID = "zva";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Adding massive amounts of RAM");
        LOGGER.info("(In your dreams)");
        ModBlocks.init();
        ZvaCreativeTab.init();
    }
}
