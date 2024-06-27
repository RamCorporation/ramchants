package net.ramgames.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.ramgames.ramchants.RamChantUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {

    @WrapOperation(method = "grind", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;apply(Lnet/minecraft/item/ItemStack;Ljava/util/function/Consumer;)Lnet/minecraft/component/type/ItemEnchantmentsComponent;"))
    private ItemEnchantmentsComponent allowCurseRemoval(ItemStack stack, Consumer<ItemEnchantmentsComponent.Builder> applier, Operation<ItemEnchantmentsComponent> original) {
        boolean val = !(RamChantUtils.isStackSealed(stack) && (RamChantUtils.getRemainingEnchantability(stack)-1 > 0));
        RamChantUtils.incrementGrinds(stack);
        return EnchantmentHelper.apply(stack, components -> components.remove(enchantment -> val));
    }

}
