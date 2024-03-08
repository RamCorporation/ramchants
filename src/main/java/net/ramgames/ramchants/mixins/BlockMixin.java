package net.ramgames.ramchants.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {



    @Inject(method = "afterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void allowVoidingToVoid(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
        if(EnchantmentHelper.getLevel(ModEnchantments.VOIDING, tool) > 0) ci.cancel();

    }

}
