package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public class BacklashEnchantment extends Enchantment {
    protected BacklashEnchantment() {
        super(Enchantments.THORNS.getRarity(), Enchantments.THORNS.target, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return Enchantments.THORNS.getMaxLevel();
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.THORNS) return false;
        return super.canAccept(other);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        Random random = user.getRandom();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.THORNS, user);
        if (ThornsEnchantment.shouldDamageAttacker(level, random)) {
            user.damage(user.getDamageSources().thorns(user), (float)ThornsEnchantment.getDamageAmount(level, random));
            user.getWorld().tickEntity((entity) -> entity.playSoundIfNotSilent(SoundEvents.ENCHANT_THORNS_HIT), user);
            if (entry != null) entry.getValue().damage(2, user, (entity) -> entity.sendEquipmentBreakStatus(entry.getKey()));
        }
    }
}
