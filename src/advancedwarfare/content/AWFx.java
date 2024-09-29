package advancedwarfare.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Log;
import mindustry.graphics.Drawf;
import mindustry.entities.Effect;
import mindustry.content.Fx;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.tilesize;

public class AWFx {
    private static final Rand rand = new Rand();

    public static Effect square(Color color, float lifetime, int num, float range, float size){
        return new Effect(lifetime, e -> {
            Draw.color(color);
            rand.setSeed(e.id);
            randLenVectors(e.id, num, range * e.finpow(), (x, y) -> {
                float s = e.fout(Interp.pow3In) * (size + rand.range(size / 3f));
                Fill.square(e.x + x, e.y + y, s, 45);
                Drawf.light(e.x + x, e.y + y, s * 2.25f, color, 0.7f);
            });
        });
    }
    public static Effect

    empedSmall = new Effect(28f, e -> {
        color(e.color, e.color.cpy().lerp(Color.white, 0.7f), e.fin());

        stroke(e.fout() * 2.4f);
        randLenVectors(e.id, 3, 3 + 25 * e.fin(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5 + 1);
        });

        color(Color.gray, Color.darkGray, e.fin());
        randLenVectors(e.id, 2, 1 + 14 * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f));
    }),

    empedLarge = new Effect(40f, e -> {
        color(e.color, e.color.cpy().lerp(Color.white, 0.7f), e.fin());

        stroke(e.fout() * 2.4f);
        randLenVectors(e.id, 4, 7 + 50 * e.fin(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5 + 1);
        });

        color(Color.gray, Color.darkGray, e.fin());
        randLenVectors(e.id, 3, 3 + 28 * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f));
    }),

    explosionWaveSmall = new Effect(Fx.chainLightning.lifetime, e -> {
        color(e.color);
        Drawf.light(e.x, e.y, e.fout() * 90f, Color.purple, 0.7f);
        e.scaled(25f, t -> {
            circle(e.x, e.y, 2f + t.fin(Interp.pow3Out) * 25f);
        });
        Fill.circle(e.x, e.y, e.fout() * 8f);
        randLenVectors(e.id + 1, 4, 1f + 25f * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 5f));

        color(Color.gray);
        Angles.randLenVectors(e.id, 8, 2.0F + 15.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4.0F + 0.5F));
    }),

    explosionWaveLarge = new Effect(50f, 160f, e -> {
        color(e.color);
        Drawf.light(e.x, e.y, e.fout() * 90f, e.color, 0.7f);
        e.scaled(25f, t -> {
            circle(e.x, e.y, 3f + t.fin(Interp.pow3Out) * 40f);
        });
        Fill.circle(e.x, e.y, e.fout() * 8f);
        randLenVectors(e.id + 1, 4, 1f + 40f * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 5f));

        color(Color.gray);
        Angles.randLenVectors(e.id, 8, 2.0F + 20.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4.0F + 0.5F));
    }),

    hugeSmokeGray = new Effect(40f, e -> {
        Draw.color(Color.gray, Color.darkGray, e.fin());
        Angles.randLenVectors(e.id, 6, 2.0F + 19.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x / 2.0F, e.y + y / 2.0F, e.fout() * 2f));
        e.scaled(25f, i -> Angles.randLenVectors(e.id, 6, 2.0F + 19.0F * i.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, i.fout() * 4.0F)));
    }),

    hitSparkLarge = new Effect(40, e -> {
        color(e.color, Color.white, e.fout() * 0.3f);
        stroke(e.fout() * 1.6f);

        rand.setSeed(e.id);
        randLenVectors(e.id, 18, e.finpow() * 27f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(4, 8) + 2f);
        });
    }),

    square45_6_45 = new Effect(45f, e-> {
        Draw.color(e.color, Color.white, e.fout() * 0.6f);
        randLenVectors(e.id, 6, 27f * e.finpow(), (x, y) -> {
            Fill.square(e.x + x, e.y + y, 5f * e.fout(), 45);
            Drawf.light(e.x + x, e.y + y, e.fout() * 9F, e.color, 0.7f);
        });
    }),

    trailToGray = new Effect(50.0F, e -> {
        Draw.color(e.color, Color.gray, e.fin());
        randLenVectors(e.id, 2, tilesize * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.rotation * e.fout()));
    }),

    lightningHitLarge = new Effect(50f, 180f, e -> {
        color(e.color);
        Drawf.light(e.x, e.y, e.fout() * 90f, e.color, 0.7f);
        e.scaled(25f, t -> {
            stroke(3f * t.fout());
            circle(e.x, e.y, 3f + t.fin(Interp.pow3Out) * 80f);
        });
        Fill.circle(e.x, e.y, e.fout() * 8f);
        randLenVectors(e.id + 1, 4, 1f + 60f * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 5f));

        color(Color.gray);
        Angles.randLenVectors(e.id, 8, 2.0F + 30.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4.0F + 0.5F));
    }),

    hitSparkHuge = new Effect(70, e -> {
        color(e.color, Color.white, e.fout() * 0.3f);
        stroke(e.fout() * 1.6f);

        rand.setSeed(e.id);
        randLenVectors(e.id, 26, e.finpow() * 65f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(6, 9) + 3f);
        });
    }),

    trailFromWhite = new Effect(50.0F, e -> {
        Draw.color(e.color, Color.white, e.fout() * 0.35f);
        randLenVectors(e.id, 2, tilesize * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.rotation * e.fout()));
    });
}
