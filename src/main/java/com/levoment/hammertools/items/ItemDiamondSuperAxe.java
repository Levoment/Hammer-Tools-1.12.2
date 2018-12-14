package com.levoment.hammertools.items;

import com.levoment.hammertools.References;

public class ItemDiamondSuperAxe extends ItemSuperAxe {

    public ItemDiamondSuperAxe(ToolMaterial material) {
        super(material);
        // Set the unlocalized and registry names
        this.setUnlocalizedName(References.HammerToolsItems.DIAMONDSUPERAXE.toString());
        this.setRegistryName(References.HammerToolsItems.DIAMONDSUPERAXE.toString());

        // Set the axe cutting speed to half of a diamond axe
        this.efficiency = ToolMaterial.DIAMOND.getEfficiency() / 2.0f;
    }
}
