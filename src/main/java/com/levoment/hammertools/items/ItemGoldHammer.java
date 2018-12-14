package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.References;

public class ItemGoldHammer extends ItemHammer {

    public ItemGoldHammer(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        setUnlocalizedName(References.HammerToolsItems.GOLDHAMMER.toString());
        setRegistryName(References.HammerToolsItems.GOLDHAMMER.toString());
        // Set the hammer mining speed to half of a gold pickaxe
        this.efficiency = material.getEfficiency() / 2.0f;
    }

    @Override
    public void registerRenders() {
        HammerTools.proxy.registerItemRenderer(this, 0);
    }
}
