package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.RamChants;

import static net.minecraft.enchantment.Enchantment.Rarity.*;

public class ModEnchantments {

    public static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment VISCOSITY = register("viscosity", new ViscosityEnchantment());
    public static final Enchantment ARTHROPODS_FAVOR = register("arthropods_favor", new AntiDamageEnchantment(UNCOMMON, 2));
    public static final Enchantment DULLNESS = register("dullness", new AntiDamageEnchantment(COMMON, 0));
    public static final Enchantment WRAITHWARD = register("wraithward", new AntiDamageEnchantment(UNCOMMON, 1));
    public static final Enchantment VULNERABILITY = register("vulnerability", new AntiProtectionEnchantment(COMMON, ProtectionEnchantment.Type.ALL));
    public static final Enchantment EXPLOSIVE_FRAGILITY = register("explosive_fragility", new AntiProtectionEnchantment(UNCOMMON, ProtectionEnchantment.Type.EXPLOSION));
    public static final Enchantment ARROWS_BANE = register("arrows_bane", new AntiProtectionEnchantment(UNCOMMON, ProtectionEnchantment.Type.PROJECTILE));
    public static final Enchantment BURNING = register("burning", new AntiProtectionEnchantment(UNCOMMON, ProtectionEnchantment.Type.FIRE));
    public static final Enchantment PLUMMET = register("plummet", new AntiProtectionEnchantment(UNCOMMON, ProtectionEnchantment.Type.FALL));
    public static final Enchantment ELECTRIC_ATTRACTION = register("electric_attraction", new ElectricAttractionEnchantment(VERY_RARE));
    public static final Enchantment VOIDING = register("voiding", new VoidingEnchantment());
    public static final Enchantment AQUA_HAUL = register("aqua_haul", new AquaHaulEnchantment());
    public static final Enchantment CRUMBLING = register("crumbling", new CrumblingEnchantment());
    public static final Enchantment BACKLASH = register("backlash", new BacklashEnchantment());

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(RamChants.MOD_ID, name), enchantment);
    }

    public static void onInitialize() {
        System.out.println("registering curses...");
    }
}
