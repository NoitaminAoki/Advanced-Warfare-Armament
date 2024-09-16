package advancedwarfare.content;

import advancedwarfare.AdvancedWarfare;
import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;

public class AWContent extends Content {
    public static TextureRegion missilePathRegion, launcherRegion;

    @Override
    public ContentType getContentType(){
        return ContentType.error;
    }

    public static void loadPriority() {
        new AWContent().load();
    }

    public void load() {
        if(Vars.headless) return;

        missilePathRegion = Core.atlas.find(AdvancedWarfare.name("missile-arrow"));
    }
}
