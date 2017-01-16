package fi.dy.masa.moreloottables.event;

import java.lang.reflect.Field;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.nbt.NBTTagCompound;
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

    /*@SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        if (Configs.enableWither && event.getEntity() instanceof EntityWither)
        {
            event.getDrops().clear();
        }
        else if (Configs.enableDragon && event.getEntity() instanceof EntityDragon)
        {
            event.getDrops().clear();
        }
    }*/

    @SuppressWarnings("deprecation")
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
        else if (Configs.enableDragon && event.getEntity() instanceof EntityDragon && event.getWorld().isRemote == false)
        {
            try
            {
                this.fieldDeathLootTable.set(event.getEntity(), MoreLootTables.ENTITIES_DRAGON);
            }
            catch (IllegalArgumentException e)
            {
                MoreLootTables.logger.error("Failed to set loot table for Ender Dragon");
            }
            catch (IllegalAccessException e)
            {
                MoreLootTables.logger.error("Failed to set loot table for Ender Dragon");
            }
        }
        else if (Configs.enableHusk && event.getWorld().isRemote == false && event.getEntity().getClass() == EntityZombie.class)
        {
            EntityZombie zombie = (EntityZombie) event.getEntity();

            if (zombie.getZombieType() == ZombieType.HUSK)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                zombie.writeEntityToNBT(nbt);
                nbt.setString("DeathLootTable", MoreLootTables.ENTITIES_HUSK.toString());
                zombie.readEntityFromNBT(nbt);
            }
        }
    }
}
