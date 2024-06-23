package net.ramgames.ramchants.api;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.List;

public class ResourceLoaders {

    public static final ResourceLoader ENCHANTABILITY_LOADER = new ResourceLoader("ramchants/enchantability", " Enchantabilities",
            (id, json) -> {
                String[] splits = id.getPath().split("/");
                String item = id.getNamespace()+':'+splits[splits.length-1].replace(".json", "");
                int enchantability = json.get("enchantability").getAsInt();
                String fileName = id.toString();
                return List.of(item, enchantability, fileName);
            },
            (objects) -> {
                String fileName = (String) objects.get(2);
                Identifier identifier = Identifier.of((String) objects.get(0));
                if(!Resources.ENCHANTABILITY.contains(identifier)) {
                    int number = (int) objects.get(1);
                    if(number < 0) ResourceLoader.LOGGER.error("file \"{}\": Enchantability must be positive, but found {}", fileName, number);
                    else Resources.ENCHANTABILITY.register(identifier, number);
                }
            },
            Resources.ENCHANTABILITY::flush

    );

    public static void onInitialize() {
        ResourceLoader.LOGGER.info("initializing...");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(ENCHANTABILITY_LOADER);
    }
}
