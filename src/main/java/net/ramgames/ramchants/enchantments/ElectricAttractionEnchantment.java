package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class ElectricAttractionEnchantment extends AbstractLinkedCurseEnchantment {
    public ElectricAttractionEnchantment() {
        super(Enchantments.CHANNELING, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
