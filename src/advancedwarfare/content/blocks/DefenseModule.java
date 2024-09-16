package advancedwarfare.content.blocks;

import advancedwarfare.content.AWBullets;
import advancedwarfare.expand.block.drawer.DrawMissilePathSequence;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.*;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;


public class DefenseModule {
    public static Block

    //Turrets
    //Turret - Base 2
    orverdriveduo, lightningswords,
    //Turret - Base 3
    gunmachina, rapidwings, thunderlance, lockmirrae, attaraxia,
    //Turret - Base 4
    gatlingwings,
    //Turret - Base 5
    mightydao;

    public static void load() {

        orverdriveduo = new ItemTurret("overdriveduo") {{
            requirements(Category.turret, with(Items.copper, 200, Items.lead, 200, Items.silicon, 80), true);

            ammo(
                    Items.graphite, new BasicBulletType(4f, 20){{
                        width = 9f;
                        height = 12f;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                    }},
                    Items.silicon, new BasicBulletType(5f, 15){{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.1f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                    }}
            );
            shoot = new ShootAlternate() {{
                spread = 3.5f;
                shots = 2;
                barrels = 2;
            }};
            recoils = 2;
            drawer = new DrawTurret();

            outlineColor = Pal.darkOutline;
            size = 2;
            recoil = 0.5f;
            shootY = 3f;
            reload = 10f;
            range = 250;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 350;
            inaccuracy = 1f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);

            limitRange();
        }};

        lightningswords = new PowerTurret("lightningswords") {{
            requirements(Category.turret, with(Items.copper, 120, Items.lead, 95, Items.silicon, 80, Items.titanium, 50), true);
            shootType = new RailBulletType(){{
                length = 218f;
                damage = 80f;
                hitColor = Color.valueOf("feb380");
                hitEffect = endEffect = Fx.hitBulletColor;
                pierceDamageFactor = 0.8f;

                smokeEffect = Fx.colorSpark;

                endEffect = new Effect(14f, e -> {
                    color(e.color);
                    Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                });

                shootEffect = new Effect(10, e -> {
                    color(e.color);
                    float w = 1.2f + 7 * e.fout();

                    Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                    color(e.color);

                    for(int i : Mathf.signs){
                        Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                    }

                    Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                });

                lineEffect = new Effect(20f, e -> {
                    if(!(e.data instanceof Vec2 v)) return;

                    color(e.color);
                    stroke(e.fout() * 0.9f + 0.6f);

                    Fx.rand.setSeed(e.id);
                    for(int i = 0; i < 7; i++){
                        Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                        Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                    }

                    e.scaled(14f, b -> {
                        stroke(b.fout() * 1.5f);
                        color(e.color);
                        Lines.line(e.x, e.y, v.x, v.y);
                    });
                });
            }};

            size = 2;
            reload = 12f;
            range = 210f;
            shootY = 10f;
            recoil = 1f;
            rotateSpeed = 5f;
            shootCone = 2f;
            cooldownTime = 20f;
            shoot = new ShootAlternate(3.5f);

            drawer = new DrawTurret();
            consumePower(1.5f);
        }};

