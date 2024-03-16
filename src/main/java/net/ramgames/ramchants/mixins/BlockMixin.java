package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChants;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "afterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void allowVoidingToVoid(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
        if(EnchantmentHelper.getLevel(RamChantments.VOIDING, tool) > 0) ci.cancel();
    }

    @ModifyArg(
            method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"))
    private static Consumer<ItemStack> applyDestitution(Consumer<ItemStack> par1, @Local(argsOnly = true) Entity entity, @Local(argsOnly = true) World world) {
        if(entity == null) return par1;
        if(!(entity instanceof LivingEntity livingEntity)) return par1;
        int level = EnchantmentHelper.getEquipmentLevel(RamChantments.DESTITUTION, livingEntity);
        if(level <= 0) return par1;
        if(world.getRandom().nextBetween(1, 100) >= 10 + 15 * (level-1)) return par1;
        return (stack) -> RamChants.LOGGER.info("cancelled: "+stack);
    }
}
