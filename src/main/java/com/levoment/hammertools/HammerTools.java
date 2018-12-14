package com.levoment.hammertools;

import com.levoment.hammertools.proxy.CommonProxy;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class HammerTools
{

    @Mod.Instance
    public static HammerTools instance;

    @SidedProxy(clientSide = References.CLIENT_PROXY_CLASS, serverSide = References.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    private static Logger logger;

    @EventHandler
    public static void PreInit(FMLPreInitializationEvent fmlPreInitializationEvent){
        OBJLoader.INSTANCE.addDomain(References.MODID);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent fmlPostInitializationEvent){

    }


}
