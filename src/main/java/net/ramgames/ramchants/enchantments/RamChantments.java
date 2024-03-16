package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.RamChants;

import static net.minecraft.enchantment.Enchantment.Rarity.COMMON;
import static net.minecraft.enchantment.Enchantment.Rarity.UNCOMMON;

@SuppressWarnings("unused")
public class RamChantments {

    public static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] MAIN_HAND = new EquipmentSlot[]{EquipmentSlot.MAINHAND};
    public static final EquipmentSlot[] FEET = new EquipmentSlot[]{EquipmentSlot.FEET};
    public static final EquipmentSlot[] CHEST = new EquipmentSlot[]{EquipmentSlot.CHEST};
    public static final EquipmentSlot[] HEAD = new EquipmentSlot[]{EquipmentSlot.HEAD};

    public static final Enchantment VISCOSITY = register("viscosity", new ViscosityEnchantment());
    public static final Enchantment ARTHROPODS_FAVOR = register("arthropods_favor", new AntiDamageEnchantment(Enchantments.BANE_OF_ARTHROPODS, UNCOMMON, 2));
    public static final Enchantment DULLNESS = register("dullness", new AntiDamageEnchantment(Enchantments.SHARPNESS, COMMON, 0));
    public static final Enchantment WRAITHWARD = register("wraithward", new AntiDamageEnchantment(Enchantments.SMITE, UNCOMMON, 1));
    public static final Enchantment VULNERABILITY = register("vulnerability", new AntiProtectionEnchantment(Enchantments.PROTECTION, COMMON, ProtectionEnchantment.Type.ALL, ALL_ARMOR));
    public static final Enchantment EXPLOSIVE_FRAGILITY = register("explosive_fragility", new AntiProtectionEnchantment(Enchantments.BLAST_PROTECTION, UNCOMMON, ProtectionEnchantment.Type.EXPLOSION, ALL_ARMOR));
    public static final Enchantment ARROWS_BANE = register("arrows_bane", new AntiProtectionEnchantment(Enchantments.PROJECTILE_PROTECTION, UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, ALL_ARMOR));
    public static final Enchantment BURNING = register("burning", new AntiProtectionEnchantment(Enchantments.FIRE_PROTECTION, UNCOMMON, ProtectionEnchantment.Type.FIRE, ALL_ARMOR));
    public static final Enchantment PLUMMET = register("plummet", new AntiProtectionEnchantment(Enchantments.FEATHER_FALLING, UNCOMMON, ProtectionEnchantment.Type.FALL, FEET));
    public static final Enchantment ELECTRIC_ATTRACTION = register("electric_attraction", new ElectricAttractionEnchantment());
    public static final Enchantment VOIDING = register("voiding", new VoidingEnchantment());
    public static final Enchantment AQUA_HAUL = register("aqua_haul", new AquaHaulEnchantment());
    public static final Enchantment CRUMBLING = register("crumbling", new CrumblingEnchantment());
    public static final Enchantment PRICKING = register("pricking", new PrickingEnchantment());
    public static final Enchantment TORPIDITY = register("torpidity", new TorpidityEnchantment());
    public static final Enchantment DETERRENCE = register("deterrence", new DeterrenceEnchantment());
    public static final Enchantment SEAS_WOE = register("seas_woe", new SeasWoeEnchantment());
    public static final Enchantment GHOST_QUIVER = register("ghost_quiver", new GhostQuiverEnchantment());
    public static final Enchantment FLIMSY = register("flimsy", new FlimsyEnchantment());
    public static final Enchantment SLOW_DRAW = register("slow_draw", new SlowDrawEnchantment());
    public static final Enchantment INACCURACY = register("inaccuracy", new InaccuracyEnchantment());
    public static final Enchantment DROWNING = register("drowning", new DrowningEnchantment());
    public static final Enchantment BOILING_ARROW = register("boiling_arrow", new BoilingArrow());
    public static final Enchantment MOLTEN_HANDLE = register("molten_handle", new MoltenHandleEnchantment());
    public static final Enchantment REPULSION = register("repulsion", new RepulsionEnchantment());
    public static final Enchantment RECOIL = register("recoil", new RecoilEnchantment());
    public static final Enchantment AQUATIC_IMPOTENCE = register("aquatic_impotence", new AquaticImpotenceEnchantment());
    public static final Enchantment WHIMSY_BLADE = register("whimsy_blade", new WhimsyBladeEnchantment());
    public static final Enchantment SCARCITY = register("scarcity", new AntiLootEnchantment(Enchantments.LOOTING, EnchantmentTarget.WEAPON));
    public static final Enchantment DESTITUTION = register("destitution", new AntiLootEnchantment(Enchantments.FORTUNE, EnchantmentTarget.DIGGER));

    private static Enchantment register(String name, Enchantment enchantment) {
        RamChants.registerLinkedCurse((AbstractLinkedCurseEnchantment) enchantment);
        return Registry.register(Registries.ENCHANTMENT, new Identifier(RamChants.MOD_ID, name), enchantment);
    }

    public static void onInitialize() {
        RamChants.LOGGER.info("registering curses");
    }
}
