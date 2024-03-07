package net.ramgames.ramchants;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NbtCompound;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RamChants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Ramchants is preparing something magical!");
        ModEnchantments.onInitialize();
    }

    public static int totalEnchantmentsUsed(Map<Enchantment, Integer> enchantments) {
        int cost = 0;
        for(Enchantment enchantment : enchantments.keySet()) cost += enchantments.get(enchantment);
        return cost;
    }
}
