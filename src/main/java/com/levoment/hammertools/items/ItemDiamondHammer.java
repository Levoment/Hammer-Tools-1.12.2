package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.References;

public class ItemDiamondHammer extends ItemHammer {

    public ItemDiamondHammer(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        setUnlocalizedName(References.HammerToolsItems.DIAMONDHAMMER.toString());
        setRegistryName(References.HammerToolsItems.DIAMONDHAMMER.toString());
        // Set the hammer mining speed to half of a diamond pickaxe
        this.efficiency = material.getEfficiency() / 2.0f;
    }

    @Override
    public void registerRenders() {
        HammerTools.proxy.registerItemRenderer(this, 0);
    }
}
