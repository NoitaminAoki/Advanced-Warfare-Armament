package advancedwarfare.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.graphics.Pal;

public class AWColor {
    public static Color
            electric = Color.valueOf("3f37c9"),
            ice = Color.valueOf("6ecdec"),
            golden = Items.surgeAlloy.color.cpy().lerp(Pal.accent, 0.115f),
            goldenLight = golden.cpy().lerp(Color.white, 0.7f),
            trail = Color.lightGray.cpy().lerp(Color.gray, 0.7f),
            fireRed = Color.valueOf("C62328"),
            darkEnrColor = Pal.sapBullet.cpy().mul(1.075f).lerp(Color.white, 0.075f),
            darkEnrFront = darkEnrColor.cpy().lerp(Color.white, 0.45f);
}
