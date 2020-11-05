package yamahari.ilikewood.plugin.vanilla;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelProvider;
import yamahari.ilikewood.registry.resource.resources.IWoodenLogResource;
import yamahari.ilikewood.registry.resource.resources.IWoodenPlanksResource;
import yamahari.ilikewood.registry.resource.resources.IWoodenSlabResource;
import yamahari.ilikewood.registry.resource.resources.IWoodenStrippedLogResource;
import yamahari.ilikewood.registry.woodtype.IWoodType;
import yamahari.ilikewood.util.Util;
import yamahari.ilikewood.util.resources.WoodenLogResource;
import yamahari.ilikewood.util.resources.WoodenPlanksResource;
import yamahari.ilikewood.util.resources.WoodenSlabResource;
import yamahari.ilikewood.util.resources.WoodenStrippedLogResource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class VanillaWoodenResources {
    public static final Map<IWoodType, IWoodenPlanksResource> PLANKS;
    public static final Map<IWoodType, IWoodenLogResource> LOGS;
    public static final Map<IWoodType, IWoodenStrippedLogResource> STRIPPED_LOGS;
    public static final Map<IWoodType, IWoodenSlabResource> SLABS;

    private static String getLogPostFix(final IWoodType woodType) {
        return woodType.equals(VanillaWoodTypes.CRIMSON) || woodType.equals(VanillaWoodTypes.WARPED) ? "stem" : "log";
    }

    static {
        final Map<IWoodType, IWoodenPlanksResource> planks = new HashMap<>();
        final Map<IWoodType, IWoodenLogResource> logs = new HashMap<>();
        final Map<IWoodType, IWoodenStrippedLogResource> strippedLogs = new HashMap<>();
        final Map<IWoodType, IWoodenSlabResource> slabs = new HashMap<>();

        VanillaWoodTypes.get().forEach(woodType -> {
            final String logPostFix = getLogPostFix(woodType);
            final ResourceLocation planksResource = new ResourceLocation(Util.toRegistryName(woodType.getName(), "planks"));
            final ResourceLocation logResource = new ResourceLocation(Util.toRegistryName(woodType.getName(), logPostFix));
            final ResourceLocation strippedLogResource = new ResourceLocation(Util.toRegistryName("stripped", woodType.getName(), logPostFix));
            final ResourceLocation slabResource = new ResourceLocation(Util.toRegistryName(woodType.getName(), "slab"));

            final ResourceLocation planksTexture = new ResourceLocation(planksResource.getNamespace(), Util.toPath(ModelProvider.BLOCK_FOLDER, planksResource.getPath()));

            planks.put(woodType, new WoodenPlanksResource(planksTexture, planksResource));

            logs.put(woodType, new WoodenLogResource(
                    new ResourceLocation(logResource.getNamespace(), Util.toPath(ModelProvider.BLOCK_FOLDER, Util.toRegistryName(logResource.getPath(), "top"))),
                    new ResourceLocation(logResource.getNamespace(), Util.toPath(ModelProvider.BLOCK_FOLDER, logResource.getPath())),
                    logResource
            ));

            strippedLogs.put(woodType, new WoodenStrippedLogResource(
                    new ResourceLocation(strippedLogResource.getNamespace(), Util.toPath(ModelProvider.BLOCK_FOLDER, Util.toRegistryName(strippedLogResource.getPath(), "top"))),
                    new ResourceLocation(strippedLogResource.getNamespace(), Util.toPath(ModelProvider.BLOCK_FOLDER, strippedLogResource.getPath())),
                    strippedLogResource
            ));

            slabs.put(woodType, new WoodenSlabResource(planksTexture, planksTexture, planksTexture, slabResource));
        });

        PLANKS = Collections.unmodifiableMap(planks);
        LOGS = Collections.unmodifiableMap(logs);
        STRIPPED_LOGS = Collections.unmodifiableMap(strippedLogs);
        SLABS = Collections.unmodifiableMap(slabs);
    }

    private VanillaWoodenResources() {
    }
}
