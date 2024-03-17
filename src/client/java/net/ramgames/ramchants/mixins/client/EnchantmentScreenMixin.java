package net.ramgames.ramchants.mixins.client;

import net.minecraft.client.gui.screen.ingame.EnchantmentScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getName(I)Lnet/minecraft/text/Text;"))
    public Text ramChants$setPreviewLevel(Enchantment instance, int level) {
        MutableText mutableText = Text.translatable(instance.getTranslationKey());
        if (instance.isCursed()) {
            mutableText.formatted(Formatting.RED);
        } else {
            mutableText.formatted(Formatting.GRAY);
        }
        return mutableText;
    }
}
