package net.ramgames.ramchants;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.ramgames.ramchants.enchantments.AbstractLinkedCurseEnchantment;
import net.ramgames.ramchants.enchantments.ModEnchantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RamChants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final HashMap<Enchantment, AbstractLinkedCurseEnchantment> curseLinkMap = new HashMap<>();

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

    public static void registerLinkedCurse(AbstractLinkedCurseEnchantment enchantment) {
        curseLinkMap.put(enchantment.getLinkedEnchantment(), enchantment);
    }

    public static boolean hasLinkedCurseFor(Enchantment enchantment) {
        return curseLinkMap.containsKey(enchantment);
    }

    public static AbstractLinkedCurseEnchantment getLinkedCurseFor(Enchantment enchantment) {
        return curseLinkMap.get(enchantment);
    }
}
