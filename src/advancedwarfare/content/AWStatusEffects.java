package advancedwarfare.content;

import mindustry.content.StatusEffects;
import mindustry.content.Fx;
import mindustry.type.StatusEffect;

public class AWStatusEffects {
    public static StatusEffect empSmall, empHigh, absoluteFreezing;

    public static void load() {
        empSmall = new StatusEffect("emp-small") {{
            color = AWColor.electric;
            damage = 0.1f;
            effect = AWFx.empedSmall;
            effectChance = 0.4f;
            speedMultiplier = 0.75f;
            reloadMultiplier = 0.5f;
            damageMultiplier = 0.8f;
        }};

        empHigh = new StatusEffect("emp-high") {{
            color = AWColor.electric;
            damage = 0.2f;
            effect = AWFx.empedLarge;
            speedMultiplier = 0f;
            disarm = true;
        }};

        absoluteFreezing = new StatusEffect("absolute-freezing") {{
            color = AWColor.ice;
            speedMultiplier = 0f;
            healthMultiplier = 0.6f;
            effect = Fx.freezing;
            transitionDamage = 22f;

            init(() -> {
                opposite(StatusEffects.melting, StatusEffects.burning);
            });
        }};
    }
}
