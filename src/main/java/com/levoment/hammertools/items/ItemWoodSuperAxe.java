package com.levoment.hammertools.items;

import com.levoment.hammertools.References;

public class ItemWoodSuperAxe extends ItemSuperAxe {

    public ItemWoodSuperAxe(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        this.setUnlocalizedName(References.HammerToolsItems.WOODSUPERAXE.toString());
        this.setRegistryName(References.HammerToolsItems.WOODSUPERAXE.toString());

        // Set the axe cutting speed to half of a wood axe
        this.efficiency = ToolMaterial.WOOD.getEfficiency() / 2.0f;
    }
}
