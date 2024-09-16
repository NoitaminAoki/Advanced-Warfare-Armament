package advancedwarfare.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;

public class AWBullets {
    public static BulletType
    graphiteDart, titaniumDart;

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

            despawnEffect = hitEffect = AWFx.explosionWaveSmall;

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
    }
}
