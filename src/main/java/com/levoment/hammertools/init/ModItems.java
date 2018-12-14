package com.levoment.hammertools.init;

import com.levoment.hammertools.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    // Hammers
    public static final Item itemWoodHammer = new ItemWoodHammer(ToolMaterial.WOOD);
    public static final Item itemStoneHammer = new ItemStoneHammer(ToolMaterial.STONE);
    public static final Item itemIronHammer = new ItemIronHammer(ToolMaterial.IRON);
    public static final Item itemGoldHammer = new ItemGoldHammer(ToolMaterial.GOLD);
    public static final Item itemDiamondHammer = new ItemDiamondHammer(ToolMaterial.DIAMOND);

    // Axes
    public static final Item itemWoodSuperAxe = new ItemWoodSuperAxe(ToolMaterial.WOOD);
    public static final Item itemStoneSuperAxe = new ItemStoneSuperAxe(ToolMaterial.STONE);
    public static final Item itemIronSuperAxe = new ItemIronSuperAxe(ToolMaterial.IRON);
    public static final Item itemGoldSuperAxe = new ItemGoldSuperAxe(ToolMaterial.GOLD);
    public static final Item itemDiamondSuperAxe = new ItemDiamondSuperAxe(ToolMaterial.DIAMOND);

}
