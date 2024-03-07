package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.ramgames.ramchants.RamChants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {

    @Shadow public abstract Item getItem();

    @Shadow public abstract NbtList getEnchantments();

    @Inject(method = "isEnchantable", at = @At("RETURN"), cancellable = true)
    public void ramChants$isEnchantable(CallbackInfoReturnable<Boolean> cir) {
        if(!this.getItem().isEnchantable((ItemStack) (Object) this)) cir.setReturnValue(false);
        else if(this.getEnchantments().isEmpty()) cir.setReturnValue(true);
        else cir.setReturnValue(RamChants.totalEnchantmentsUsed(EnchantmentHelper.get((ItemStack) (Object) this)) < this.getItem().getEnchantability());
    }

    @Inject(method = "addEnchantment", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtList;add(Ljava/lang/Object;)Z", shift = At.Shift.BEFORE), cancellable = true)
    public void ramChants$addEnchantment(Enchantment enchantment, int level, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        int subTotal = RamChants.totalEnchantmentsUsed(EnchantmentHelper.get(stack));
        int maxLevel = this.getItem().getEnchantability() - subTotal;
        NbtList enchants = stack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantmentNbt(stack) : this.getEnchantments();
        for(int i = 0; i < enchants.copy().size(); i++) {
            NbtCompound compound = enchants.getCompound(i);
            if(compound.getString("id").equals(EnchantmentHelper.getEnchantmentId(enchantment).toString())) {
                int proposedLevel = compound.getInt("lvl") + level;
                enchants.set(i, EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), (byte) (Math.min(proposedLevel, maxLevel+compound.getInt("lvl")))));
                ci.cancel();
                return;
            }
        }
        int newLevel = Math.min(maxLevel, level);
        if(newLevel != 0) enchants.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), (byte) newLevel));
        ci.cancel();
    }



}
