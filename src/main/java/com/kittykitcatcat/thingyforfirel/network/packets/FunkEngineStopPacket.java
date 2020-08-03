package com.kittykitcatcat.thingyforfirel.network.packets;

import com.kittykitcatcat.thingyforfirel.funkengine.FunkEngineTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.kittykitcatcat.thingyforfirel.ClientStuff.funkEngineStopPacketFunction;

public class FunkEngineStopPacket
{
    private double x, y, z;

    public FunkEngineStopPacket(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                {
                    BlockPos pos = new BlockPos(x, y, z);
                    funkEngineStopPacketFunction(pos);
                }));
        context.get().setPacketHandled(true);
    }

    public static FunkEngineStopPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new FunkEngineStopPacket(x, y, z);
    }
}