package advancedwarfare.content;

import advancedwarfare.AdvancedWarfare;
import advancedwarfare.expand.bullets.AccelBulletType;
import advancedwarfare.expand.bullets.EndroidLaserBulletType;
import advancedwarfare.util.func.AWFunc;
import advancedwarfare.util.func.AWInterp;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Lines.*;
public class AWBullets {
    public static BulletType
    graphiteDart, titaniumDart, titanRushTech, ionizerTech, ancientBall;

    public static void load() {
        graphiteDart = new BasicBulletType(8f, 80f) {{
            lifetime = 48f;
            width = 8f;
            height = 42f;

            shrinkX = 0;

            trailWidth = 1.7f;
            trailLength = 9;

            trailColor = backColor = hitColor = lightColor = lightningColor = Pal.berylShot;
            frontColor = backColor.cpy().lerp(Color.white, 0.35f);

            shootEffect = AWFx.square(backColor, 45f, 5, 38, 4);
            smokeEffect = Fx.shootBigSmoke;

            despawnEffect = hitEffect = AWFx.explosionWaveSmall;

            splashDamage = damage;
            splashDamageRadius = 25f;

            status = StatusEffects.corroded;
            statusDuration = 120f;
            buildingDamageMultiplier = 0.5f;
            pierceCap = 2;
            pierce = true;
            pierceBuilding = true;
            ammoMultiplier = 4;
        }};

        titaniumDart = new BasicBulletType(8f, 105f) {{
            lifetime = 48f;
            width = 8f;
            height = 42f;

            shrinkX = 0;

            trailWidth = 1.7f;
            trailLength = 9;

            trailColor = backColor = hitColor = lightColor = lightningColor = Pal.berylShot;
            frontColor = backColor.cpy().lerp(Color.white, 0.35f);

            shootEffect = AWFx.square(backColor, 45f, 5, 38, 4);
            smokeEffect = Fx.shootBigSmoke;

            despawnEffect = hitEffect = AWFx.explosionWaveLarge;

            splashDamage = damage;
            splashDamageRadius = 25f;

            status = StatusEffects.corroded;
            statusDuration = 120f;
            buildingDamageMultiplier = 0.5f;
            pierceCap = 3;
            pierce = true;
            pierceBuilding = true;
            ammoMultiplier = 8;
            reloadMultiplier = 1.25f;
        }};

        titanRushTech = new FlakBulletType(8f, 70f){{
            sprite = "large-orb";

            lifetime = 45f;
            width = 12f;
            height = 22f;

            hitSize = 7f;
            shootEffect = Fx.shootSmokeSquareBig;
            smokeEffect = Fx.shootSmokeDisperse;
            ammoMultiplier = 1;
            hitColor = backColor = trailColor = lightningColor = AWColor.golden;
            frontColor = Color.white;
            trailWidth = 3f;
            trailLength = 12;
            buildingDamageMultiplier = 0.5f;

            trailEffect = Fx.colorSpark;
            trailRotation = true;
            trailInterval = 3f;
            lightning = 1;
            lightningCone = 15f;
            lightningLength = 20;
            lightningLengthRand = 30;
            lightningDamage = 20f;

//            homingPower = 0.17f;
//            homingDelay = 19f;
//            homingRange = 160f;

            explodeRange = 160f;
            explodeDelay = 0f;

            flakInterval = 20f;
            despawnShake = 3f;

            fragBullets = 3;
            fragBullet = new EndroidLaserBulletType(250f, 300f, 30f);

            fragRandomSpread = 0f;
            fragSpread = 15f;

            splashDamage = 50f;
            hitEffect = AWFx.hitSparkLarge;
//            smokeEffect = AWFx.lightningHitLarge;

            hitEffect = new Effect(90, e -> {
                Draw.color(backColor, frontColor, e.fout() * 0.7f);
                Fill.circle(e.x, e.y, e.fout() * height / 1.25f);
                Lines.stroke(e.fout() * 3f);
                Lines.circle(e.x, e.y, e.fin() * 80);
                Lines.stroke(e.fout() * 2f);
                Lines.circle(e.x, e.y, e.fin() * 50);
                Angles.randLenVectors(e.id, 35, 18 + 100 * e.fin(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 12 + 4));

                Draw.color(frontColor);
                Fill.circle(e.x, e.y, e.fout() * height / 1.75f);
            });
            despawnEffect = AWFx.hitSparkHuge;
            collidesGround = true;
        }};

        ionizerTech = new FlakBulletType(8f, 300f){{
            sprite = "missile-large";

            lifetime = 45f;
            width = 12f;
            height = 22f;

            hitSize = 7f;
            shootEffect = Fx.shootSmokeSquareBig;
//            shootEffect = new MultiEffect(
//                    new WaveEffect(){{
//                        lifetime = 35f;
//                        sizeFrom = 0;
//                        sizeTo = 20;
//                        lightColor = Color.valueOf("66B1FFFF");
//                        interp = Interp.circleOut;
//                        strokeFrom = 4;
//                        strokeTo = 0;
//                        colorFrom = Color.valueOf("66B1FFFF");
//                        colorTo = Color.valueOf("66B1FFFF");
//                    }},
//                    new ParticleEffect() {{
//                        particles = 1;
//                        sizeFrom = 9;
//                        sizeTo = 0;
//                        length = 0;
//                        layer = 144;
//                        region = AdvancedWarfare.name("plasma-medium-back");
//                        interp = Interp.swing;
//                        lifetime = 50;
//                        colorFrom = Color.valueOf("000000FF");
//                        colorTo = Color.valueOf("000000FF");
//                    }},
//                    new ParticleEffect() {{
//                        particles = 1;
//                        sizeFrom = 10;
//                        sizeTo = 0;
//                        length = 0;
//                        interp = Interp.swing;
//                        lifetime = 50;
//                        colorFrom = Color.valueOf("66B1FFFF");
//                        colorTo = Color.valueOf("66B1FFFF");
//                    }}
//            );
            smokeEffect = Fx.shootSmokeDisperse;
            ammoMultiplier = 1;
            hitColor = backColor = trailColor = lightningColor = AWColor.golden;
            frontColor = Color.white;
            trailWidth = 3f;
            trailLength = 12;
            hitEffect = despawnEffect = Fx.hitBulletColor;
            buildingDamageMultiplier = 0.3f;

            trailEffect = Fx.colorSpark;
            trailRotation = true;
            trailInterval = 3f;

            homingPower = 0.17f;
            homingDelay = 19f;
            homingRange = 160f;

            explodeRange = 160f;
            explodeDelay = 0f;

            flakInterval = 20f;
            despawnShake = 3f;

            fragBullets = 1;
            fragBullet = new BasicBulletType(0f, 1) {{
                hittable = false;
                sprite = "large-orb";
                shrinkX = shrinkY = 0f;
                width = height = 16f;
                ammoMultiplier = 1f;
//                spin = 3f;
                lifetime = 400f;

                intervalBullet = new AccelBulletType(2f, 150){
                    {
                        backColor = lightningColor = trailColor = hitColor = lightColor = AWColor.golden;
                        width = 11f;
                        height = 20f;

                        velocityBegin = 1f;
                        velocityIncrease = 11f;
                        accelInterp = AWInterp.inOut;
                        accelerateBegin = 0.045f;
                        accelerateEnd = 0.675f;

                        pierceCap = 3;
                        splashDamage = damage / 4;
                        splashDamageRadius = 24f;

                        trailLength = 30;
                        trailWidth = 3f;

                        lifetime = 60f;

                        trailEffect = AWFx.trailFromWhite;

                        pierceArmor = true;
                        trailRotation = false;
                        trailChance = 0.35f;
                        trailParam = 4f;

                        homingRange = 640F;
                        homingPower = 0.075f;
                        homingDelay = 5;

                        lightning = 3;
                        lightningLengthRand = 10;
                        lightningLength = 5;
                        lightningDamage = damage / 4;

                        shootEffect = smokeEffect = Fx.none;
                        hitEffect = despawnEffect = new MultiEffect(new Effect(65f, b -> {
                            Draw.color(b.color);

                            Fill.circle(b.x, b.y, 6f * b.fout(Interp.pow3Out));

                            Angles.randLenVectors(b.id, 6, 35 * b.fin() + 5, (x, y) -> Fill.circle(b.x + x, b.y + y, 4 * b.fout(Interp.pow2Out)));
                        }), AWFx.hitSparkLarge);

                        despawnHit = false;

                        rangeOverride = 320f;
                    }};
//                intervalBullet = new BasicBulletType(9.7f, 50){{
//                    width = 8f;
//                    height = 42f;
//                    frontColor = Color.valueOf("FFFFFF");
//                    backColor = Color.valueOf("66B1FFFF");
//                    lifetime = 80f;
//                    shrinkX = 0;
//                    shrinkY = 0;
//                    homingDelay = 10;
////                    homingPower =
//                    shootEffect = new MultiEffect(
//                            new WaveEffect(){{
//                                lifetime = 35f;
//                                sizeFrom = 0;
//                                sizeTo = 20;
//                                lightColor = Color.valueOf("66B1FFFF");
//                                interp = Interp.circleOut;
//                                strokeFrom = 4;
//                                strokeTo = 0;
//                                colorFrom = Color.valueOf("66B1FFFF");
//                                colorTo = Color.valueOf("66B1FFFF");
//                            }},
//                            new ParticleEffect() {{
//                                particles = 1;
//                                sizeFrom = 9;
//                                sizeTo = 0;
//                                length = 0;
//                                layer = 144;
//                                region = "plasma-medium-back";
//                                interp = Interp.swing;
//                                lifetime = 50;
//                                colorFrom = Color.valueOf("000000FF");
//                                colorTo = Color.valueOf("000000FF");
//                            }},
//                            new ParticleEffect() {{
//                                particles = 1;
//                                sizeFrom = 10;
//                                sizeTo = 0;
//                                length = 0;
//                                interp = Interp.swing;
//                                lifetime = 50;
//                                colorFrom = Color.valueOf("66B1FFFF");
//                                colorTo = Color.valueOf("66B1FFFF");
//                            }}
//                    );
//                    smokeEffect = Fx.shootSmallSmoke;
//                }};

                bulletInterval = 10f;
                intervalBullets = 1;
            }};
        }};

        ancientBall = new AccelBulletType(2.85f, 240f, "large-orb"){{
            frontColor = Color.white;
            backColor = lightningColor = trailColor = hitColor = lightColor = AWColor.golden;
            lifetime = 95f;

            spin = 3f;

            status = StatusEffects.none;
            statusDuration = 300f;

            accelerateBegin = 0.15f;
            accelerateEnd = 0.95f;

            despawnSound = hitSound = Sounds.titanExplosion;

            velocityBegin = 8f;
            velocityIncrease = -7.5f;

            collides = false;
            scaleLife = scaledSplashDamage = true;
            despawnHit = true;
            hitShake = despawnShake = 18f;
            lightning = 4;
            lightningCone = 360;
            lightningLengthRand = 12;
            lightningLength = 10;
            width = height = 30;
            shrinkX = shrinkY = 0;

            splashDamageRadius = 120f;
            splashDamage = 800f;

            lightningDamage = damage * 0.85f;

            hitEffect = AWFx.hitSparkLarge;
            despawnEffect = AWFx.square45_6_45;
            trailEffect = AWFx.trailToGray;

            trailLength = 15;
            trailWidth = 5f;
            drawSize = 300f;

//            shootEffect = AWFx.instShoot(backColor, frontColor);
            smokeEffect = AWFx.lightningHitLarge;

            hitEffect = new Effect(90, e -> {
                Draw.color(backColor, frontColor, e.fout() * 0.7f);
                Fill.circle(e.x, e.y, e.fout() * height / 1.25f);
                Lines.stroke(e.fout() * 3f);
                Lines.circle(e.x, e.y, e.fin() * 80);
                Lines.stroke(e.fout() * 2f);
                Lines.circle(e.x, e.y, e.fin() * 50);
                Angles.randLenVectors(e.id, 35, 18 + 100 * e.fin(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 12 + 4));

                Draw.color(frontColor);
                Fill.circle(e.x, e.y, e.fout() * height / 1.75f);
            });
            despawnEffect = AWFx.hitSparkHuge;

            fragBullets = 3;
            fragBullet = new LaserBulletType(){{
                length = 460f;
                damage = 4060f;
                width = 45f;

                status = StatusEffects.none;
                statusDuration = 120f;

                lifetime = 65f;

                splashDamage = 800;
                splashDamageRadius = 120;
                hitShake = 18f;

                lightningSpacing = 35f;
                lightningLength = 8;
                lightningDelay = 1.1f;
                lightningLengthRand = 15;
                lightningDamage = 450;
                lightningAngleRand = 40f;
                scaledSplashDamage = largeHit = true;

                lightningColor = trailColor = hitColor = lightColor = Items.surgeAlloy.color.cpy().lerp(Pal.accent, 0.055f);

                despawnHit = false;
                hitEffect = new Effect(90, 500, e -> {
                    Draw.color(backColor, frontColor, e.fout() * 0.7f);
                    Fill.circle(e.x, e.y, e.fout() * height / 1.55f);
                    Lines.stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, e.fin(Interp.pow3Out) * 80);
                    Angles.randLenVectors(e.id, 18, 18 + 100 * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 7f));

                    Draw.color(frontColor);
                    Fill.circle(e.x, e.y, e.fout() * height / 2f);
                });

                sideAngle = 15f;
                sideWidth = 0f;
                sideLength = 0f;
                colors = new Color[]{hitColor.cpy().a(0.2f), hitColor, Color.white};
            }

                @Override
                public void despawned(Bullet b){
                    //							super.despawned(b);
                }

                @Override
                public void init(Bullet b){
                    Vec2 p = new Vec2().set(AWFunc.collideBuildOnLength(b.team, b.x, b.y, length, b.rotation(), bu -> true));

                    float resultLength = b.dst(p), rot = b.rotation();

                    b.fdata = resultLength;
                    laserEffect.at(b.x, b.y, rot, resultLength * 0.75f);

                    if(lightningSpacing > 0){
                        int idx = 0;
                        for(float i = 0; i <= resultLength; i += lightningSpacing){
                            float cx = b.x + Angles.trnsx(rot,  i),
                                    cy = b.y + Angles.trnsy(rot, i);

                            int f = idx++;

                            for(int s : Mathf.signs){
                                Time.run(f * lightningDelay, () -> {
                                    if(b.isAdded() && b.type == this){
                                        Lightning.create(b, lightningColor,
                                                lightningDamage < 0 ? damage : lightningDamage,
                                                cx, cy, rot + 90*s + Mathf.range(lightningAngleRand),
                                                lightningLength + Mathf.random(lightningLengthRand));
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void draw(Bullet b){
                    float realLength = b.fdata;

                    float f = Mathf.curve(b.fin(), 0f, 0.2f);
                    float baseLen = realLength * f;
                    float cwidth = width;
                    float compound = 1f;

                    Tmp.v1.trns(b.rotation(), baseLen);

                    for(Color color : colors){
                        Draw.color(color);
                        Lines.stroke((cwidth *= lengthFalloff) * b.fout());
                        Lines.lineAngle(b.x, b.y, b.rotation(), baseLen, false);

                        Fill.circle(Tmp.v1.x + b.x, Tmp.v1.y + b.y, Lines.getStroke() * 2.2f);
                        Fill.circle(b.x, b.y, 1f * cwidth * b.fout());
                        compound *= lengthFalloff;
                    }
                    Draw.reset();
                    Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, width * 1.4f * b.fout(), colors[0], 0.6f);
                }
            };
        }};
    }
}
