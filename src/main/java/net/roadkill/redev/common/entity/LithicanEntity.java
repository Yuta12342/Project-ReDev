package net.roadkill.redev.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.roadkill.redev.common.entity.goal.TransformEntityGoal;
import net.roadkill.redev.util.RDMath;
import net.roadkill.redev.util.registries.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.rmi.server.Skeleton;
import java.util.Optional;
import java.util.List;

public class LithicanEntity extends Zombie implements RangedAttackMob {

    private final MeleeAttackGoal meleeGoal = new ZombieAttackGoal(this, 1.0, false);
    private final RangedBowAttackGoal<LithicanEntity> bowGoal = new RangedBowAttackGoal(this, 1.0D, 20, 15.0F);
    private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.defineId(LithicanEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(LithicanEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> HEAT = SynchedEntityData.defineId(LithicanEntity.class,
            EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(LithicanEntity.class,
            EntityDataSerializers.BOOLEAN);

    private boolean isBroken = false;

    // private boolean isBaby = false;

    private int awakenDelay = -1;

    // private LivingDeathEvent deathEvent = new LivingDeathEvent();

    public LithicanEntity(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        NeoForge.EVENT_BUS.addListener(this::onLivingDeath);
    }

    private void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() == this) {
            this.killedEntity((ServerLevel) this.level(), player);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.ATTACK_DAMAGE, 3).add(Attributes.ARMOR, 5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.targetSelector.addGoal(5, new TransformEntityGoal(this, Zombie.class, true));
        // this.targetSelector.addGoal(5, new TransformEntityGoal(this, Player.class,
        // true));
    }



    protected LithicanEntity babySpawn() {
        LithicanEntity baby = new LithicanEntity(getType(), this.level());
        // baby.isBaby = true;
        baby.setBaby(true);
        baby.entityData.set(BABY, true);
        return baby;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ACTIVE, true);
        builder.define(VARIANT, 0);
        builder.define(HEAT, 0f);
        builder.define(BABY, false);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    protected AbstractArrow getArrow(ItemStack arrow, float velocity, @Nullable ItemStack weapon) {
        return ProjectileUtil.getMobArrow(this, arrow, velocity, weapon);
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack weapon = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, (item) -> {
            return item instanceof BowItem;
        }));
        ItemStack itemstack1 = this.getProjectile(weapon);
        AbstractArrow abstractarrow = this.getArrow(itemstack1, distanceFactor, weapon);
        Item var7 = weapon.getItem();
        if (var7 instanceof ProjectileWeaponItem weaponItem) {
            abstractarrow = weaponItem.customArrow(abstractarrow, itemstack1, weapon);
        }

        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333) - abstractarrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Level var15 = this.level();
        if (var15 instanceof ServerLevel serverlevel) {
            Projectile.spawnProjectileUsingShoot(abstractarrow, serverlevel, itemstack1, d0,
                    d1 + d3 * 0.20000000298023224, d2, 1.6F, (float) (14 - serverlevel.getDifficulty().getId() * 4));
        }

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public void reassessWeaponGoal() {
        if (this.level() != null && !this.level().isClientSide) {
            this.goalSelector.removeGoal(this.bowGoal);
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.getAvailableGoals().removeIf(wrappedGoal -> wrappedGoal.getGoal() instanceof net.minecraft.world.entity.ai.goal.ZombieAttackGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, (item) -> {
                return item instanceof BowItem;
            }));
            if (itemstack.getItem() instanceof BowItem) {
                int i = this.getHardAttackInterval();
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = this.getAttackInterval();
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }

    }

    protected int getHardAttackInterval() {
        return 20;
    }

    protected int getAttackInterval() {
        return 40;
    }

    @Override
    public boolean isSunSensitive() {
        return false;
    }

    @Override
    public boolean isUnderWaterConverting() {
        return false;
    }

    @Override
    public boolean convertVillagerToZombieVillager(ServerLevel level, net.minecraft.world.entity.npc.Villager pZombie) {
        return false;
    }

    public boolean isActive() {
        return this.entityData.get(ACTIVE) && this.level().getDifficulty() != Difficulty.PEACEFUL; 
    }

