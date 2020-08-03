package com.kittykitcatcat.thingyforfirel.init;

import com.kittykitcatcat.thingyforfirel.funkengine.FunkEngineTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities
{

    @ObjectHolder("thingyforfirel:funk_engine_tile_entity")
    public static TileEntityType<FunkEngineTileEntity> funk_engine_tile_entity;


    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
                TileEntityType.Builder.create((Supplier<TileEntity>) FunkEngineTileEntity::new, ModBlocks.funk_engine).build(null).setRegistryName("funk_engine_tile_entity")
        );
    }
}
