package mainArm;

import mainArm.content.*;
import mindustry.mod.Mod;

public class ModModular extends Mod {
    public ModModular() {

    }

    @Override
    public void loadContent() {
        BlocksModular.load();
    }
}
