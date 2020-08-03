package com.kittykitcatcat.thingyforfirel.init;

import com.kittykitcatcat.thingyforfirel.ThingyForFirel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ThingyForFirel.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModSounds
{
    public static final SoundEvent redstone_pulse = makeSoundEvent("redstone_pulse");
    public static final SoundEvent netherborne = makeSoundEvent("netherborne");
    public static final SoundEvent skeletons_in_the_night = makeSoundEvent("skeletons_in_the_night");

    private static SoundEvent makeSoundEvent(String name)
    {
        ResourceLocation loc = new ResourceLocation(ThingyForFirel.MODID, name);
        return new SoundEvent(loc).setRegistryName(name);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
        IForgeRegistry<SoundEvent> r = evt.getRegistry();
        r.register(redstone_pulse);
        r.register(netherborne);
        r.register(skeletons_in_the_night);
    }
}
