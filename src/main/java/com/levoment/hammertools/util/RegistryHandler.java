package com.levoment.hammertools.util;

import com.levoment.hammertools.init.ModItems;
import com.levoment.hammertools.items.ItemHammer;
import com.levoment.hammertools.items.ItemSuperAxe;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registryEvent) {
        for (Item item : ModItems.ITEMS) {
            registryEvent.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void onModelRegister (ModelRegistryEvent modelRegistryEvent){
        for (Item item : ModItems.ITEMS) {
            if (item instanceof ItemHammer) {
                ((ItemHammer)item).registerRenders();
            } else if (item instanceof ItemSuperAxe) {
                ((ItemSuperAxe)item).registerRenders();
            }
        }
    }
}
