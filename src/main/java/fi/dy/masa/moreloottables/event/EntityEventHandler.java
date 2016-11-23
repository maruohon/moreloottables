package fi.dy.masa.moreloottables.event;

import java.lang.reflect.Field;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import fi.dy.masa.moreloottables.MoreLootTables;
import fi.dy.masa.moreloottables.config.Configs;

public class EntityEventHandler
{
    private final Field fieldDeathLootTable;

    public EntityEventHandler()
    {
        this.fieldDeathLootTable = ReflectionHelper.findField(EntityLiving.class, "field_184659_bA", "deathLootTable");
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event)
    {
        if (Configs.enableWither && event.getEntity() instanceof EntityWither && event.getWorld().isRemote == false)
        {
            try
            {
                this.fieldDeathLootTable.set(event.getEntity(), MoreLootTables.ENTITIES_WITHER);
            }
            catch (IllegalArgumentException e)
            {
                MoreLootTables.logger.error("Failed to set loot table for Wither");
            }
            catch (IllegalAccessException e)
            {
                MoreLootTables.logger.error("Failed to set loot table for Wither");
            }
        }
    }
}
