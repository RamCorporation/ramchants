package net.ramgames.ramchants.mixins.client;


import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.GrindstoneScreen;
import net.minecraft.client.item.TooltipData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.ramgames.ramchants.RamChants;
import net.ramgames.ramchants.RamChantsItemStackAccess;
import net.ramgames.ramchants.items.tooltip.EnchantabilityToolTipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public abstract class ItemMixin {


    @Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
    private void getTooltipDataMixin(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if(stack.getItem().isEnchantable(stack) || stack.getItem() == Items.ENCHANTED_BOOK) getEnchantabilityToolTip(stack, cir);
    }

    @Unique
    private void getEnchantabilityToolTip(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        cir.setReturnValue(Optional.of(new EnchantabilityToolTipData(
                ((RamChantsItemStackAccess)(FabricItemStack)stack).ramChants$enchantabilityWithGrinds(),
                RamChants.totalEnchantmentsUsed(EnchantmentHelper.get(stack)),
                MinecraftClient.getInstance().currentScreen instanceof EnchantmentScreen || MinecraftClient.getInstance().currentScreen instanceof GrindstoneScreen,
                ((RamChantsItemStackAccess)(FabricItemStack)stack).ramChants$isSealed()
        )));
    }

    @Inject(method = "getEnchantability", at = @At("RETURN"), cancellable = true)
    public void getEnchantability(CallbackInfoReturnable<Integer> cir) {
        Item item = (Item) (Object) this;
        if(item instanceof EnchantedBookItem ||
            item instanceof ShieldItem ||
            item instanceof BookItem ||
            item instanceof BrushItem ||
            item instanceof ShearsItem ||
            item instanceof FlintAndSteelItem) cir.setReturnValue(5);
        else if(item instanceof RangedWeaponItem ||
            item instanceof TridentItem) cir.setReturnValue(8);
    }
}