    public void setActive(boolean active) {
        this.entityData.set(ACTIVE, active);
    }

    public Variant getVariant() {
        return Variant.fromId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    public float getHeat() {
        return this.entityData.get(HEAT);
    }

    public void setHeat(float heat) {
        if (getVariant() == Variant.GLASS) {
            this.entityData.set(HEAT, 0f);
            return;
        }
        this.entityData.set(HEAT, heat);
    }

    @Override
    public void tick() {


        if (this.getTarget() instanceof LithicanEntity
                || (this.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED)
                        && this.getTargetFromBrain() instanceof LithicanEntity)) {
            this.setTarget(null);
        }        

        // Decrease heat gradually
        float heat = this.getHeat();
        if (heat > 0) {
            this.setHeat(heat - 0.2f);
            if (this.level().isClientSide && this.random.nextInt(100) < heat) {
            // spawn dripping lava particles within the entity's hitbox
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(ParticleTypes.FALLING_LAVA, this.getRandomX(0.5), this.getRandomY(),
                    this.getRandomZ(0.5), 0, 0, 0);
            }
            }
        }

        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            // this.discard();
            super.tick();
            return;
        }

        if (heat >= 100.0 && this.random.nextInt(1000) == 0) {
            if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, Level.ExplosionInteraction.MOB);

            this.dropPreservedEquipment((ServerLevel)this.level());
            this.discard();
        }
        }

        // If lithican is scheduled to be awakened
        if (this.awakenDelay > 0) {
            this.awakenDelay--;
            if (this.awakenDelay == 0) {
                this.playBreakAnimation();
                this.setActive(true);
                this.playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 1, 1);
                for (WrappedGoal wrappedGoal : this.goalSelector.getAvailableGoals()) {
                    if (wrappedGoal.getGoal() instanceof MeleeAttackGoal meleeAttackGoal) {
                        meleeAttackGoal.canUse();
                        meleeAttackGoal.start();
                        break;
                    }
                }
                this.awakenDelay = -1;
            }
            if (this.isActive()) {
            BlockPos currentPos = this.blockPosition();
            BlockState currentBlock = level().getBlockState(currentPos);
            if (!currentBlock.isAir() && !isBlacklistedBlock(currentBlock)) {
                BlockPos abovePos = currentPos.above(2);
                BlockState aboveBlock = level().getBlockState(abovePos);
                if (aboveBlock.isAir()) {
                    this.jumpFromGround();
                } else {
                    BlockPos teleportPos = this.findTopmostAirBlock(abovePos);
                    this.teleportTo(teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5);
                }
            }
        }
    }

        if (this.tickCount % 10 == 0 && !this.isActive() && this.random.nextInt(5) == 0) {
            for (Player player : this.level().players()) {
                if (player.distanceTo(this) < 6 && !player.isCreative() && !player.isSpectator()) {
                    this.setActive(true);
                    this.playBreakAnimation();
                    this.playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 1, 1);
                    this.setTarget(player);
                    break;
                }
            }
        }
        super.tick();

    }

    private boolean isBlacklistedBlock(BlockState blockState) {
        // Add your logic to determine if the block is blacklisted
        return blockState.is(Blocks.BEDROCK) || blockState.is(Blocks.BARRIER) || blockState.is(Blocks.LAVA) || blockState.is(Blocks.WATER) || blockState.is(Blocks.FIRE);
    }

    private void tryTransformEntity(Entity entity) {
        BlockState blockState = this.level().getBlockState(entity.blockPosition().below());
        Variant variant = getVariantForBlock(blockState);

        if (variant != null) {
            transformEntity(entity, variant);
        }
    }

    private Variant getVariantForBlock(BlockState blockState) {
        if (blockState.is(Tags.Blocks.SANDSTONE_BLOCKS) || blockState.is(Tags.Blocks.SANDS)) {
            return Variant.SANDSTONE;
        } else if (blockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)) {
            return Variant.DEEPSLATE;
        } else if (blockState.is(Blocks.BASALT)) {
            return Variant.BASALT;
        } else if (blockState.is(Blocks.STONE)) {
            return Variant.STONE;
        }
        return null;
    }

    public static void copyEquipment(LivingEntity source, LivingEntity target) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = source.getItemBySlot(slot);
            target.setItemSlot(slot, itemStack.copy());
        }
    }

    private void transformEntity(Entity entity, Variant variant) {
        if (!this.level().isClientSide) {
            LithicanEntity lithican = (entity instanceof LivingEntity livingEntity && livingEntity.isBaby())
                    ? this.babySpawn()
                    : new LithicanEntity(getType(), level());
            if (lithican != null) {
                lithican.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                lithican.setVariant(variant);
                this.level().addFreshEntity(lithican);
                entity.discard();
                if (entity instanceof LivingEntity livingEntity) {
                    lithican.getAttribute(Attributes.MAX_HEALTH)
                            .setBaseValue(livingEntity.getMaxHealth() / 2 + lithican.getMaxHealth());
                    lithican.setHealth(lithican.getMaxHealth());

                    livingEntity.getActiveEffects().forEach(lithican::addEffect);

                    copyEquipment(livingEntity, lithican);

                    if (livingEntity instanceof Player player) {
                        for (EquipmentSlot slot : EquipmentSlot.values()) {
                            if (((ServerLevel) this.level()).getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                                lithican.setDropChance(slot, 0.0f);
                            } else {
                                player.getItemBySlot(slot).setCount(0);
                                lithican.setDropChance(slot, 1.0f);
                                lithican.setPersistenceRequired();
                            }
                        }
                        lithican.reassessWeaponGoal();
                    }
                    // else { // Get Mob intended drops, and add them as part of the loot table.
                    // livingEntity.captureDrops(null);
                    // }
                }
            }
        }
    }

    protected void addNeNavigation(Level level) {
        this.navigation = new WallClimberNavigation(this, level);
     }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("Active")) {
            this.setActive(nbt.getBoolean("Active"));
        }
        if (nbt.contains("Variant")) {
            this.setVariant(Variant.fromId(nbt.getInt("Variant")));
        }
        if (nbt.contains("Heat")) {
            this.setHeat(nbt.getInt("Heat"));
        }
        this.reassessWeaponGoal();
    }

    @Override
    public boolean save(CompoundTag nbt) {
        boolean canSave = super.save(nbt);
        if (canSave) {
            nbt.putBoolean("Active", this.isActive());
            nbt.putInt("Variant", this.getVariant().getId());
            nbt.putFloat("Heat", this.getHeat());
        }
        return canSave;
    }

    private boolean tryTransformToOreVariant() {
        if (this.random.nextFloat() < 0.05) { // 5% chance
            Variant[] oreVariants = {Variant.IRON, Variant.GOLD, Variant.DIAMOND, Variant.EMERALD, Variant.COPPER};
            Variant selectedVariant = oreVariants[this.random.nextInt(oreVariants.length)];
            this.setVariant(selectedVariant);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnData) {
        BlockState groundState = level.getBlockState(this.blockPosition().below());
        if (level.getBiome(this.blockPosition()).is(BiomeTags.HAS_DESERT_PYRAMID)
                || groundState.is(Tags.Blocks.SANDSTONE_BLOCKS) || groundState.is(Tags.Blocks.SANDS)) {
            this.setVariant(Variant.SANDSTONE);
        } else if (groundState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES)) {
            if (!this.tryTransformToOreVariant()) {
                this.setVariant(Variant.DEEPSLATE);
            }
        } else if (groundState.is(Blocks.BASALT)) {
            this.setVariant(this.random.nextBoolean() ? Variant.BASALT : Variant.BLACKSTONE);
        } else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_BADLANDS)) {
            this.setVariant(Variant.RED);
        } else {
            this.setVariant(Variant.STONE);
        }

        switch (this.getVariant()) {
            case SANDSTONE -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
                this.getAttribute(Attributes.ARMOR).setBaseValue(6);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
            }
            case DEEPSLATE -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8);
                this.getAttribute(Attributes.ARMOR).setBaseValue(9);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60);
            }
            case BASALT -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5);
                this.getAttribute(Attributes.ARMOR).setBaseValue(3);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
                this.addNeNavigation((ServerLevel) level);
            }
            case BLACKSTONE -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8);
                this.getAttribute(Attributes.ARMOR).setBaseValue(9);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50);
            }
            case RED -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
                this.getAttribute(Attributes.ARMOR).setBaseValue(5);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
            }
            case COPPER -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6);
                this.getAttribute(Attributes.ARMOR).setBaseValue(7);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60 + this.random.nextInt(20));
            }
            case IRON -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7);
                this.getAttribute(Attributes.ARMOR).setBaseValue(8);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(70 + this.random.nextInt(20));
            }
            case GOLD -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8);
                this.getAttribute(Attributes.ARMOR).setBaseValue(9);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + this.random.nextInt(20));
                this.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(2);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.2);
            }
            case DIAMOND -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10);
                this.getAttribute(Attributes.ARMOR).setBaseValue(10);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80 + this.random.nextInt(20));
            }
            case EMERALD -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12);
                this.getAttribute(Attributes.ARMOR).setBaseValue(12);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100 + this.random.nextInt(20));
            }
            case GLASS -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
            }
            default -> {
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3);
                this.getAttribute(Attributes.ARMOR).setBaseValue(5);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
            }
        }
        this.setHealth((float) this.getAttribute(Attributes.MAX_HEALTH).getValue());

        if (getVariant() == Variant.DEEPSLATE || getVariant() == Variant.STONE) {
            if (this.random.nextFloat() < 0.05) {
                if (this.random.nextFloat() < 0.05) {
                    this.addEffect(new MobEffectInstance(MobEffects.INFESTED, -1));
                }
            }
        }

        return spawnData;
    }

    @Override
    public boolean isAffectedByPotions() {
        return true;
    }
    private static final List<Holder<MobEffect>> ALLOWED_EFFECTS = List.of(MobEffects.WITHER, MobEffects.INFESTED);

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        if (ALLOWED_EFFECTS.contains(pEffectInstance.getEffect())) {
            return super.addEffect(pEffectInstance, pEntity);
        }
        return false;
    }

    @Override
    protected void tickEffects() {
        this.getActiveEffects().removeIf(effect -> !ALLOWED_EFFECTS.contains(effect.getEffect()));
        super.tickEffects();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (this.getHeat() > 5) {
            target.invulnerableTime = 0;
            target.setRemainingFireTicks((int) Math.max(target.getRemainingFireTicks(), this.getHeat()));
            target.hurt(this.damageSources().onFire(), 1f);
        }
        return super.doHurtTarget(level, target);
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        System.out.println("Entity punished: " + entity);
        boolean killed = super.killedEntity(level, entity);

        if (killed && (entity instanceof Zombie || entity instanceof Player)) {
            BlockState blockState = this.level().getBlockState(entity.blockPosition().below());
            Variant variant = getVariantForBlock(blockState);

            if (variant != null) {
                transformEntity(entity, variant);
                killed = false;
            }
        }
        return killed;
    }

    @Override
    public boolean isBaby() {
        return this.getEntityData().get(BABY);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {

        if (damageSource.is(DamageTypes.LIGHTNING_BOLT)) {
            if (this.getVariant() == Variant.SANDSTONE || this.getVariant() == Variant.RED) {
            this.setVariant(Variant.GLASS);
            this.setHealth(10);
            this.setHeat(0);
            }
            return false;
        }

        if (this.getVariant() == Variant.GLASS) {
            amount = amount * 3;
        }

        if (damageSource.is(DamageTypes.CACTUS) && this.getVariant() != Variant.GLASS) {
            return false;
        }

        if (damageSource.is(DamageTypes.CRAMMING)) {
            if (getVariant() == Variant.GLASS) {
                amount = 999;
                return true;
            }
            BlockPos currentPos = this.blockPosition();
            for (int i = 0; i < 10; i++) {
            BlockPos randomPos = currentPos.offset(this.random.nextInt(10) - 5, 0, this.random.nextInt(10) - 5);
            if (this.level().getBlockState(randomPos).isAir() && this.level().getBlockState(randomPos.above()).isAir()) {
                this.teleportTo(randomPos.getX() + 0.5, randomPos.getY(), randomPos.getZ() + 0.5);
                return false;
            }
            }
        }

        if (damageSource.is(DamageTypes.STALAGMITE) || damageSource.is(DamageTypes.FALLING_STALACTITE)) {
            if (this.getVariant() == Variant.GLASS) {
                amount = 999;
                return true;
            } else {
                amount = 0;
            }
            return false;
        }
        if (damageSource.is(DamageTypes.IN_WALL)) {
            if (this.getVariant() == Variant.GLASS) {
                amount = 999;
                return true;
            }
            BlockPos headPos = this.blockPosition().above(1);
            BlockState headBlock = level.getBlockState(headPos);
            if (!headBlock.isAir() && !isBlacklistedBlock(headBlock)) {
                level.destroyBlock(headPos, true);


                
        BlockPos currentPos = this.blockPosition();
        BlockState currentBlock = level.getBlockState(currentPos);
        if (!currentBlock.isAir()) {
            BlockPos abovePos = currentPos.above(2);
            BlockState aboveBlock = level.getBlockState(abovePos);
            if (aboveBlock.isAir()) {
                this.jumpFromGround();
            } else {
                BlockPos teleportPos = this.findTopmostAirBlock(abovePos);
                this.teleportTo(teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5);
            }
            if (!this.level().getBlockState(this.blockPosition().above(1)).isAir()) {
                BlockPos teleportPos = this.findTopmostAirBlock(currentPos.above());
                this.teleportTo(teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5);
            }   
        }
            } else return false;
        }


        // Immune to arrows
        if (damageSource.getDirectEntity() instanceof Arrow arrow) {
            if (this.getVariant() == Variant.GLASS) {
                if (!this.level().isClientSide) {
                    this.kill(level);
                    return false;
                }
                }
            this.setArrowCount(this.getArrowCount() + 1);
            level.playSound(null, this.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.NEUTRAL, 1, 1);
            if (arrow.isOnFire() && (arrow.getOwner() != null)) {
                this.setHeat(Math.min(100, this.getHeat() + 10));
            }
            arrow.setDeltaMovement(arrow.getDeltaMovement().scale(-1));
            arrow.setOwner(null);
            return false;
        } else if ((damageSource.is(DamageTypes.MOB_ATTACK) || damageSource.is(DamageTypes.PLAYER_ATTACK))
                && Optional.ofNullable(damageSource.getWeaponItem()).map(weapon -> weapon.is(ItemTags.PICKAXES))
                        .orElse(false)) {
            Item weapon = damageSource.getWeaponItem().getItem();
            // if (damageSource.getWeaponItem().isEnchanted() &&
            // damageSource.getWeaponItem()..getEnchantmentLevel(Enchantments.FIRE_ASPECT) >
            // 0) {
            // this.setHeat(Math.min(100, this.getHeat() +
            // EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT,
            // damageSource.getWeaponItem()) * 5));
            // }
            if (weapon instanceof PickaxeItem pickaxe) {
                int pickaxeLevel = getPickaxeLevel(pickaxe);
                switch (this.getVariant()) {
                    case SANDSTONE -> amount *= 2.3 * (pickaxeLevel + 1);
                    case DEEPSLATE -> amount *= 2 * (pickaxeLevel + 1);
                    case BASALT -> amount *= 3 * (pickaxeLevel + 1);
                    case BLACKSTONE -> amount *= 1 * (pickaxeLevel + 1);
                    case RED -> amount *= 2.5 * (pickaxeLevel + 1);
                    case COPPER -> amount *= 4.3 * (pickaxeLevel + 1);
                    case IRON -> amount *= 2.5 * (pickaxeLevel + 1);
                    case GOLD -> amount *= 5 * (pickaxeLevel + 1);
                    case DIAMOND -> amount *= 1.2 * (pickaxeLevel + 1);
                    case EMERALD -> amount *= 1 * (pickaxeLevel + 1);
                    default -> amount *= 1.5 * (pickaxeLevel + 1);
                }
            }
            if (this.getVariant() == Variant.GLASS) {
                amount *= 2;
            }
        } else if (damageSource.is(DamageTypes.FALL) && amount > 5) {
            amount = 999;
        } else if (damageSource.is(DamageTypes.FIREWORKS) || damageSource.is(DamageTypes.EXPLOSION)) {
            amount *= 5;
        } else if (damageSource.type().effects() == DamageEffects.BURNING) {
            this.clearFire();
            this.setHeat(Math.min(100, this.getHeat() + (int) (amount * 2)));
            return false;
        } else if (damageSource.is(DamageTypes.FALL) && this.getVariant() == Variant.GLASS) {
            amount = 999;
        }
        // Trigger the super method
        if (super.hurtServer(level, damageSource, (float) RDMath.blend(amount, amount * 2, this.getHeat(), 0, 100))) {
            // Make all nearby lithicans active if damage passes
            level.getEntitiesOfClass(LithicanEntity.class, new AABB(this.blockPosition()).inflate(8))
                    .forEach(lithican -> {
                        if (!lithican.isActive()) {
                            lithican.wakeUp();
                        }
                    });
            if (!this.isActive()) {
                this.awakenDelay = 1;
            }
            this.playHitAnimation();
            return true;
        }
        return false;
    }

    private BlockPos findTopmostAirBlock(BlockPos pos) {
        while (!this.level().isEmptyBlock(pos) && !((ServerLevel) this.level()).isOutsideBuildHeight(pos)) {
            pos = pos.above();
        }
        return pos;
    }

    private int getPickaxeLevel(PickaxeItem pickaxe) {
        float speed = pickaxe.getDefaultInstance().getDestroySpeed(Blocks.STONE.defaultBlockState());
        if (speed <= 2.0f) {
            return 0; // WOOD
        } else if (speed <= 4.0f) {
            return 1; // STONE
        } else if (speed <= 6.0f) {
            return 2; // IRON
        } else if (speed <= 8.0f) {
            return 3; // DIAMOND
        } else {
            return 4; // NETHERITE
        }
        }

        @Override
        public SoundEvent getDeathSound() {
        return this.getVariant() == Variant.GLASS ? SoundEvents.GLASS_BREAK : ModSounds.LITHICAN_DEATH;
        }

        @Override
        public SoundEvent getAmbientSound() {
        return ModSounds.LITHICAN_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.LITHICAN_HURT;
    }

    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.GILDED_BLACKSTONE_PLACE;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(this.getStepSound(), 0.3F, 0.5F);
    }

    @Override
    public void die(DamageSource damageSource) {
        this.playBreakAnimation();
        if (random.nextFloat() < 0.1) {
            if (level() instanceof ServerLevel)
            dropAllDeathLoot((ServerLevel) level(), damageSource);
            this.discard();
            isBroken = true;
        }
        if (this.getVariant() == Variant.GLASS) {
            this.discard();
            isBroken = true;
        }
        if (this.hasEffect(MobEffects.INFESTED)) {
            int count = this.level().getDifficulty() == Difficulty.HARD ? this.random.nextInt(5) + 1 : this.random.nextInt(3) + 1;
            for (int i = 0; i < count; i++) {
            Silverfish silverfish = EntityType.SILVERFISH.create(this.level(), EntitySpawnReason.TRIGGERED);
            if (silverfish != null) {
                silverfish.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                this.level().addFreshEntity(silverfish);
            }
            }
        }
        super.die(damageSource);
        }

        @Override
        public void remove(RemovalReason pReason) {
        if (!isBroken)
            this.playBreakAnimation();
        super.remove(pReason);
        }

    private static final SimpleWeightedRandomList<ItemStack> STONE_VARIANT_DROPS = SimpleWeightedRandomList
            .<ItemStack>builder()
            .add(new ItemStack(Items.COBBLESTONE), 100)
            // .add(new ItemStack(Items.COAL_ORE), 10)
            // .add(new ItemStack(Items.IRON_ORE), 5)
            // .add(new ItemStack(Items.COPPER_ORE), 5)
            // .add(new ItemStack(Items.GOLD_ORE), 3)
            // .add(new ItemStack(Items.DIAMOND_ORE), 1)
            // .add(new ItemStack(Items.EMERALD_ORE), 1)
            .build();

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        ItemStack drop = switch (this.getVariant()) {
            case SANDSTONE -> new ItemStack(Items.SANDSTONE);
            case DEEPSLATE -> new ItemStack(Items.COBBLED_DEEPSLATE);
            case BASALT -> new ItemStack(Items.BASALT);
            case BLACKSTONE -> new ItemStack(Items.BLACKSTONE);
            case COPPER -> new ItemStack(Items.RAW_COPPER);
            case IRON -> new ItemStack(Items.RAW_IRON);
            case GOLD -> new ItemStack(Items.RAW_GOLD);
            case DIAMOND -> new ItemStack(Items.DIAMOND);
            case EMERALD -> new ItemStack(Items.EMERALD);
            case GLASS -> new ItemStack(ItemStack.EMPTY.getItem());
            case RED -> new ItemStack(Items.RED_SAND);
            case GILDED -> new ItemStack(Items.GILDED_BLACKSTONE);
            default -> STONE_VARIANT_DROPS.getRandomValue(this.random).orElse(ItemStack.EMPTY);
        };
        if (!drop.isEmpty() && this.level() instanceof ServerLevel serverLevel) {
            this.spawnAtLocation(serverLevel, drop);
        }
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
    }

    private void playBreakAnimation() {
        if (!level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockForVariant()), this.getX(),
                    this.getY() + this.getBbHeight() / 2, this.getZ(), 50, this.getBbWidth() / 2,
                    this.getBbHeight() / 2, this.getBbWidth() / 2, 0.1);
        }
        this.playSound(SoundEvents.DECORATED_POT_SHATTER, 1, 0.7f);
    }

    private void playHitAnimation() {
        if (!level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockForVariant()), this.getX(),
                    this.getY() + this.getBbHeight() / 2, this.getZ(), 10, this.getBbWidth() / 2,
                    this.getBbHeight() / 4, this.getBbWidth() / 2, 0.1);
        }
        this.playSound(SoundEvents.DECORATED_POT_FALL, 1, 1f);
    }

    private void wakeUp() {
        if (!this.level().isClientSide) {
            this.awakenDelay = this.random.nextInt(5, 20);
        }
    }

    @Override
    public boolean isSilent() {
        return super.isSilent() || !this.isActive();
    }

    public BlockState getBlockForVariant() {
        return switch (this.getVariant()) {
            case SANDSTONE -> Blocks.SANDSTONE.defaultBlockState();
            case DEEPSLATE -> Blocks.DEEPSLATE.defaultBlockState();
            case BASALT -> Blocks.BASALT.defaultBlockState();
            case BLACKSTONE -> Blocks.BLACKSTONE.defaultBlockState();
            case COPPER -> Blocks.COPPER_ORE.defaultBlockState();
            case IRON -> Blocks.IRON_ORE.defaultBlockState();
            case GOLD -> Blocks.GOLD_ORE.defaultBlockState();
            case DIAMOND -> Blocks.DIAMOND_ORE.defaultBlockState();
            case EMERALD -> Blocks.EMERALD_ORE.defaultBlockState();
            case GLASS -> Blocks.GLASS.defaultBlockState();
            case RED -> Blocks.RED_SAND.defaultBlockState();
            case GILDED -> Blocks.GILDED_BLACKSTONE.defaultBlockState();
            default -> Blocks.STONE.defaultBlockState();
        };
        }

        public enum Variant {
        STONE(0),
        SANDSTONE(1),
        DEEPSLATE(2),
        BASALT(3),
        BLACKSTONE(4),
        COPPER(5),
        IRON(6),
        GOLD(7),
        DIAMOND(8),
        EMERALD(9),
        GLASS(10),
        RED(11),
        GILDED(12);

        private final int id;

        Variant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static Variant fromId(int id) {
            return switch (id) {
                case 1 -> SANDSTONE;
                case 2 -> DEEPSLATE;
                case 3 -> BASALT;
                case 4 -> BLACKSTONE;
                case 5 -> COPPER;
                case 6 -> IRON;
                case 7 -> GOLD;
                case 8 -> DIAMOND;
                case 9 -> EMERALD;
                case 10 -> GLASS;
                case 11 -> RED;
                case 12 -> GILDED;
                default -> STONE;
            };
        }
    }
}
