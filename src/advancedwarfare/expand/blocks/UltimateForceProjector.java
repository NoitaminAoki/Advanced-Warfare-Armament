package advancedwarfare.expand.blocks;

import advancedwarfare.content.AWFx;
import advancedwarfare.content.AWStatusEffects;
import arc.func.*;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.ForceProjector;

public class UltimateForceProjector extends ForceProjector {

    protected static UltimateForceBuild paramEntity, paramBuild;

    protected static final Cons<Unit> unitConsumer = unit -> {
        //Only control flying unit
        if(!unit.isFlying()) return;
        //if this is positive, repel the unit; if it exceeds the unit radius * 2, it's inside the forcefield and must be killed
        float overlapDst = (unit.hitSize/2f + paramBuild.radius()) - unit.dst(paramBuild);

        if(overlapDst > 0){
            if(overlapDst > unit.hitSize * 1.5f){
                //instakill units that are stuck inside the shield (TODO or maybe damage them instead?)
                unit.kill();
            }else{
                //stop
                unit.vel.setZero();
                //get out
                unit.apply(AWStatusEffects.empSmall);
                unit.move(Tmp.v1.set(unit).sub(paramBuild).setLength(overlapDst + 0.01f));

//                if(Mathf.chanceDelta(0.12f * Time.delta)){
//                    Fx.circleColorSpark.at(unit.x, unit.y, paramBuild.team.color);
//                }
            }
        }
    };

    public UltimateForceProjector(String name) {
        super(name);
        sides = 24;
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("shield");
        addBar("shield", (UltimateForceBuild entity) -> new Bar("stat.shieldhealth", Pal.accent, () -> entity.broken ? 0f : 1f - entity.buildup / (shieldHealth + phaseShieldBoost * entity.phaseHeat)).blink(Color.white));
    }

    public class UltimateForceBuild extends ForceBuild {
        @Override
        public void updateTile() {
            super.updateTile();

            float rad = radius();

            if(rad > 1){
                paramBuild = this;
                Units.nearbyEnemies(team, x, y, rad + 10f, unitConsumer);
            }

        }
        public float radius(){
            return super.realRadius();
        }
    }
}
