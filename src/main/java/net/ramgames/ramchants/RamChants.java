package net.ramgames.ramchants;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.enchantments.AbstractLinkedCurseEnchantment;
import net.ramgames.ramchants.enchantments.RamChantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RamChants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final HashMap<Enchantment, Enchantment> curseLinkMap = new HashMap<>();
    private static final HashMap<Identifier, Integer> enchantabilityMap = new HashMap<>();

    @Override
    public void onInitialize() {
        LOGGER.info("Ramchants is preparing something magical!");
        RamChantments.onInitialize();
    }

    public static int totalEnchantmentsUsed(Map<Enchantment, Integer> enchantments) {
        int cost = 0;
        for(Enchantment enchantment : enchantments.keySet()) cost += enchantments.get(enchantment);
        return cost;
    }

    public static void registerLinkedCurse(AbstractLinkedCurseEnchantment... enchantments) {
        for(AbstractLinkedCurseEnchantment enchantment : enchantments) curseLinkMap.put(enchantment.getLinkedEnchantment(), enchantment);
    }

    public static void registerLinkedCurse(Enchantment linkedEnchant, Enchantment curse) {
        curseLinkMap.put(linkedEnchant, curse);
    }

    public static void registerEnchantability(Identifier identifier, int enchantability) {
        enchantabilityMap.put(identifier, enchantability);
    }

    public static boolean hasCustomEnchantability(Identifier identifier) {
        return enchantabilityMap.containsKey(identifier);
    }

    public static int getEnchantability(Identifier identifier) {
        return enchantabilityMap.get(identifier);
    }

    public static boolean hasLinkedCurseFor(Enchantment enchantment) {
        return curseLinkMap.containsKey(enchantment);
    }

    public static Enchantment getLinkedCurseFor(Enchantment enchantment) {
        return curseLinkMap.get(enchantment);
    }
}
