package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.References;

public class ItemStoneHammer extends ItemHammer {

    public ItemStoneHammer(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        setUnlocalizedName(References.HammerToolsItems.STONEHAMMER.toString());
        setRegistryName(References.HammerToolsItems.STONEHAMMER.toString());
        // Set the hammer mining speed to half of a stone pickaxe
        this.efficiency = material.getEfficiency() / 2.0f;
    }

    @Override
    public void registerRenders() {
        HammerTools.proxy.registerItemRenderer(this, 0);
    }
}
