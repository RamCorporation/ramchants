package net.ramgames.ramchants.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    public boolean removeNumberFromEnchantmentPreview(List<Text> instance, Object e, Operation<Boolean> original, @Local Optional<RegistryEntry.Reference<Enchantment>> optional) {
        Enchantment enchantment = optional.orElseThrow().value();
        MutableText text = Text.translatable("container.enchant.clue", enchantment.description());
        if(MinecraftClient.getInstance().world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(enchantment).isIn(EnchantmentTags.CURSE))
            text.formatted(Formatting.RED);
        else text.formatted(Formatting.GRAY);
        return instance.add(text);
    }
}
