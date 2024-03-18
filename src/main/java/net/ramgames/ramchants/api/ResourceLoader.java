package net.ramgames.ramchants.api;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import net.ramgames.ramchants.RamChants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ResourceLoader implements SimpleResourceReloadListener<ResourceLoader.InternalLoader>{
    public static final Logger LOGGER = LoggerFactory.getLogger("RamChants API");
    private final String path;
    private final BiFunction<Identifier, JsonObject, List<Object>> codec;
    private final Consumer<List<Object>> applier;
    private final Runnable destroyer;
    private final String loadMessage;

    public ResourceLoader(String path, String loadMessage, BiFunction<Identifier, JsonObject, List<Object>> codec, Consumer<List<Object>> applier, Runnable destroyer) {
        this.path = path;
        this.loadMessage = loadMessage;
        this.codec = codec;
        this.applier = applier;
        this.destroyer = destroyer;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(RamChants.MOD_ID, "api_" + path + "_loader");
    }

    @Override
    public CompletableFuture<InternalLoader> load(ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> new InternalLoader(loadMessage, manager, profiler, path, codec), executor);
    }

    @Override
    public CompletableFuture<Void> apply(InternalLoader data, ResourceManager manager, Profiler profiler, Executor executor) {
        data.getModules().forEach(applier);
        return CompletableFuture.runAsync(() -> {
        });
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer helper, ResourceManager manager, Profiler loadProfiler, Profiler applyProfiler, Executor loadExecutor, Executor applyExecutor) {
        destroyer.run();
        return SimpleResourceReloadListener.super.reload(helper, manager, loadProfiler, applyProfiler, loadExecutor, applyExecutor);
    }

    @Override
    public String getName() {
        return SimpleResourceReloadListener.super.getName();
    }

    public static class InternalLoader {
        private final ResourceManager manager;
        private final Profiler profiler;
        private final String path;
        private final List<List<Object>> modules = new ArrayList<>();
        private final BiFunction<Identifier, JsonObject, List<Object>> codec;
        private final String loadMessage;

        private InternalLoader(String loadMessage, ResourceManager manager, Profiler profiler, String path, BiFunction<Identifier, JsonObject, List<Object>> codec) {
            this.loadMessage = loadMessage;
            this.manager = manager;
            this.profiler = profiler;
            this.path = path;
            this.codec = codec;
            loadData();
        }

        private void loadData() {
            profiler.push("Load API_"+path);
            Map<Identifier, Resource> resources = manager.findResources(path, id -> id.getPath().endsWith(".json"));
            resources.forEach((key, value) -> {
                try {
                    List<Object> result = codec.apply(key, JsonHelper.deserialize(value.getReader()).getAsJsonObject());
                    modules.add(result);
                } catch (Exception e) {
                    LOGGER.error("failed to load resource " + key + ":" + e.getMessage());
                }

            });
            profiler.pop();
            LOGGER.info("Loaded "+resources.size()+loadMessage);
        }

        public List<List<Object>> getModules() {
            return this.modules;
        }
    }
}
