package mainArm.content.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.*;

public class DefenseModule {
    public static Block

    //Turrets
    gunmachina, mightydao;

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
                        0f, 2f, 0f,
                        9f, 0f, 0f,
                        -9f, 0f, 0f,
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
                trailInterval = 3f;
                trailParam = 4f;
                pierceCap = 2;
                buildingDamageMultiplier = 0.5f;
                fragOnHit = false;
                speed = 5f;
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
            size = 3;
            reload = 100f;
            cooldownTime = reload;
            recoil = 3f;
            range = 420;
            shootCone = 20f;
            scaledHealth = 260;
            rotateSpeed = 1.5f;
            researchCostMultiplier = 0.04f;

            limitRange(9f);
        }};
    }
}
