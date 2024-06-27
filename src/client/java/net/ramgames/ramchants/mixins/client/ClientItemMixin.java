package net.ramgames.ramchants.mixins.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.client.gui.screen.ingame.GrindstoneScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipData;
import net.ramgames.ramchants.GrindstoneScreenDuck;
import net.ramgames.ramchants.RamChantUtils;
import net.ramgames.ramchants.items.tooltip.EnchantabilityToolTipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Item.class)
public class ClientItemMixin {


    @Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
    private void getTooltipDataMixin(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if(stack.getItem().isEnchantable(stack) || stack.getItem() == Items.ENCHANTED_BOOK) getEnchantabilityToolTip(stack, cir);
    }

    @Unique
    private void getEnchantabilityToolTip(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        cir.setReturnValue(Optional.of(new EnchantabilityToolTipData(
                RamChantUtils.getEnchantabilityWithGrinds(stack),
                RamChantUtils.getUsedEnchantability(stack),
                MinecraftClient.getInstance().options.advancedItemTooltips || MinecraftClient.getInstance().currentScreen instanceof EnchantmentScreen || MinecraftClient.getInstance().currentScreen instanceof GrindstoneScreen,
                RamChantUtils.isStackSealed(stack),
                (MinecraftClient.getInstance().currentScreen instanceof GrindstoneScreen screen) && ItemStack.areItemsAndComponentsEqual(((GrindstoneScreenDuck) screen).ramchants$getStack(2), stack) && !ItemStack.areItemsAndComponentsEqual(((GrindstoneScreenDuck) screen).ramchants$getStack(1), stack) && !ItemStack.areItemsAndComponentsEqual(((GrindstoneScreenDuck) screen).ramchants$getStack(0), stack)
        )));
    }

}
