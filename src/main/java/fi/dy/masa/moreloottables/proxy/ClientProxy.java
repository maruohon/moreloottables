package fi.dy.masa.moreloottables.proxy;

import net.minecraftforge.common.MinecraftForge;
import fi.dy.masa.moreloottables.config.Configs;

public class ClientProxy implements IProxy
{
    @Override
    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new Configs());
    }
}
