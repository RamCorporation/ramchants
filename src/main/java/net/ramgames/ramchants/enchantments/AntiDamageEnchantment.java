package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class AntiDamageEnchantment extends Enchantment {

    private final int typeIndex;

    protected AntiDamageEnchantment(Rarity rarity, int typeIndex) {
        super(rarity, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.typeIndex = typeIndex;
    }

    public int getMaxLevel() {
        return 5;
    }



    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if (this.typeIndex == 0) {
            return -(1.0F + (float)Math.max(0, level - 1) * 0.5F);
        } else if (this.typeIndex == 1 && group == EntityGroup.UNDEAD) {
            return -(float)level * 2.5F;
        } else {
            return this.typeIndex == 2 && group == EntityGroup.ARTHROPOD ? -(float)level * 2.5F : 0.0F;
        }
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.PROTECTION || other == Enchantments.BLAST_PROTECTION || other == Enchantments.SMITE) return false;
        switch(typeIndex) {
            case 0 -> {
                if (other == ModEnchantments.ARTHROPODS_FAVOR || other == ModEnchantments.WRAITHWARD) return false;
            }
            case 1 -> {
                if (other == ModEnchantments.ARTHROPODS_FAVOR || other == ModEnchantments.DULLNESS) return false;
            }
            case 2 -> {
                if (other == ModEnchantments.WRAITHWARD || other == ModEnchantments.DULLNESS) return false;
            }
        }
        return super.canAccept(other);
    }
}
