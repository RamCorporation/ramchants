package net.ramgames.ramchants.mixins;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEnchantmentsComponent.Builder.class)
public class BuilderMixin {

    @Shadow @Final private Object2IntOpenHashMap<RegistryEntry<Enchantment>> enchantments;

    @Inject(method = "add", at = @At(value = "HEAD"), cancellable = true)
    private void increaseLevelIfPresent(RegistryEntry<Enchantment> enchantment, int level, CallbackInfo ci) {
        if(this.enchantments.containsKey(enchantment)) {
            this.enchantments.put(enchantment, Math.min(enchantment.value().getMaxLevel(), this.enchantments.getInt(enchantment) + level));
            ci.cancel();
        }
    }

}
