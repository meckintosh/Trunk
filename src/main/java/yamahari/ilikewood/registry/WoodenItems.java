package yamahari.ilikewood.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yamahari.ilikewood.client.tileentity.renderer.WoodenChestItemStackTileEntityRenderer;
import yamahari.ilikewood.item.WoodenBlockItem;
import yamahari.ilikewood.util.Constants;
import yamahari.ilikewood.util.WoodType;
import yamahari.ilikewood.util.WoodenObjectType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class WoodenItems {
    public static final DeferredRegister<Item> REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, Constants.MOD_ID);
    private static final Map<WoodenObjectType, Map<WoodType, RegistryObject<Item>>> REGISTRY_OBJECTS;

    static {
        final Map<WoodenObjectType, Map<WoodType, RegistryObject<Item>>> registryObjects = new EnumMap<>(WoodenObjectType.class);

        final BiFunction<WoodenObjectType, RegistryObject<Block>, Item> simpleBuildingBlockItem = registerSimpleBlockItem((new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS));
        final BiFunction<WoodenObjectType, RegistryObject<Block>, Item> simpleDecorationBlockItem = registerSimpleBlockItem((new Item.Properties()).group(ItemGroup.DECORATIONS));
        final BiFunction<WoodenObjectType, RegistryObject<Block>, Item> simpleMiscBlockItem = registerSimpleBlockItem((new Item.Properties()).group(ItemGroup.MISC));

        registryObjects.put(WoodenObjectType.PANELS, registerBlockItems(WoodenObjectType.PANELS, simpleBuildingBlockItem));
        registryObjects.put(WoodenObjectType.STAIRS, registerBlockItems(WoodenObjectType.STAIRS, simpleBuildingBlockItem));
        registryObjects.put(WoodenObjectType.SLAB, registerBlockItems(WoodenObjectType.SLAB, simpleBuildingBlockItem));
        registryObjects.put(WoodenObjectType.BARREL, registerBlockItems(WoodenObjectType.BARREL, simpleDecorationBlockItem));
        registryObjects.put(WoodenObjectType.BOOKSHELF, registerBlockItems(WoodenObjectType.BOOKSHELF, simpleBuildingBlockItem));
        registryObjects.put(WoodenObjectType.COMPOSTER, registerBlockItems(WoodenObjectType.COMPOSTER, simpleMiscBlockItem));
        registryObjects.put(WoodenObjectType.CHEST, registerBlockItems(WoodenObjectType.CHEST, registerSimpleBlockItem((new Item.Properties()).group(ItemGroup.DECORATIONS).setISTER(() -> WoodenChestItemStackTileEntityRenderer::new))));

        REGISTRY_OBJECTS = Collections.unmodifiableMap(registryObjects);
    }

    private WoodenItems() {
    }

    private static Map<WoodType, RegistryObject<Item>> registerBlockItems(final WoodenObjectType objectType, final BiFunction<WoodenObjectType, RegistryObject<Block>, Item> function) {
        final Map<WoodType, RegistryObject<Item>> items = new EnumMap<>(WoodType.class);
        for (final WoodType woodType : WoodType.values()) {
            final RegistryObject<Block> block = WoodenBlocks.getRegistryObject(objectType, woodType);
            items.put(woodType, REGISTRY.register(block.getId().getPath(), () -> function.apply(objectType, block)));
        }
        return Collections.unmodifiableMap(items);
    }

    private static BiFunction<WoodenObjectType, RegistryObject<Block>, Item> registerSimpleBlockItem(final Item.Properties properties) {
        return (objectType, block) -> new WoodenBlockItem(objectType, block.get(), properties);
    }
}