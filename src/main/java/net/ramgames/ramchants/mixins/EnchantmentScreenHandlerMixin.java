package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ramgames.ramchants.RamChantUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Arrays;
import java.util.List;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final public int[] enchantmentPower;

    @Shadow @Final public int[] enchantmentId;

    @Shadow @Final private Random random;

    @Unique private boolean used = false;

    protected EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }


    @ModifyArgs(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;generateEnchantments(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/item/ItemStack;IZ)Ljava/util/List;"))
    private void allowTreasure(Args args) {
        if((int) args.get(2) >= 30) args.set(3, true);
    }

    @ModifyArg(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;generateEnchantments(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/item/ItemStack;IZ)Ljava/util/List;"), index = 1)
    private ItemStack allowEnchantedBookEnchanting(ItemStack stack) {
        if(stack.getItem() == Items.ENCHANTED_BOOK) return new ItemStack(Items.BOOK);
        else return stack;
    }

    @Inject(method = "generateEnchantments", at = @At("RETURN"), cancellable = true)
    private void fixEnchants(ItemStack stack, int slot, int level, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        cir.setReturnValue(RamChantUtils.getFirstCompatible(this.random, EnchantmentHelper.get(stack), cir.getReturnValue()));
    }
    @Inject(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V", shift = At.Shift.AFTER))
    private void applyCurseMaybe(ItemStack itemStack, int id, PlayerEntity playerEntity, int j, ItemStack itemStack2, World world, BlockPos pos, CallbackInfo ci, @Local LocalRef<List<EnchantmentLevelEntry>> list) {
        EnchantmentLevelEntry entry = list.get().get(0);
        EnchantmentLevelEntry newEntry = new EnchantmentLevelEntry(RamChantUtils.determineIfApplyCurse(this.random, entry.enchantment, enchantmentPower[id]), entry.level);
        list.set(List.of(newEntry));
    }
    @Inject(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V", shift = At.Shift.AFTER))
    private void sealEnchantmentsMaybe(ItemStack itemStack, int id, PlayerEntity playerEntity, int j, ItemStack itemStack2, World world, BlockPos pos, CallbackInfo ci) {
        int sealChance = (int) (5 * Math.pow(2, id-1));
        if(this.random.nextBetween(1,100) <= sealChance) RamChantUtils.getStackAccess(itemStack).ramChants$setSealed(true);
    }

    @Inject(method = "onContentChanged", at = @At("TAIL"))
    private void setPowerLevelAndMaybeSeal(Inventory inventory, CallbackInfo ci) {
        for(int i = 0; i < this.enchantmentPower.length; i++) {
            if (this.enchantmentPower[i] > 0 && this.enchantmentId[i] == -1) this.enchantmentPower[i] = 0;
        }
        if(used && Arrays.equals(this.enchantmentPower, new int[]{0,0,0}) && inventory.getStack(0).getItem() != Items.AIR) {
            RamChantUtils.getStackAccess(inventory.getStack(0)).ramChants$setSealed(true);
        }
    }

    @Inject(method = "onButtonClick", at = @At("HEAD"))
    private void determineUsed(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        used = true;
    }
}