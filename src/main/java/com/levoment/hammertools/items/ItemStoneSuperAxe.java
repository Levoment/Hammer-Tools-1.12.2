package com.levoment.hammertools.items;

import com.levoment.hammertools.References;

public class ItemStoneSuperAxe extends ItemSuperAxe {

    public ItemStoneSuperAxe(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        this.setUnlocalizedName(References.HammerToolsItems.STONESUPERAXE.toString());
        this.setRegistryName(References.HammerToolsItems.STONESUPERAXE.toString());

        // Set the axe cutting speed to half of a stone axe
        this.efficiency = ToolMaterial.STONE.getEfficiency() / 2.0f;
    }
}
