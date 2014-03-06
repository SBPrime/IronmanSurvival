/*
 * The MIT License
 *
 * Copyright 2014 SBPrime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primesoft.ironmansurvival;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.painting.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.*;
import org.bukkit.event.world.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author SBPrime
 */
public class EventListener implements Listener {

    private IronmanSurvival m_parent;

    public EventListener(IronmanSurvival parent) {
        m_parent = parent;
    }

    private void print(Event event) {
        IronmanSurvival.log(event.getEventName());
    }

    @EventHandler
    public void onBrewEvent(BrewEvent event) {
        print(event);
    }

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onFurnaceBurnEvent(FurnaceBurnEvent event) {
        print(event);
    }

    @EventHandler
    public void onFurnaceExtractEvent(FurnaceExtractEvent event) {
        print(event);
    }

    @EventHandler
    public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Inventory ci = event.getClickedInventory();
        IronmanSurvival.log(event.getEventName()
                + " a:" + event.getAction() + " c:" + event.getClick()
                + " ci:" + ci.getName() + "/" + ci.getTitle() + "/" + ci.getType()
                + " i:" + event.getCurrentItem() + " cu:" + event.getCursor()
                + " s:" + event.getSlotType() + " hbb:" + event.getHotbarButton());

        if (ci.getType() != InventoryType.PLAYER) {
            return;
        }
        
        int slotId = event.getSlot();
        int hbButton = event.getHotbarButton();

        if ((slotId < 9 && slotId >= 6)
                || (hbButton < 9 && hbButton >= 6)) {
            event.setCancelled(true);
            return;
        }

