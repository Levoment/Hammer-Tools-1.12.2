package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.init.ModItems;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

// Register this class in the event bus
@Mod.EventBusSubscriber
public class ItemHammer extends ItemPickaxe {

    protected ItemHammer(ToolMaterial material) {
        super(material);
        MinecraftForge.EVENT_BUS.register(ItemHammer.class);
        ModItems.ITEMS.add(this);
    }

    private static EnumFacing sideHit = null;

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent.LeftClickBlock event) {
        // Get the world
        World worldIn = event.getWorld();
        if (!worldIn.isRemote) {
            // Store in a variable the face that was hit
            sideHit = event.getFace();
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        // Check that the action gets executed in the logical server side
        if (!worldIn.isRemote) {
            // Cast the entity living into a player
            EntityPlayer player = (EntityPlayer) entityLiving;
            // The player must not be sneaking, the block must be a full block, must not have tile entities, and a side must have been hit
            if (!player.isSneaking() && worldIn.getBlockState(pos).isFullBlock() && !worldIn.getBlockState(pos).getBlock().hasTileEntity(state) && sideHit != null) {

                // Variable containing the face of the block that was hit
                EnumFacing facing = this.sideHit;
                // The block was hit from a side
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {

                    // Blocks to break
                    BlockPos blockToBreakUp = pos.offset(EnumFacing.UP);
                    BlockPos blockToBreakDown = pos.offset(EnumFacing.DOWN);
                    BlockPos blockToBreakNorth = pos.offset(EnumFacing.NORTH);
                    BlockPos blockToBreakSouth = pos.offset(EnumFacing.SOUTH);
                    BlockPos blockToBreakNorthUp = pos.offset(EnumFacing.NORTH).offset(EnumFacing.UP);
                    BlockPos blockToBreakNorthDown = pos.offset(EnumFacing.NORTH).offset(EnumFacing.DOWN);
                    BlockPos blockToBreakSouthUp = pos.offset(EnumFacing.SOUTH).offset(EnumFacing.UP);
                    BlockPos blockToBreakSouthDown = pos.offset(EnumFacing.SOUTH).offset(EnumFacing.DOWN);
                    List<BlockPos> listOfBlocksToBreak = new ArrayList<>();
                    listOfBlocksToBreak.add(blockToBreakUp);
                    listOfBlocksToBreak.add(blockToBreakDown);
                    listOfBlocksToBreak.add(blockToBreakNorth);
                    listOfBlocksToBreak.add(blockToBreakSouth);
                    listOfBlocksToBreak.add(blockToBreakNorthUp);
                    listOfBlocksToBreak.add(blockToBreakNorthDown);
                    listOfBlocksToBreak.add(blockToBreakSouthUp);
                    listOfBlocksToBreak.add(blockToBreakSouthDown);

                    // Iterate through the blocks to break and check block by block if the block to break exist, the player can edit the block, and the block has a harvest tool
                    for (BlockPos blockToBreak : listOfBlocksToBreak) {

                        if (blockToBreak != null
                                && player.canPlayerEdit(blockToBreak, facing, stack)
                                && worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()) != null) {
                            // Check if this this tool can be used to harvest the block
                            if (this.getToolClasses(stack).toString().toLowerCase().contains(worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()).toLowerCase())) {
                                // Check if the tool has the level to harvest the block
                                if (this.toolMaterial.getHarvestLevel() >= worldIn.getBlockState(blockToBreak).getBlock().getHarvestLevel(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState())) {
                                    // Harvest the block
                                    worldIn.getBlockState(blockToBreak).getBlock().harvestBlock(worldIn, player, blockToBreak, worldIn.getBlockState(blockToBreak), null, stack);
                                    // Destroy the block
                                    worldIn.destroyBlock(blockToBreak, false);
                                }
                            }
                        }
                    }
                }

                // The block was hit from a side
                if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                    // Blocks to break
                    BlockPos blockToBreakUp = pos.offset(EnumFacing.UP);
                    BlockPos blockToBreakDown = pos.offset(EnumFacing.DOWN);
                    BlockPos blockToBreakEast = pos.offset(EnumFacing.EAST);
                    BlockPos blockToBreakWest = pos.offset(EnumFacing.WEST);
                    BlockPos blockToBreakEastUp = pos.offset(EnumFacing.EAST).offset(EnumFacing.UP);
                    BlockPos blockToBreakEastDown = pos.offset(EnumFacing.EAST).offset(EnumFacing.DOWN);
                    BlockPos blockToBreakWestUp = pos.offset(EnumFacing.WEST).offset(EnumFacing.UP);
                    BlockPos blockToBreakWestDown = pos.offset(EnumFacing.WEST).offset(EnumFacing.DOWN);
                    List<BlockPos> listOfBlocksToBreak = new ArrayList<>();
                    listOfBlocksToBreak.add(blockToBreakUp);
                    listOfBlocksToBreak.add(blockToBreakDown);
                    listOfBlocksToBreak.add(blockToBreakEast);
                    listOfBlocksToBreak.add(blockToBreakWest);
                    listOfBlocksToBreak.add(blockToBreakEastUp);
                    listOfBlocksToBreak.add(blockToBreakEastDown);
                    listOfBlocksToBreak.add(blockToBreakWestUp);
                    listOfBlocksToBreak.add(blockToBreakWestDown);

                    // Iterate through the blocks to break and check block by block if the block to break exist, the player can edit the block, and the block has a harvest tool
                    for (BlockPos blockToBreak : listOfBlocksToBreak) {

                        if (blockToBreak != null
                                && player.canPlayerEdit(blockToBreak, facing, stack)
                                && worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()) != null) {
                            // Check if this this tool can be used to harvest the block
                            if (this.getToolClasses(stack).toString().toLowerCase().contains(worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()).toLowerCase())) {
                                // Check if the tool has the level to harvest the block
                                if (this.toolMaterial.getHarvestLevel() >= worldIn.getBlockState(blockToBreak).getBlock().getHarvestLevel(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState())) {
                                    // Harvest the block
                                    worldIn.getBlockState(blockToBreak).getBlock().harvestBlock(worldIn, player, blockToBreak, worldIn.getBlockState(blockToBreak), null, stack);
                                    // Destroy the block
                                    worldIn.destroyBlock(blockToBreak, false);
                                }
                            }
                        }
                    }

                }

                // If the block was hit on the upside or the downside
                if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                    // Blocks to break
                    BlockPos blockToBreakNorth = pos.offset(EnumFacing.NORTH);
                    BlockPos blockToBreakSouth = pos.offset(EnumFacing.SOUTH);
                    BlockPos blockToBreakEast = pos.offset(EnumFacing.EAST);
                    BlockPos blockToBreakWest = pos.offset(EnumFacing.WEST);
                    BlockPos blockToBreakNorthEast = pos.offset(EnumFacing.NORTH).offset(EnumFacing.EAST);
                    BlockPos blockToBreakNorthWest = pos.offset(EnumFacing.NORTH).offset(EnumFacing.WEST);
                    BlockPos blockToBreakSouthEast = pos.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST);
                    BlockPos blockToBreakSouthWest = pos.offset(EnumFacing.SOUTH).offset(EnumFacing.WEST);
                    List<BlockPos> listOfBlocksToBreak = new ArrayList<>();
                    listOfBlocksToBreak.add(blockToBreakNorth);
                    listOfBlocksToBreak.add(blockToBreakSouth);
                    listOfBlocksToBreak.add(blockToBreakEast);
                    listOfBlocksToBreak.add(blockToBreakWest);
                    listOfBlocksToBreak.add(blockToBreakNorthEast);
                    listOfBlocksToBreak.add(blockToBreakNorthWest);
                    listOfBlocksToBreak.add(blockToBreakSouthEast);
                    listOfBlocksToBreak.add(blockToBreakSouthWest);


                    // Iterate through the blocks to break and check block by block if the block to break exist, the player can edit the block, and the block has a harvest tool
                    for (BlockPos blockToBreak : listOfBlocksToBreak) {

                        if (blockToBreak != null
                                && player.canPlayerEdit(blockToBreak, facing, stack)
                                && worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()) != null) {
                            // Check if this this tool can be used to harvest the block
                            if (this.getToolClasses(stack).toString().toLowerCase().contains(worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()).toLowerCase())) {
                                // Check if the tool has the level to harvest the block
                                if (this.toolMaterial.getHarvestLevel() >= worldIn.getBlockState(blockToBreak).getBlock().getHarvestLevel(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState())) {
                                    // Harvest the block
                                    worldIn.getBlockState(blockToBreak).getBlock().harvestBlock(worldIn, player, blockToBreak, worldIn.getBlockState(blockToBreak), null, stack);
                                    // Destroy the block
                                    worldIn.destroyBlock(blockToBreak, false);
                                }
                            }
                        }
                    }

                }
            }
        }
        // Call and return super that calculates the damage and apply everything to the tool
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Check that the action gets executed in the logical server side
        if (!worldIn.isRemote) {
            // Check if the player has torches in the inventory
            if (player.inventory.hasItemStack(new ItemStack(Blocks.TORCH))) {

                // Store the slot number that contains the torches
                int torchSlot = player.inventory.getSlotFor(new ItemStack(Blocks.TORCH));
                // Get the ItemStack of the torches in a variable
                ItemStack itemStackOfTorches = player.inventory.getStackInSlot(torchSlot);

                // Check if player can edit the respective block with torches
                if (player.canPlayerEdit(pos, facing, itemStackOfTorches)) {

                    // Get into a variable the block to edit
                    IBlockState blockToEdit = worldIn.getBlockState(pos);
                    // Variable to check whether a torch was placed or not
                    boolean placed = false;
                    // Variable to check whether a torch can be placed or not
                    boolean canPlaceTorch = true;

                    // Check that the block is a full block and it doesn't have any tile entity
                    if ((!blockToEdit.isFullBlock() || blockToEdit.getBlock().hasTileEntity(blockToEdit))) {
                        canPlaceTorch = false;
                    }
                    // Check if the player is not trying to put a torch on the down side of a block
                    if (facing == EnumFacing.DOWN) {
                        canPlaceTorch = false;
                    }

                    // Otherwise, check if the block is a full block, you can place a torch on top, and the facing is up
                    if (!blockToEdit.isFullBlock() && blockToEdit.getBlock().canPlaceTorchOnTop(blockToEdit, worldIn, pos) && facing != EnumFacing.UP) {
                        canPlaceTorch = false;
                    }

                    if (!blockToEdit.isFullBlock() && blockToEdit.getBlock().canPlaceTorchOnTop(blockToEdit, worldIn, pos) && facing == EnumFacing.UP) {
                        canPlaceTorch = true;
                    }

                    // If you can place a torch after passing through all of the filters above
                    if (canPlaceTorch) {
                        // Place the torch and store in the 'placed' variable whether the torch was successfully placed
                        placed = worldIn.setBlockState(pos.offset(facing), Blocks.TORCH.getBlockState().getBaseState().withProperty(BlockTorch.FACING, facing), 2);
                        // Check if the torch was placed
                        if (placed) {
                            // Play the sound of a torch when placed
                            SoundType soundType = Blocks.TORCH.getBlockState().getBlock().getSoundType(Blocks.TORCH.getBlockState().getBaseState(), worldIn, pos, player);
                            worldIn.playSound(null, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, 1.0f, 0.8F);
                            // Remove a torch from the inventory
                            itemStackOfTorches.setCount(itemStackOfTorches.getCount() - 1);
                            return EnumActionResult.SUCCESS;
                        }
                    }
                    // Torch not placed, return EnumActionResult.FAIL
                    return EnumActionResult.FAIL;
                }
                // Player cannot edit block, return EnumActionResult.FAIL
                return EnumActionResult.FAIL;
            } else {
                // Player has no torches in inventory, return EnumActionResult.FAIL
                return EnumActionResult.FAIL;
            }
        }
        // World is not in logical server, return EnumActionResult.FAIL
        return EnumActionResult.FAIL;
    }

    public void registerRenders() {

    }

}
