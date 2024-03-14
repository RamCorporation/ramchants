package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class GhostQuiverEnchantment extends AbstractLinkedCurseEnchantment {
    protected GhostQuiverEnchantment() {
        super(Enchantments.INFINITY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
