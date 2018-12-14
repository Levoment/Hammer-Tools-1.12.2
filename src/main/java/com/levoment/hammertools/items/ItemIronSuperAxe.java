package com.levoment.hammertools.items;

import com.levoment.hammertools.References;

public class ItemIronSuperAxe extends ItemSuperAxe {

    public ItemIronSuperAxe(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        this.setUnlocalizedName(References.HammerToolsItems.IRONSUPERAXE.toString());
        this.setRegistryName(References.HammerToolsItems.IRONSUPERAXE.toString());

        // Set the axe cutting speed to half of an iron axe
        this.efficiency = ToolMaterial.IRON.getEfficiency() / 2.0f;
    }
}
