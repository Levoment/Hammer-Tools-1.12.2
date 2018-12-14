package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.References;

public class ItemWoodHammer extends ItemHammer {

    public ItemWoodHammer(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        setUnlocalizedName(References.HammerToolsItems.WOODHAMMER.toString());
        setRegistryName(References.HammerToolsItems.WOODHAMMER.toString());
        // Set the hammer mining speed to half of a wood pickaxe
        this.efficiency = material.getEfficiency() / 2.0f;
    }

    @Override
    public void registerRenders() {
        HammerTools.proxy.registerItemRenderer(this, 0);
    }
}
