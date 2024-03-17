package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractLinkedCurseEnchantment extends Enchantment {

    private final Enchantment linkedEnchantment;

    protected AbstractLinkedCurseEnchantment(Enchantment enchantment, EquipmentSlot[] slotTypes) {
        super(enchantment.getRarity(), enchantment.target, slotTypes);
        this.linkedEnchantment = enchantment;
    }

    protected AbstractLinkedCurseEnchantment(Enchantment enchantment, Rarity rarity, EquipmentSlot[] slotTypes) {
        super(rarity, enchantment.target, slotTypes);
        this.linkedEnchantment = enchantment;
    }

    protected AbstractLinkedCurseEnchantment(Enchantment enchantment, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(enchantment.getRarity(), target, slotTypes);
        this.linkedEnchantment = enchantment;
    }

    public Enchantment getLinkedEnchantment() {
        return linkedEnchantment;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        for(Enchantment curse : cannotCombine()) {
            if (other == curse) return false;
            if((curse instanceof AbstractLinkedCurseEnchantment curseEnchantment) && curseEnchantment.linkedEnchantment == other) return false;
        }
        if(EnchantmentHelper.getEnchantmentId(other) == EnchantmentHelper.getEnchantmentId(linkedEnchantment)) return false;
        return linkedEnchantment.canAccept(other);
    }

    @Override
    public int getMaxLevel() {
        return linkedEnchantment.getMaxLevel();
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    public Set<Enchantment> cannotCombine() {
        return Collections.emptySet();
    }
}
