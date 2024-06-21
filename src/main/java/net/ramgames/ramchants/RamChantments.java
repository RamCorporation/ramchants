package net.ramgames.ramchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;


@SuppressWarnings("unused")
public class RamChantments {

    public static final RegistryKey<Enchantment> VISCOSITY = of("viscosity");
    public static final RegistryKey<Enchantment> ARTHROPODS_FAVOR = of("arthropods_favor");
    public static final RegistryKey<Enchantment> EXPLOSIVE_FRAGILITY = of("explosive_fragility");
    public static final RegistryKey<Enchantment> SNARE = of("snare");
    public static final RegistryKey<Enchantment> ELECTRIC_ATTRACTION = of("electric_attraction");
    public static final RegistryKey<Enchantment> HALLOW = of("hallow");
    public static final RegistryKey<Enchantment> AQUA_HAUL = of("aqua_haul");
    public static final RegistryKey<Enchantment> TORPIDITY = of("torpidity");
    public static final RegistryKey<Enchantment> PLUMMET = of("plummet");
    public static final RegistryKey<Enchantment> MOLTEN_HANDLE = of("molten_handle");
    /*public static final Enchantment DULLNESS = register("dullness", new AntiDamageEnchantment(Enchantments.SHARPNESS, COMMON, 0));
    public static final Enchantment WRAITHWARD = register("wraithward", new AntiDamageEnchantment(Enchantments.SMITE, UNCOMMON, 1));
    public static final Enchantment VULNERABILITY = register("vulnerability", new AntiProtectionEnchantment(Enchantments.PROTECTION, COMMON, ProtectionEnchantment.Type.ALL, ALL_ARMOR));
    public static final Enchantment ARROWS_BANE = register("arrows_bane", new AntiProtectionEnchantment(Enchantments.PROJECTILE_PROTECTION, UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, ALL_ARMOR));
    public static final Enchantment BURNING = register("burning", new AntiProtectionEnchantment(Enchantments.FIRE_PROTECTION, UNCOMMON, ProtectionEnchantment.Type.FIRE, ALL_ARMOR));
    public static final Enchantment VOIDING = register("voiding", new VoidingEnchantment());
    public static final Enchantment CRUMBLING = register("crumbling", new CrumblingEnchantment());
    public static final Enchantment PRICKING = register("pricking", new PrickingEnchantment());
    public static final Enchantment DETERRENCE = register("deterrence", new DeterrenceEnchantment());
    public static final Enchantment SEAS_WOE = register("seas_woe", new SeasWoeEnchantment());
    public static final Enchantment GHOST_QUIVER = register("ghost_quiver", new GhostQuiverEnchantment());
    public static final Enchantment FLIMSY = register("flimsy", new FlimsyEnchantment());
    public static final Enchantment SLOW_DRAW = register("slow_draw", new SlowDrawEnchantment());
    public static final Enchantment INACCURACY = register("inaccuracy", new InaccuracyEnchantment());
    public static final Enchantment DROWNING = register("drowning", new DrowningEnchantment());
    public static final Enchantment BOILING_ARROW = register("boiling_arrow", new BoilingArrow());

    public static final Enchantment REPULSION = register("repulsion", new RepulsionEnchantment());
    public static final Enchantment RECOIL = register("recoil", new RecoilEnchantment());
    public static final Enchantment AQUATIC_IMPOTENCE = register("aquatic_impotence", new AquaticImpotenceEnchantment());
    public static final Enchantment WHIMSY_BLADE = register("whimsy_blade", new WhimsyBladeEnchantment());
    public static final Enchantment SCARCITY = register("scarcity", new AntiLootEnchantment(Enchantments.LOOTING, EnchantmentTarget.WEAPON));
    public static final Enchantment DESTITUTION = register("destitution", new AntiLootEnchantment(Enchantments.FORTUNE, EnchantmentTarget.DIGGER));
    public static final Enchantment STAGNATION = register("stagnation", new StagnationEnchantment());
    public static final Enchantment BETRAYAL = register("betrayal", new BetrayalEnchantment());
    public static final Enchantment DEFLECTION = register("deflection", new DeflectionEnchantment());*/


    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(RamChants.MOD_ID, id));
    }

    public static void onInitialize() {
        RamChants.LOGGER.info("registering curses");
    }
}
