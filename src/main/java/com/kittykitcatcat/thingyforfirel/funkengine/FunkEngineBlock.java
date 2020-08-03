package com.kittykitcatcat.thingyforfirel.funkengine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HAS_RECORD;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FunkEngineBlock extends Block
{
    public FunkEngineBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(HAS_RECORD, false));
    }
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new FunkEngineTileEntity();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(HAS_RECORD);
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos)
    {
        return state.get(HAS_RECORD) ? 12 : 2;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            if (worldIn.getTileEntity(pos) instanceof FunkEngineTileEntity)
            {
                FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) worldIn.getTileEntity(pos);
                if (funkEngineTileEntity.inventory.getStackInSlot(0) != ItemStack.EMPTY)
                {
                    funkEngineTileEntity.stopSound();
                    Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, funkEngineTileEntity.inventory.getStackInSlot(0).copy());
                    worldIn.addEntity(entity);
                }
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof FunkEngineTileEntity)
                {
                    FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) worldIn.getTileEntity(pos);
                    ItemStack stack = player.getHeldItemMainhand();
                    boolean value = funkyItemTEHandling(player, handIn, stack, funkEngineTileEntity, 0);
                    BlockState newState = state.with(HAS_RECORD, value);
                    worldIn.setBlockState(pos, newState);
                    player.world.notifyBlockUpdate(pos, state, newState, 3);
                    player.swingArm(handIn);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean funkyItemTEHandling(PlayerEntity player, Hand hand, ItemStack heldItem, FunkEngineTileEntity funkEngineTileEntity, int slot)
    {
        ItemStackHandler inventory = funkEngineTileEntity.inventory;
        ItemStack targetItem = inventory.getStackInSlot(slot);
        if (targetItem.isEmpty())
        {
            if (heldItem.getItem() instanceof MusicDiscItem)
            {
                inventory.setStackInSlot(0,heldItem);
                player.setHeldItem(hand, ItemStack.EMPTY);
                return true;
            }
        }
        else
        {
            funkEngineTileEntity.stopSound();
            ItemHandlerHelper.giveItemToPlayer(player, targetItem);
            inventory.setStackInSlot(0,ItemStack.EMPTY);
            return false;
        }
        return false;
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_RECORD, false);
    }

}