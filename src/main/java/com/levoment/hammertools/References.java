package com.levoment.hammertools;

public class References {
    public static final String MODID = "levoment_hammertools";
    public static final String NAME = "Hammer Tools";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY_CLASS = "com.levoment.hammertools.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.levoment.hammertools.proxy.ServerProxy";

    public static enum HammerToolsItems {

        // Hammers
        WOODHAMMER("ItemWoodHammer"),
        STONEHAMMER("ItemStoneHammer"),
        IRONHAMMER("ItemIronHammer"),
        GOLDHAMMER("ItemGoldHammer"),
        DIAMONDHAMMER("ItemDiamondHammer"),

        // Super Axes
        WOODSUPERAXE("ItemWoodSuperAxe"),
        STONESUPERAXE("ItemStoneSuperAxe"),
        IRONSUPERAXE("ItemIronSuperAxe"),
        GOLDSUPERAXE("ItemGoldSuperAxe"),
        DIAMONDSUPERAXE("ItemDiamondSuperAxe");

        private final String itemText;

        HammerToolsItems(final String text) {
            this.itemText = text;
        }


        @Override
        public String toString() {
            return this.itemText;
        }
    }
}
