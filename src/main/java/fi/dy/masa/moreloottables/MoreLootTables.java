package fi.dy.masa.moreloottables;

import org.apache.logging.log4j.Logger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import fi.dy.masa.moreloottables.config.Configs;
import fi.dy.masa.moreloottables.event.EntityEventHandler;
import fi.dy.masa.moreloottables.proxy.IProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION,
    guiFactory = "fi.dy.masa.moreloottables.config.MoreLootTablesGuiFactory",
    updateJSON = "https://raw.githubusercontent.com/maruohon/moreloottables/master/update.json",
    acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.12")
public class MoreLootTables
{
    @Mod.Instance(Reference.MOD_ID)
    public static MoreLootTables instance;

    @SidedProxy(clientSide = "fi.dy.masa.moreloottables.proxy.ClientProxy", serverSide = "fi.dy.masa.moreloottables.proxy.ServerProxy")
    public static IProxy proxy;

    public static Logger logger;

    public static final ResourceLocation ENTITIES_WITHER = LootTableList.register(new ResourceLocation("minecraft", "entities/boss/wither"));

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        logger = event.getModLog();
        Configs.loadConfigsFromFile(event.getSuggestedConfigurationFile());

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        proxy.registerEventHandlers();
    }
}
