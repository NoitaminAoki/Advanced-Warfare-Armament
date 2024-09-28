package advancedwarfare.util.func;

import arc.func.Boolf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import mindustry.core.World;
import mindustry.game.Team;
import mindustry.gen.Building;

import static mindustry.Vars.*;
public class AWFunc {
    private static Building tmpBuilding;
    private static final Vec2
            vec2 = new Vec2(),
            vec2Cp1 = new Vec2();

    public static Position collideBuild(Team team, float x1, float y1, float x2, float y2, Boolf<Building> boolf){
        tmpBuilding = null;

        boolean found = World.raycast(World.toTile(x1), World.toTile(y1), World.toTile(x2), World.toTile(y2),
                (x, y) -> (tmpBuilding = world.build(x, y)) != null && tmpBuilding.team != team && boolf.get(tmpBuilding));

        return found ? tmpBuilding : vec2.set(x2, y2);
    }

    public static Position collideBuildOnLength(Team team, float x1, float y1, float length, float ang, Boolf<Building> boolf){
        vec2Cp1.trns(ang, length).add(x1, y1);
        return collideBuild(team, x1, y1, vec2Cp1.x, vec2Cp1.y, boolf);
    }
}