        gunmachina = new ItemTurret("gunmachina") {{
           requirements(Category.turret, with(Items.copper, 200, Items.titanium, 300, Items.silicon, 50), true);
           ammo(
                   Items.graphite, new FlakBulletType(7f, 25) {{
                       ammoMultiplier = 6f;
                       shootEffect = Fx.shootSmall;
                       width = 6f;
                       height = 8f;
                       knockback = 0.3f;
                       reloadMultiplier = 1.7f;
                       collidesGround = true;
                   }},
                   Items.pyratite, new FlakBulletType(7f, 55){{
                       shootEffect = Fx.shootSmall;
                       ammoMultiplier = 9f;
                       splashDamage = 20f;
                       splashDamageRadius = 30f;
                       collidesGround = true;

                       status = StatusEffects.burning;
                       statusDuration = 30f;
                   }},
                   Items.blastCompound, new FlakBulletType(7f, 70){{
                       shootEffect = Fx.shootBig;
                       ammoMultiplier = 12f;
                       splashDamage = 45f;
                       splashDamageRadius = 60f;
                       collidesGround = true;

                       status = StatusEffects.blasted;
                       statusDuration = 60f;
                   }},
                   Items.surgeAlloy, new FlakBulletType(8.5f, 95){{
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

        rapidwings = new ItemTurret("rapidwings") {{
            requirements(Category.turret, with(Items.copper, 300, Items.lead, 300, Items.silicon, 150, Items.graphite, 250), true);

            ammo(
                    Items.graphite, AWBullets.graphiteDart,
                    Items.titanium, AWBullets.titaniumDart
            );

            shoot = new ShootMulti(new ShootPattern(), new ShootBarrel(){{
                barrels = new float[]{-6.5f, 10f, 0f};
            }}, new ShootBarrel(){{
                barrels = new float[]{6.5f, 10f, 0f};
            }});

            recoils = 1;
            drawer = new DrawTurret(){{
                parts.add(
                        new RegionPart("-barrels"){{
                            progress = PartProgress.recoil;
                            recoilIndex = 0;
                            under = true;
                            moveY = -1.0f;
                        }},
                        new RegionPart("-main") {{
                            progress = PartProgress.recoil;
                            moveY = -0.2f;
                        }}
                );
            }};

            shootSound = Sounds.shootAlt;
            outlineColor = Pal.darkOutline;
            size = 3;
            reload = 10f;
            recoilTime = reload * 2f;
            coolantMultiplier = 0.5f;
            recoil = 3f;
            range = 310f;
            inaccuracy = 1.5f;
            shootCone = 10f;
            scaledHealth = 280;
            rotateSpeed = 7.5f;
            maxAmmo = 80;
        }};

        thunderlance = new ItemTurret("thunderlance") {{
            requirements(Category.turret, with(Items.copper, 180, Items.lead, 140, Items.silicon, 120, Items.titanium, 80), true);
            range = 395f;

            ammo(
                    Items.titanium, new LaserBulletType(){{
                        colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                        
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);

                        length = 403f;
                        width = 40f;
                        damage = 250f;

                        lifetime = 65f;
                        largeHit = true;
                        buildingDamageMultiplier = 0.7f;
                        hitEffect = Fx.hitLancer;
                        collidesAir = true;
                        ammoMultiplier = 3f;
                        pierceCap = 4;
                    }}
            );
            shoot = new ShootAlternate() {{
                spread = 11f;
                shots = 2;
                barrels = 2;
                firstShotDelay = 40f;
            }};

            recoil = 3f;
            drawer = new DrawTurret("reinforced-"){{
                parts.add(
                        new RegionPart("-blade"){{
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.sky.cpy().a(0.9f);
                            mirror = true;
                            under = true;
                            moveY = -1.5f;
                        }},
                        new RegionPart("-back"){{
                            mirror = true;
                            under = true;
                            moveX = 1.75f;
                            moveY = -0.5f;
                        }}
                );
            }};

            reload = 80f;
            shake = 2f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            heatColor = Color.red;
            size = 3;
            scaledHealth = 280;
            moveWhileCharging = true;
            accurateDelay = true;
            shootSound = Sounds.laser;
            maxAmmo = 40;
            rotateSpeed = 6f;
            shootCone = 5f;

            coolantMultiplier = 0.4f;
            coolant = consumeCoolant(0.3f);

            consumePower(6f);
        }};

        lockmirrae = new ItemTurret("lockmirrae") {{
            requirements(Category.turret, with(Items.copper, 200, Items.lead, 200, Items.silicon, 110, Items.graphite, 150), true);
            shootSound = Sounds.missileSmall;

            ammo(
                    Items.silicon, new BulletType(){{
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootBigSmoke2;
                        shake = 1f;
                        speed = 0f;
                        keepVelocity = false;
                        collidesAir = true;

                        spawnUnit = new MissileUnitType("lockmirrae-missile"){{
                            targetAir = true;
                            speed = 5.3f;
                            maxRange = 6f;
                            lifetime = 60f * 2.2f;
                            outlineColor = Color.valueOf("272930FF");
                            engineColor = trailColor = Color.valueOf("A9D8FFFF");
                            engineLayer = Layer.effect;
                            health = 95;
                            loopSoundVolume = 0.1f;
                            trailLength = 12;

                            weapons.add(new Weapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                shootOnDeath = true;
                                bullet = new ExplosionBulletType(650f, 40f){{
                                    collidesAir = true;
                                    shootEffect = new MultiEffect(
                                            new WaveEffect() {{
                                                lifetime = 15;
                                                sizeFrom = 0;
                                                sizeTo = 30;
                                                strokeFrom = 7.5f;
                                                strokeTo = 0;
                                                colorFrom = Color.valueOf("A9D8FFFF");
                                                colorTo = Color.valueOf("66B1FFFF");
                                            }},
                                            new ParticleEffect() {{
                                                offset = 30;
                                                particles = 5;
                                                lifetime = 20;
                                                length = 50;
                                                interp = Interp.circleOut;
                                                sizeInterp = Interp.pow5In;
                                                cone = -360;
                                                line = true;
                                                strokeFrom = 1.5f;
                                                strokeTo = 1.5f;
                                                lenFrom = 5;
                                                lenTo = 5;
                                                lightColor = Color.valueOf("A9D8FFFF");
                                                colorFrom = Color.valueOf("E1F2FFFF");
                                                colorTo = Color.valueOf("A9D8FFFF");
                                            }}
                                    );
                                }};
                            }});

                            abilities.add(new MoveEffectAbility(){{
                                effect = new ParticleEffect(){{
                                   particles = 4;
                                   sizeFrom = 4;
                                   sizeTo = 1;
                                   length = 30;
                                   lifetime = 30;
                                   lightOpacity = 0;
                                   interp = Interp.circleOut;
                                   sizeInterp = Interp.pow5In;
                                   colorFrom = Color.valueOf("66B1FFAa");
                                   colorTo = Color.valueOf("78787870");
                                   cone = 9;
                                }};
                                interval = 2;
                                y = -6;
                                rotateEffect = true;
                                rotation = 180;
                            }});
                        }};
                    }}
            );
            shootWarmupSpeed = 0.1f;
            shootY = 12f;
            shootCone = 20f;
            shoot.shots = 3;
            shoot.shotDelay = 4.5f;
            reload = 120f;
            range = 480f;
            scaledHealth = 380;
            size = 3;

            drawer = new DrawTurret(){{
                parts.add(
                        new RegionPart("-launcher"){{
                            mirror = true;
                            under = false;
                            moveX = 2.35f;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                            heatColor = Color.sky.cpy().a(0.9f);
                        }},
                        new RegionPart("-top"){{
                            under = false;
                            moveY = -1.5f;
                        }},
                        new DrawMissilePathSequence() {{
                            x = 0;
                            y = 2f;
                            arrows = 5;
                            color = Pal.surge;
                            colorTo = Color.red;
                            colorToFinScl = 0.12f;
                        }}
                        );
            }};

            coolant = consume(new ConsumeLiquid(Liquids.water, 20f / 60f));
            coolantMultiplier = 2.5f;
            limitRange();
        }};

        attaraxia = new ItemTurret("attaraxia") {{
            requirements(Category.turret, with(Items.copper, 180, Items.lead, 140, Items.silicon, 120, Items.titanium, 80), true);
            shootSound = Sounds.blaster;

            ammo(
                    Items.graphite,  new BasicBulletType(5f, 60){{
                        width = 10f;
                        height = 15f;
                        shootEffect = Fx.sparkShoot;
                        smokeEffect = Fx.shootBigSmoke;
                        hitColor = backColor = trailColor = Pal.suppress;
                        frontColor = Color.white;
                        trailWidth = 2f;
                        trailLength = 5;
                        despawnEffect = hitEffect = new ExplosionEffect(){{
                            waveColor = Pal.sapBullet;
                            smokeColor = Color.gray;
                            sparkColor = Pal.sap;
                            waveStroke = 4f;
                            waveRad = 20f;
                        }};
                    }}
            );

            shoot = new ShootHelix();

            drawer = new DrawTurret(){{
                parts.add(new RegionPart("-cannon"){{
                    under = true;
                    moveY = -0.5f;
                    progress = PartProgress.recoil;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = Color.sky.cpy().a(0.9f);
                }});
            }};

            shootY = 10f;
            range = 340f;
            shootCone = 20f;
            health = 350;
            size = 3;
            inaccuracy = 1f;
            rotateSpeed = 8f;
            reload = 12f;
            coolant = consumeCoolant(0.2f);
            coolantMultiplier = 2.5f;
            limitRange(9);
        }};

        gatlingwings = new ItemTurret("gatlingwings") {{
            requirements(Category.turret, with(Items.titanium, 650, Items.silicon, 150, Items.thorium, 50, Items.plastanium, 100), true);

            ammo(
                    Items.titanium, new BasicBulletType(){{
                        damage = 55;
                        speed = 7f;
                        width = height = 12;
                        shrinkY = 0.2f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        velocityRnd = 0.11f;
                        pierceBuilding = true;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = Color.magenta;
                        trailChance = 0.44f;
                        ammoMultiplier = 3f;
                        buildingDamageMultiplier = 0.8f;

                        lifetime = 64f;
                        rotationOffset = 90f;
                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    Items.surgeAlloy, new BasicBulletType(){{
                        damage = 105;
                        speed = 8.5f;
                        width = height = 16;
                        shrinkY = 0.3f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        velocityRnd = 0.11f;
                        pierceBuilding = true;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = Pal.surge;
                        trailChance = 0.44f;
                        ammoMultiplier = 9f;
                        buildingDamageMultiplier = 0.8f;

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

        mightydao = new PowerTurret("mightydao") {{
            requirements(Category.turret, with(Items.graphite, 200, Items.titanium, 200, Items.silicon, 350, Items.plastanium, 20, Items.surgeAlloy, 5), true);

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
                buildingDamageMultiplier = 0.75f;
                fragOnHit = false;
                speed = 7f;
                damage = 450f;
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

                shootSound = Sounds.cannon;

                fragBullet = intervalBullet = new BasicBulletType(3f, 65){{
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
                    buildingDamageMultiplier = 0.5f;
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
            size = 5;
            reload = 85f;
            cooldownTime = reload;
            recoil = 3f;
            range = 520;
            shootCone = 20f;
            scaledHealth = 460;
            rotateSpeed = 5f;
            researchCostMultiplier = 0.04f;

            limitRange(9f);
        }};
    }
}
