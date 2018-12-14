package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.References;

public class ItemIronHammer extends ItemHammer {

    public ItemIronHammer(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        setUnlocalizedName(References.HammerToolsItems.IRONHAMMER.toString());
        setRegistryName(References.HammerToolsItems.IRONHAMMER.toString());
        // Set the hammer mining speed to half of an iron pickaxe
        this.efficiency = material.getEfficiency() / 2.0f;
    }

    @Override
    public void registerRenders() {
        HammerTools.proxy.registerItemRenderer(this, 0);
    }
}
