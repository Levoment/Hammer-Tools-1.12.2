package com.levoment.hammertools.items;

import com.levoment.hammertools.HammerTools;
import com.levoment.hammertools.init.ModItems;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ItemSuperAxe extends ItemAxe {

    protected ItemSuperAxe(ToolMaterial material) {
        super(material);
        MinecraftForge.EVENT_BUS.register(ItemSuperAxe.class);
        ModItems.ITEMS.add(this);
    }


    // Variable containing the face that was hit
    private static EnumFacing sideHit = null;
    // List buffer of positions of blocks to break
    private List<BlockPos> blocksToBreakListBuffer = new ArrayList<BlockPos>();

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent.LeftClickBlock playerInteractEvent) {
        // Get the world
        World worldIn = playerInteractEvent.getWorld();
        if (!worldIn.isRemote) {
            // Store in a variable the face that was hit
            sideHit = playerInteractEvent.getFace();
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        // Check that the action gets executed in the logical server side
        if (!worldIn.isRemote) {
            // Cast the entity living into a player
            EntityPlayer player = (EntityPlayer)entityLiving;
            // Check that information exists about the side of the block that was hit
            if (this.sideHit != null) {
                // Store in a variable the side that was hit
                EnumFacing facing = this.sideHit;
                // The player must not be sneaking, the block must be a full block, must not have tile entities, and must be a wood block
                if (!player.isSneaking() && worldIn.getBlockState(pos).isFullBlock() && !worldIn.getBlockState(pos).getBlock().hasTileEntity(state) && worldIn.getBlockState(pos).getBlock().isWood(worldIn, pos)) {
                    // Clear the list buffer of blocks to break
                    blocksToBreakListBuffer.clear();
                    // Traverse the tree to get the blocks to break in a buffer (blocksToBreakListBuffer)
                    traverseTree(worldIn, pos);
                    List<BlockPos> blocksToBreak = this.blocksToBreakListBuffer;
                    // Iterate through the list of blocks to break
                    for (BlockPos blockToBreak : blocksToBreak) {
                        // Check if player can edit the block
                        if (player.canPlayerEdit(blockToBreak, facing, stack)) {
                            // Check if the block has a harvest tool defined
                            if (worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()) != null) {
                                // Check if this this tool can be used to harvest the block
                                if (this.getToolClasses(stack).toString().toLowerCase().contains(worldIn.getBlockState(blockToBreak).getBlock().getHarvestTool(worldIn.getBlockState(blockToBreak).getBlock().getBlockState().getBaseState()).toLowerCase())){
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
                    // Empty the list of blocks to break to free memory
                    this.blocksToBreakListBuffer.clear();
                }
            }
        }
        // Call and return super that calculates the damage and apply everything to the tool
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    /*
    Function to traverse through the tree blocks
     */
    public void traverseTree(World worldIn, BlockPos pos) {
        // Stack that will contain the block positions that must be traversed
        Stack<BlockPos> stackOfBlockPositions = new Stack<BlockPos>();
        // Put the root block to initiate the tree traversing
        stackOfBlockPositions.push(pos);
        // Last block position that will be used to continue searching for more blocks
        BlockPos lastBlockPosition = pos;
        // The positions of the blocks to look for
        BlockPos positionToBreakNorth = null;
        BlockPos positionToBreakSouth = null;
        BlockPos positionToBreakEast = null;
        BlockPos positionToBreakWest = null;
        BlockPos positionToBreakUp = null;
        BlockPos positionToBreakDown = null;

        // Loop while there are elements in the stack
        while (!stackOfBlockPositions.isEmpty()) {
            // Set the blocks to look for
            positionToBreakNorth = lastBlockPosition.offset(EnumFacing.NORTH);
            positionToBreakSouth = lastBlockPosition.offset(EnumFacing.SOUTH);
            positionToBreakEast = lastBlockPosition.offset(EnumFacing.EAST);
            positionToBreakWest = lastBlockPosition.offset(EnumFacing.WEST);
            positionToBreakUp = lastBlockPosition.offset(EnumFacing.UP);
            positionToBreakDown = lastBlockPosition.offset(EnumFacing.DOWN);
            // Check that the block position that will be used to search for other blocks is not null
            if (lastBlockPosition != null) {
                // Check that the block at the North of the lastBlockPosition is not null
                if (positionToBreakNorth != null) {
                    // Check that there is an IBlockState in the North offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakNorth) != null && worldIn.getBlockState(positionToBreakNorth).getBlock() != null && worldIn.getBlockState(positionToBreakNorth).getBlock().isWood(worldIn, positionToBreakNorth)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakNorth)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakNorth);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakNorth);
                        }
                    }
                }

                // Check that the block at the South of the lastBlockPosition is not null
                if (positionToBreakSouth != null) {
                    // Check that there is an IBlockState in the South offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakSouth) != null && worldIn.getBlockState(positionToBreakSouth).getBlock() != null && worldIn.getBlockState(positionToBreakSouth).getBlock().isWood(worldIn, positionToBreakSouth)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakSouth)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakSouth);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakSouth);
                        }
                    }
                }

                // Check that the block at the East of the lastBlockPosition is not null
                if (positionToBreakEast != null) {
                    // Check that there is an IBlockState in the East offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakEast) != null && worldIn.getBlockState(positionToBreakEast).getBlock() != null && worldIn.getBlockState(positionToBreakEast).getBlock().isWood(worldIn, positionToBreakEast)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakEast)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakEast);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakEast);
                        }
                    }
                }

                // Check that the block at the West of the lastBlockPosition is not null
                if (positionToBreakWest != null) {
                    // Check that there is an IBlockState in the West offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakWest) != null && worldIn.getBlockState(positionToBreakWest).getBlock() != null && worldIn.getBlockState(positionToBreakWest).getBlock().isWood(worldIn, positionToBreakWest)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakWest)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakWest);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakWest);
                        }
                    }
                }

                // Check that the block at the Up side of the lastBlockPosition is not null
                if (positionToBreakUp != null) {
                    // Check that there is an IBlockState in the Up offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakUp) != null && worldIn.getBlockState(positionToBreakUp).getBlock() != null && worldIn.getBlockState(positionToBreakUp).getBlock().isWood(worldIn, positionToBreakUp)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakUp)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakUp);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakUp);
                        }
                    }
                }

                // Check that the block at the Down side of the lastBlockPosition is not null
                if (positionToBreakDown != null) {
                    // Check that there is an IBlockState in the Down offset, that there is a block in said position and that said block is wood
                    if (worldIn.getBlockState(positionToBreakDown) != null && worldIn.getBlockState(positionToBreakDown).getBlock() != null && worldIn.getBlockState(positionToBreakDown).getBlock().isWood(worldIn, positionToBreakDown)) {
                        // Check the the block has not already been added to the list
                        if (!blocksToBreakListBuffer.contains(positionToBreakDown)) {
                            // Add the block to the list
                            blocksToBreakListBuffer.add(positionToBreakDown);
                            // Add the block to the stack
                            stackOfBlockPositions.push(positionToBreakDown);
                        }
                    }
                }

            }
            // Make the next position to be used to search for other blocks be the one gotten from the top position in the stack
            lastBlockPosition = stackOfBlockPositions.peek();
            // Pop a BlockPos from the stack
            stackOfBlockPositions.pop();
        }
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
                    if (!blockToEdit.isFullBlock() || blockToEdit.getBlock().hasTileEntity(blockToEdit)) {
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
        HammerTools.proxy.registerItemRenderer(this, 0);
    }


}
