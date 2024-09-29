package advancedwarfare.util.func;

import arc.math.Interp;

public class AWInterp {
    public static final Interp inOut = a -> 2 * (0.9f * a + 0.31f) + 1f / (5f * (a + 0.1f)) - 1.6f;
}
