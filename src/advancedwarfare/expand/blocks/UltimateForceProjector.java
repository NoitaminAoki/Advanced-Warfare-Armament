package advancedwarfare.expand.blocks;

import advancedwarfare.content.AWStats;
import advancedwarfare.content.AWStatusEffects;
import arc.Core;
import arc.func.*;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

public class UltimateForceProjector extends ForceProjector {

    public @Nullable Consume enhanceConsumer;
    protected boolean preventFlyingEnemies = false;
    protected static UltimateForceBuild paramEntity;

    protected static final Cons<Unit> unitConsumer = unit -> {
        //Only control flying unit
        if(!unit.isFlying()) return;
        if(Intersector.isInRegularPolygon(((ForceProjector)(paramEntity.block)).sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), ((ForceProjector)(paramEntity.block)).shieldRotation, unit.x, unit.y)) {
            unit.vel.setZero();
            //get out

            unit.apply(AWStatusEffects.electricRooting);
        }
    };

    public UltimateForceProjector(String name) {
        super(name);
        crushDamageMultiplier = 0.7f;
    }

    @Override
    public void setStats(){
        super.setStats();
        boolean consItems = enhanceConsumer != null;

        if(consItems) stats.timePeriod = phaseUseTime;

        if(consItems && enhanceConsumer instanceof ConsumeItems coni) {
            stats.add(Stat.booster, StatValues.itemBoosters(Core.bundle.get("aw.stat.percent") + Strings.autoFixed(100, 0), stats.timePeriod, 0, 0, coni.items, this::consumesItem));
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("shield");
        addBar("shield", (UltimateForceBuild entity) -> new Bar("stat.shieldhealth", Pal.accent, () -> entity.broken ? 0f : 1f - entity.buildup / (shieldHealth + phaseShieldBoost * entity.phaseHeat)).blink(Color.white));
    }

    public class UltimateForceBuild extends ForceBuild {
        public float enhanceHeat;
        @Override
        public void updateTile() {
            super.updateTile();
            boolean enhanceValid = enhanceConsumer != null && enhanceConsumer.efficiency(this) > 0;

            enhanceHeat = Mathf.lerpDelta(enhanceHeat, Mathf.num(enhanceValid), 0.1f);

            preventFlyingEnemies = enhanceValid && !broken && efficiency > 0;

            if(enhanceValid && !broken && timer(timerUse, phaseUseTime) && efficiency > 0) {
                consume();
            }
            float rad = radius();
            if(rad > 1 && preventFlyingEnemies){
                paramEntity = this;
                Units.nearbyEnemies(team, x, y, rad + 10f, unitConsumer);
            }

        }
        public float radius(){
            return super.realRadius();
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.f(enhanceHeat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            enhanceHeat = read.f();
        }
    }
}
