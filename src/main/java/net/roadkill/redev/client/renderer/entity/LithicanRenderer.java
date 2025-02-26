package net.roadkill.redev.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nimbusds.jose.util.Resource;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;
import net.roadkill.redev.ReDev;
import net.roadkill.redev.client.model.entity.LithicanModel;
import net.roadkill.redev.client.renderer.layer.LithicanMoltenLayer;
import net.roadkill.redev.client.renderer.layer.StuckArrowLayer;
import net.roadkill.redev.client.renderer.render_sate.LithicanRenderState;
import net.roadkill.redev.common.entity.LithicanEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LithicanRenderer<T extends LithicanEntity, S extends LithicanRenderState, M extends LithicanModel<S>> extends AbstractZombieRenderer<T, S, M>
{
    public static final ResourceLocation TEXTURE_STONE = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/stone_lithican.png");
    public static final ResourceLocation TEXTURE_SANDSTONE = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/sandstone_lithican.png");
    public static final ResourceLocation TEXTURE_DEEPSLATE = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/deepslate_lithican.png");
    public static final ResourceLocation TEXTURE_BASALT = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/basalt_lithican.png");
    public static final ResourceLocation TEXTURE_HEAT = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/molten_lithican.png");
    public static final ResourceLocation TEXTURE_BLACKSTONE = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/blackstone.png");
    public static final ResourceLocation TEXTURE_COPPER = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/copper.png");
    public static final ResourceLocation TEXTURE_IRON = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/iron.png");
    public static final ResourceLocation TEXTURE_GOLD = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/gold.png");
    public static final ResourceLocation TEXTURE_DIAMOND = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/diamond.png");
    public static final ResourceLocation TEXTURE_EMERALD = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/emerald.png");
    public static final ResourceLocation TEXTURE_GLASS = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/glass_lithican.png");
    public static final ResourceLocation TEXTURE_RED = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/petrified_red.png");
    public static final ResourceLocation TEXTURE_GILDED = ResourceLocation.fromNamespaceAndPath(ReDev.MOD_ID, "textures/entity/lithican/gilded_blackstone.png");

    public LithicanRenderer(EntityRendererProvider.Context pContext)
    {
        this(pContext, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_BABY,
             ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR,
             ModelLayers.ZOMBIE_BABY_INNER_ARMOR, ModelLayers.ZOMBIE_BABY_OUTER_ARMOR);

        this.addLayer(new StuckArrowLayer<>(pContext, this));
        this.addLayer(new LithicanMoltenLayer<>(this));
    }

    @Override
    public S createRenderState()
    {   return (S) new LithicanRenderState();
    }

    @Override
    public void extractRenderState(T lithican, S renderState, float partialTick)
    {
        super.extractRenderState(lithican, renderState, partialTick);
        renderState.isActive = lithican.isActive();
        renderState.heat = lithican.getHeat();
        renderState.variant = lithican.getVariant();
        renderState.entityId = lithican.getId();
    }

    private LithicanRenderer(EntityRendererProvider.Context pContext,
                             ModelLayerLocation adultModel, ModelLayerLocation babyModel,
                             ModelLayerLocation innerArmorModel, ModelLayerLocation outerArmorModel,
                             ModelLayerLocation innerArmorBabyModel, ModelLayerLocation outerArmorBabyModel)
    {
        super(pContext, (M) new LithicanModel(pContext.bakeLayer(adultModel)),
                        (M) new LithicanModel(pContext.bakeLayer(babyModel)),
                        (M) new LithicanModel(pContext.bakeLayer(innerArmorModel)),
                        (M) new LithicanModel(pContext.bakeLayer(outerArmorModel)),
                        (M) new LithicanModel(pContext.bakeLayer(innerArmorBabyModel)),
                        (M) new LithicanModel(pContext.bakeLayer(outerArmorBabyModel)));
    }

    @Override
    public ResourceLocation getTextureLocation(S renderState)
    {
        return switch (renderState.variant)
        {
            default -> TEXTURE_STONE;
            case SANDSTONE -> TEXTURE_SANDSTONE;
            case DEEPSLATE -> TEXTURE_DEEPSLATE;
            case BASALT    -> TEXTURE_BASALT;
            case BLACKSTONE -> TEXTURE_BLACKSTONE;
            case COPPER    -> TEXTURE_COPPER;
            case IRON      -> TEXTURE_IRON;
            case GOLD      -> TEXTURE_GOLD;
            case DIAMOND   -> TEXTURE_DIAMOND;
            case EMERALD   -> TEXTURE_EMERALD;
            case GLASS     -> TEXTURE_GLASS;
            case RED       -> TEXTURE_RED;
            case GILDED    -> TEXTURE_GILDED;
        };
    }
}