        IronmanSurvival.log(event.getSlot() + "");

    }

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        IronmanSurvival.log(event.getEventName() + " from " + event.getPreviousSlot() + " to "
                + event.getNewSlot());

        if (event.getNewSlot() >= 6) {
            event.setCancelled(true);
            //((PlayerInventory)event.getPlayer().getInventory()).setHeldItemSlot(0);
        }
    }

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryEvent(InventoryEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        print(event);
    }

    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        print(event);
        //event.getInventory().setResult(new ItemStack(Material.AIR));
        //IronmanSurvival.log(event.getView().getPlayer().getWorld().getName());
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Location from = event.getFrom().add(0, 1, 0);
        Location to = event.getTo().add(0, 1, 0);

        Block fBlock = from.getBlock();
        Block tBlock = to.getBlock();

        if (fBlock.getType() == Material.TORCH) {
            fBlock.removeMetadata(FloatingTorch.FLOATING_TORCH, m_parent);
            fBlock.setType(Material.AIR);
        }

        if (tBlock.getType() != Material.AIR) {
            return;
        }

        tBlock.setMetadata(FloatingTorch.FLOATING_TORCH, new FixedMetadataValue(m_parent, true));
        tBlock.setType(Material.TORCH);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockCanBuildEvent(BlockCanBuildEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockDamageEvent(BlockDamageEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockExpEvent(BlockExpEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {
        print(event);
    }
    /*
     @EventHandler
     public void onBlockFromToEvent(BlockFromToEvent event) {
     print(event);
     }
     */

    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockIgniteEvent(BlockIgniteEvent event) {
        print(event);
    }

    /*@EventHandler
     public void onBlockPhysicsEvent(BlockPhysicsEvent event) {        
     print(event);
     }*/
    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        print(event);
    }

    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
        print(event);
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent event) {
        print(event);
    }

    @EventHandler
    public void onNotePlayEvent(NotePlayEvent event) {
        print(event);
    }

    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onEnchantItemEvent(EnchantItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
        print(event);
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        print(event);
    }

    @EventHandler
    public void onCreeperPowerEvent(CreeperPowerEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityBreakDoorEvent(EntityBreakDoorEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityCombustEvent(EntityCombustEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityInteractEvent(EntityInteractEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityPortalEvent(EntityPortalEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityPortalExitEvent(EntityPortalExitEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityTameEvent(EntityTameEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityTargetEvent(EntityTargetEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityTeleportEvent(EntityTeleportEvent event) {
        print(event);
    }

    @EventHandler
    public void onEntityUnleashEvent(EntityUnleashEvent event) {
        print(event);
    }

    @EventHandler
    public void onExpBottleEvent(ExpBottleEvent event) {
        print(event);
    }

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        print(event);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onHorseJumpEvent(HorseJumpEvent event) {
        print(event);
    }

    @EventHandler
    public void onItemDespawnEvent(ItemDespawnEvent event) {
        print(event);
    }

    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        print(event);
        IronmanSurvival.log("t: " + event.getEntityType() + " m:"
                + event.getEntity().getItemStack().getType());
    }

    @EventHandler
    public void onPigZapEvent(PigZapEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerLeashEntityEvent(PlayerLeashEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent event) {
        print(event);
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        print(event);
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        print(event);
    }

    @EventHandler
    public void onSheepDyeWoolEvent(SheepDyeWoolEvent event) {
        print(event);
    }

    @EventHandler
    public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent event) {
        print(event);
    }

    @EventHandler
    public void onSlimeSplitEvent(SlimeSplitEvent event) {
        print(event);
    }

    @EventHandler
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onHangingBreakEvent(HangingBreakEvent event) {
        print(event);
    }

    @EventHandler
    public void onHangingPlaceEvent(HangingPlaceEvent event) {
        print(event);
    }

    @EventHandler
    public void onPaintingBreakByEntityEvent(PaintingBreakByEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onPaintingBreakEvent(PaintingBreakEvent event) {
        print(event);
    }

    @EventHandler
    public void onPaintingPlaceEvent(PaintingPlaceEvent event) {
        print(event);
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        print(event);
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerAnimationEvent(PlayerAnimationEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerChannelEvent(PlayerChannelEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerEditBookEvent(PlayerEditBookEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        print(event);

        //event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3200, 0, true));
    }

    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerPreLoginEvent(PlayerPreLoginEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerRegisterChannelEvent(PlayerRegisterChannelEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerUnleashEntityEvent(PlayerUnleashEntityEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerUnregisterChannelEvent(PlayerUnregisterChannelEvent event) {
        print(event);
    }

    @EventHandler
    public void onPlayerVelocityEvent(PlayerVelocityEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleBlockCollisionEvent(VehicleBlockCollisionEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleCreateEvent(VehicleCreateEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleDamageEvent(VehicleDamageEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleDestroyEvent(VehicleDestroyEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleEnterEvent(VehicleEnterEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleEntityCollisionEvent(VehicleEntityCollisionEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleExitEvent(VehicleExitEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleMoveEvent(VehicleMoveEvent event) {
        print(event);
    }

    @EventHandler
    public void onVehicleUpdateEvent(VehicleUpdateEvent event) {
        print(event);
    }

    @EventHandler
    public void onLightningStrikeEvent(LightningStrikeEvent event) {
        print(event);
    }

    @EventHandler
    public void onThunderChangeEvent(ThunderChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        print(event);
    }

    /*
     @EventHandler
     public void onChunkLoadEvent(ChunkLoadEvent event) {
     print(event);
     }
     */
    @EventHandler
    public void onChunkPopulateEvent(ChunkPopulateEvent event) {
        print(event);
    }

    /*
     @EventHandler
     public void onChunkUnloadEvent(ChunkUnloadEvent event) {
     print(event);
     }
     */
    @EventHandler
    public void onPortalCreateEvent(PortalCreateEvent event) {
        print(event);
    }

    @EventHandler
    public void onSpawnChangeEvent(SpawnChangeEvent event) {
        print(event);
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event) {
        print(event);
    }

    @EventHandler
    public void onWorldInitEvent(WorldInitEvent event) {
        print(event);
    }

    @EventHandler
    public void onWorldLoadEvent(WorldLoadEvent event) {
        print(event);
    }

    @EventHandler
    public void onWorldSaveEvent(WorldSaveEvent event) {
        print(event);
    }

    @EventHandler
    public void onWorldUnloadEvent(WorldUnloadEvent event) {
        print(event);
    }
}
