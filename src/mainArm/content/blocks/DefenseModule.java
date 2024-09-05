package mainArm.content.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.*;

public class DefenseModule {
    public static Block

    //Turrets
    gunmachina, mightydao, rapidwings, gatlingwings;

    public static void load() {

        gunmachina = new ItemTurret("gunmachina") {{
           requirements(Category.turret, with(Items.copper, 200, Items.titanium, 300, Items.silicon, 50));
           ammo(
                   Items.graphite, new FlakBulletType(7f, 5) {{
                       ammoMultiplier = 3f;
                       shootEffect = Fx.shootSmall;
                       width = 6f;
                       height = 8f;
                       knockback = 0.3f;
                       reloadMultiplier = 1.7f;
                       collidesGround = true;
                   }},
                   Items.pyratite, new FlakBulletType(7f, 15){{
                       shootEffect = Fx.shootSmall;
                       ammoMultiplier = 6f;
                       splashDamage = 20f;
                       splashDamageRadius = 30f;
                       collidesGround = true;

                       status = StatusEffects.burning;
                       statusDuration = 30f;
                   }},
                   Items.blastCompound, new FlakBulletType(7f, 20){{
                       shootEffect = Fx.shootBig;
                       ammoMultiplier = 9f;
                       splashDamage = 45f;
                       splashDamageRadius = 60f;
                       collidesGround = true;

                       status = StatusEffects.blasted;
                       statusDuration = 60f;
                   }},
                   Items.surgeAlloy, new FlakBulletType(8.5f, 25){{
                       ammoMultiplier = 15f;
                       splashDamage = 30f * 1.5f;
                       splashDamageRadius = 38f;
                       lightning = 2;
                       lightningLength = 7;
                       shootEffect = Fx.shootBig;
                       collidesGround = true;
                       explodeRange = 20f;
                   }}
           );
            shootY = 10f;

            shoot = new ShootBarrel(){{
                barrels = new float[]{
                        0f, 3f, 0f,
                        5f, 0f, 0f,
                        -5f, 0f, 0f,
                };
            }};

            recoils = 3;
            drawer = new DrawTurret("reinforced-"){{
                for(int i = 3; i > 0; i--){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + i){{
                        progress = PartProgress.recoil;
                        recoilIndex = f - 1;
                        under = true;
                        moveY = -2f;
                    }});
                }
            }};

            reload = 5f;
            range = 380f;
            size = 3;
            recoil = 1.5f;
            recoilTime = 10;
            rotateSpeed = 10f;
            inaccuracy = 2.5f;
            shootCone = 30f;
            shootSound = Sounds.shootSnap;
            coolant = consumeCoolant(0.25f);

            scaledHealth = 200;
            limitRange();

        }};

        mightydao = new PowerTurret("mightydao") {{
            requirements(Category.turret, with(Items.graphite, 200, Items.titanium, 200, Items.silicon, 350, Items.plastanium, 20, Items.surgeAlloy, 5));

            shootType = new BasicBulletType(){{
                shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                    colorTo = Pal.surge;
                    sizeTo = 26f;
                    lifetime = 14f;
                    strokeFrom = 4f;
                }});
                smokeEffect = Fx.shootSmokeTitan;
                hitColor = Pal.surge;

                sprite = "large-orb";
                trailEffect = Fx.missileTrail;
                trailInterval = 5f;
                trailParam = 4f;
                pierceCap = 2;
                buildingDamageMultiplier = 0.5f;
                fragOnHit = false;
                speed = 7f;
                damage = 100f;
                lifetime = 120f;
                width = height = 16f;
                backColor = Pal.surge;
                frontColor = Color.white;
                shrinkX = shrinkY = 0f;
                trailColor = Pal.surge;
                trailLength = 12;
                trailWidth = 2.2f;
                despawnEffect = hitEffect = new ExplosionEffect(){{
                    waveColor = Pal.surge;
                    smokeColor = Color.gray;
                    sparkColor = Pal.sap;
                    waveStroke = 4f;
                    waveRad = 40f;
                }};
                despawnSound = Sounds.dullExplosion;

                //TODO shoot sound
                shootSound = Sounds.cannon;

                fragBullet = intervalBullet = new BasicBulletType(3f, 35){{
                    width = 9f;
                    hitSize = 5f;
                    height = 15f;
                    pierce = true;
                    lifetime = 45f;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = Pal.surge;
                    frontColor = Color.white;
                    trailWidth = 2.1f;
                    trailLength = 5;
                    hitEffect = despawnEffect = new WaveEffect(){{
                        colorFrom = colorTo = Pal.surge;
                        sizeTo = 4f;
                        strokeFrom = 4f;
                        lifetime = 10f;
                    }};
                    buildingDamageMultiplier = 0.3f;
                    homingPower = 0.2f;
                    homingRange = 160f;
                }};

                bulletInterval = 3f;
                intervalRandomSpread = 20f;
                intervalBullets = 2;
                intervalAngle = 180f;
                intervalSpread = 300f;

                fragBullets = 20;
                fragVelocityMin = 0.5f;
                fragVelocityMax = 1.5f;
                fragLifeMin = 0.5f;
            }};
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-blade"){{
                              progress = PartProgress.recoil;
                              heatColor = Color.valueOf("ff6214");
                              mirror = true;
                              under = true;
                              moveX = 2f;
                              moveY = -1f;
                              moveRot = -7f;
                          }},
                        new RegionPart("-back") {{
                            progress = PartProgress.recoil;
                            moveY = (-5 * 0.25f) + (-8 * 0.25f);
                        }});
            }};
            consumePower(3.5f);

            inaccuracy = 1f;
            shake = 2f;
            shootY = 4;
            outlineColor = Pal.darkOutline;
            size = 4;
            reload = 85f;
            cooldownTime = reload;
            recoil = 3f;
            range = 460;
            shootCone = 20f;
            scaledHealth = 260;
            rotateSpeed = 5f;
            researchCostMultiplier = 0.04f;

            limitRange(9f);
        }};

        rapidwings = new ItemTurret("rapidwings") {{
            requirements(Category.turret, with(Items.copper, 300, Items.lead, 300, Items.silicon, 150, Items.graphite, 250));

            Effect sfe = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);

            ammo(
                    Items.graphite, new BasicBulletType(7.5f, 15){{
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        shootEffect = sfe;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 4;
                        pierceCap = 2;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.berylShot;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                    }},
                    Items.titanium, new BasicBulletType(8f, 30){{
                        width = 13f;
                        height = 19f;
                        hitSize = 7f;
                        shootEffect = sfe;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 8;
                        reloadMultiplier = 1f;
                        pierceCap = 3;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.tungstenShot;
                        frontColor = Color.white;
                        trailWidth = 2.2f;
                        trailLength = 11;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        rangeChange = 40f;
                        buildingDamageMultiplier = 0.85f;
                    }}
            );
            shoot = new ShootAlternate(8f) {{
                shotDelay = 0;
            }};

            recoils = 2;
            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-barrels"){{
                    progress = PartProgress.recoil;
                    recoilIndex = 0;
                    under = true;
                    moveY = -1.5f;
                }});
            }};

            shootSound = Sounds.shootAlt;
            outlineColor = Pal.darkOutline;
            size = 3;
            reload = 10f;
            recoilTime = reload * 2f;
            coolantMultiplier = 0.5f;
            recoil = 3f;
            range = 260f;
            inaccuracy = 1.5f;
            shootCone = 10f;
            scaledHealth = 280;
            rotateSpeed = 7.5f;
        }};

        gatlingwings = new ItemTurret("gatlingwings") {{
            requirements(Category.turret, with(Items.titanium, 650, Items.silicon, 150, Items.thorium, 50, Items.plastanium, 100));

            ammo(
                    Items.titanium, new BasicBulletType(){{
                        damage = 35;
                        speed = 7f;
                        width = height = 12;
                        shrinkY = 0.2f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        velocityRnd = 0.11f;
                        collidesGround = true;
                        collidesTiles = false;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = Color.blue;
                        trailChance = 0.44f;
                        ammoMultiplier = 3f;

                        lifetime = 64f;
                        rotationOffset = 90f;
                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    Items.surgeAlloy, new BasicBulletType(){{
                        damage = 65;
                        speed = 8.5f;
                        width = height = 16;
                        shrinkY = 0.3f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        velocityRnd = 0.11f;
                        collidesGround = true;
                        collidesTiles = false;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = Pal.surge;
                        trailChance = 0.44f;
                        ammoMultiplier = 9f;

                        lifetime = 64f;
                        rotationOffset = 90f;
                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }}
            );
            reload = 9f;
            shootY = 15f;
            rotateSpeed = 5f;
            shootCone = 30f;
            consumeAmmoOnce = true;
            shootSound = Sounds.shootBig;

            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-side"){{
                              mirror = true;
                              under = true;
                              moveX = 1.75f;
                              moveY = -0.5f;
                          }},
                        new RegionPart("-top"){{
                            under = false;
                            moveY = -1.5f;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                            heatColor = Color.sky.cpy().a(0.9f);
                        }},
                        new RegionPart("-blade"){{
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.sky.cpy().a(0.9f);
                            mirror = true;
                            under = true;
                            moveY = 1f;
                            moveX = 1.5f;
                            moveRot = 8;
                        }},
                        new RegionPart("-mid"){{
                            under = true;
                        }});
            }};

            shoot = new ShootAlternate(){{
                spread = 4.7f;
                shots = 4;
                barrels = 4;
            }};

            targetGround = true;
            inaccuracy = 3f;

            shootWarmupSpeed = 0.08f;

            outlineColor = Pal.darkOutline;

            scaledHealth = 280;
            range = 390f;
            size = 4;

            coolant = consume(new ConsumeLiquid(Liquids.water, 20f / 60f));
            coolantMultiplier = 2.5f;

            limitRange(5f);
        }};
    }
}
