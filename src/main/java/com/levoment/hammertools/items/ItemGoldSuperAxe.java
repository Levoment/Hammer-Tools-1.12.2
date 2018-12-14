package com.levoment.hammertools.items;

import com.levoment.hammertools.References;

public class ItemGoldSuperAxe extends ItemSuperAxe {

    public ItemGoldSuperAxe(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        this.setUnlocalizedName(References.HammerToolsItems.GOLDSUPERAXE.toString());
        this.setRegistryName(References.HammerToolsItems.GOLDSUPERAXE.toString());

        // Set the axe cutting speed to half of a gold axe
        this.efficiency = ToolMaterial.GOLD.getEfficiency() / 2.0f;
    }
}
