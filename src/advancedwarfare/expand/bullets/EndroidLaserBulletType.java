package advancedwarfare.expand.bullets;

import advancedwarfare.content.AWColor;
import advancedwarfare.util.func.AWFunc;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.*;

public class EndroidLaserBulletType extends LaserBulletType {
    protected float bulletLength = 460f;
    protected float bulletDamage = 800f;
    protected float bulletWidth = 45f;
    public float height = 30f;
    public Color backColor = Pal.bulletYellowBack, frontColor = Color.white;

    public EndroidLaserBulletType(float length, float damage, float width) {
        this();
        this.length = length;
        this.damage = damage;
        this.width = width;
    }
    public EndroidLaserBulletType() {
        super();
        length = bulletLength;
        damage = bulletDamage;
        width = bulletWidth;

        status = StatusEffects.electrified;
        statusDuration = 120f;

        lifetime = 65f;

        splashDamage = 200;
        splashDamageRadius = 120;
        hitShake = 12f;

        lightningSpacing = 20f;
        lightningLength = 5;
        lightningDelay = 1.1f;
        lightningLengthRand = 15;
        lightningDamage = 100;
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
        //super.despawned(b);
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

}
