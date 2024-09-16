package advancedwarfare;

import advancedwarfare.content.*;
import mindustry.mod.Mod;

public class AdvancedWarfare extends Mod {
    public static final String MOD_NAME = "advanced-warfare-armament";

    public AdvancedWarfare() {

    }

    @Override
    public void loadContent() {
        BlocksModular.load();
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }
}
