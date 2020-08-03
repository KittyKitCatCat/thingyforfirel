package com.kittykitcatcat.thingyforfirel.init;

import com.google.common.base.Preconditions;
import com.kittykitcatcat.thingyforfirel.funkengine.MusicDiscItemTHing;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static com.kittykitcatcat.thingyforfirel.ThingyForFirel.MODID;
import static com.kittykitcatcat.thingyforfirel.init.ModSounds.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
    public static Item funk_engine;

    public static Item music_disc_redstone_pulse;
    public static Item music_disc_netherborne;
    public static Item music_disc_skeletons_in_the_night;

    static Item.Properties basic_properties = new Item.Properties().group(ItemGroup.DECORATIONS).maxStackSize(64);
    static Item.Properties music_disc_properties = new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE);
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                funk_engine = setup(new BlockItem(ModBlocks.funk_engine, basic_properties), "funk_engine"),
                music_disc_redstone_pulse = setup(new MusicDiscItemTHing(1, redstone_pulse,music_disc_properties), "music_disc_redstone_pulse"),
                music_disc_netherborne = setup(new MusicDiscItemTHing(2,netherborne,music_disc_properties), "music_disc_netherborne"),
                music_disc_skeletons_in_the_night = setup(new MusicDiscItemTHing(3,skeletons_in_the_night,music_disc_properties), "music_disc_skeletons_in_the_night")

                );
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name)
    {
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
        return setup(entry, new ResourceLocation(MODID, name));
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
    {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}
