package net.ramgames.ramchants.api;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.List;

public class ResourceLoaders {

    public static final ResourceLoader ENCHANTABILITY_LOADER = new ResourceLoader("ramchants/enchantability", " Enchantabilities",
            (id, json) -> {
                boolean replace = json.get("replace").getAsBoolean();
                String[] splits = id.getPath().split("/");
                String item = id.getNamespace()+':'+splits[splits.length-1].replace(".json", "");
                int enchantability = json.get("enchantability").getAsInt();
                String fileName = id.toString();
                return List.of(item, replace, enchantability, fileName);
            },
            (objects) -> {
                String fileName = (String) objects.get(3);
                Identifier identifier = Identifier.of((String) objects.get(0));
                if(!Resources.ENCHANTABILITY.contains(identifier) || Resources.ENCHANTABILITY.contains(identifier) && (boolean) objects.get(1)) {
                    int number = (int) objects.get(2);
                    if(number < 0) ResourceLoader.LOGGER.error("file \""+fileName+"\": "+"Enchantability must be positive, but found "+number);
                    else Resources.ENCHANTABILITY.register(identifier, number);
                }
            },
            Resources.ENCHANTABILITY::flush

    );
    /*public static final ResourceLoader LINKED_CURSES_LOADER = new ResourceLoader("ramchants/linked_curses", " Linked Curses",
            (id, json) -> {
                boolean replace = json.get("replace").getAsBoolean();
                String enchantment = json.get("enchantment").getAsString();
                String curse = json.get("linkedCurse").getAsString();
                String fileName = id.toString();
                return List.of(enchantment, replace, curse, fileName);
            },
            (objects) -> {
                String fileName = (String) objects.get(3);
                Identifier identifier = new Identifier((String) objects.get(0));
                if(!Resources.LINKED_CURSES.contains(identifier) || Resources.LINKED_CURSES.contains(identifier) && (boolean) objects.get(1)) {
                    RegistryEntryLookup<Enchantment> registryEntryLookup2 = DynamicRegistryManager.getRegistryLookup(RegistryKeys.ENCHANTMENT);
                    RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT)
                    Enchantment curse = Registries.ENCHANTMENT.get(new Identifier((String) objects.get(2)));
                    Enchantment linked = Registries.ENCHANTMENT.get(identifier);
                    if(curse == null) ResourceLoader.LOGGER.error("file \""+fileName+"\": "+"failed to find curse enchantment by the id: "+new Identifier((String) objects.get(2)));
                    if(linked == null) ResourceLoader.LOGGER.error("file \""+fileName+"\": "+"failed to find linked enchantment by the id: "+new Identifier((String) objects.get(0)));
                    if(curse != null && linked != null) Resources.LINKED_CURSES.register(identifier, curse);
                }
            },
            Resources.LINKED_CURSES::flush
    );*/

    public static void initialize() {
        ResourceLoader.LOGGER.info("initializing...");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(ENCHANTABILITY_LOADER);
        //ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(LINKED_CURSES_LOADER);
    }
}
