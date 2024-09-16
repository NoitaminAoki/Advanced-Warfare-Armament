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
        color(Color.valueOf("ffd814"), Color.valueOf("e8b500"), e.fin());

        stroke(e.fout() * 2.4f);
        randLenVectors(e.id, 3, 3 + 25 * e.fin(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5 + 1);
        });

        color(Color.gray, Color.darkGray, e.fin());
        randLenVectors(e.id, 2, 1 + 14 * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f));
    }),

    empedLarge = new Effect(40f, e -> {
        color(Color.valueOf("ffd814"), Color.valueOf("e8b500"), e.fin());

        stroke(e.fout() * 2.4f);
        randLenVectors(e.id, 4, 7 + 50 * e.fin(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5 + 1);
        });

        color(Color.gray, Color.darkGray, e.fin());
        randLenVectors(e.id, 3, 3 + 28 * e.fin(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f));
    }),

    explosionWaveSmall = new Effect(Fx.chainLightning.lifetime, e -> {
        color(e.color);
        e.scaled(7f, t -> {
            Lines.circle(e.x, e.y, t.fin() * (e.rotation + 12f));
            Log.info("[Effect:ExplosionWaveSmall] Circle Radius: " + (t.fin() * (e.rotation + 12f)));
        });
        randLenVectors(e.id, 6, e.fin() * e.rotation + 7f, (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 4 + 2f));
        Fill.circle(e.x, e.y, 2.5f * e.fout());
    }),

    explosionWaveLarge = new Effect(50f, 160f, e -> {
        color(e.color);
        Drawf.light(e.x, e.y, e.fout() * 90f, e.color, 0.7f);
        Log.info("[Effect:ExplosionWaveLarge] Light Radius: " + (e.fout() * 90f));
        e.scaled(25f, t -> {
            circle(e.x, e.y, 3f + t.fin(Interp.pow3Out) * 60f);
            Log.info("[Effect:ExplosionWaveLarge] Circle Radius: " + (3f + t.fin(Interp.pow3Out) * 60f));
        });
        Fill.circle(e.x, e.y, e.fout() * 8f);
        randLenVectors(e.id + 1, 4, 1f + 60f * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 5f));

        color(Color.gray);
        Angles.randLenVectors(e.id, 8, 2.0F + 30.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4.0F + 0.5F));
    });
}
