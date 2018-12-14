package com.levoment.hammertools.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item itemToRegister, int meta) {
        ModelLoader.setCustomModelResourceLocation(itemToRegister, 0, new ModelResourceLocation(itemToRegister.getRegistryName(), "inventory"));
    }


}
