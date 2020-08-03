package com.kittykitcatcat.thingyforfirel;

import com.kittykitcatcat.thingyforfirel.funkengine.FunkEngineTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientStuff
{
    public static void FunkEngineTick(FunkEngineTileEntity tileEntity)
    {
        if (tileEntity.getWorld().isRemote())
        {
            if (tileEntity.inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
            {
                SimpleSound sound = getSound(tileEntity);
                if (sound != null)
                {
                    if (!Minecraft.getInstance().getSoundHandler().isPlaying(sound))
                    {
                        Minecraft.getInstance().getSoundHandler().play(sound);
                    }
                }
            }
        }
    }

    public static SimpleSound getSound(FunkEngineTileEntity tileEntity)
    {
        ItemStack stack = tileEntity.inventory.getStackInSlot(0);
        if (stack.getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem item = (MusicDiscItem) stack.getItem();
            if (tileEntity.sound == null)
            {
                tileEntity.sound = SimpleSound.record(item.getSound(), tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
            }
            return (SimpleSound) tileEntity.sound;
        }
        else
        {
            tileEntity.sound = null;
        }
        return null;
    }
    public static void funkEngineStopPacketFunction(BlockPos pos)
    {
        World world = Minecraft.getInstance().world;
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof FunkEngineTileEntity)
        {
            FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) tileEntity;
            SimpleSound sound = (SimpleSound) funkEngineTileEntity.sound;
            if (sound != null)
            {
                if (Minecraft.getInstance().getSoundHandler().isPlaying(sound))
                {
                    Minecraft.getInstance().getSoundHandler().stop(sound);
                }
            }
            funkEngineTileEntity.sound = null;
        }
    }
}
