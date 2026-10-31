package advancedwarfare.expand.blocks;

import advancedwarfare.content.AWStatusEffects;
import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.Nullable;
import arc.util.Scaling;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class UltimateForceProjector extends ForceProjector {

    public @Nullable Consume enhanceConsumer;

    protected static UltimateForceBuild paramEntity;

    protected static final Cons<Unit> unitConsumer = unit -> {
        // Only affect flying units
        if(!unit.isFlying()) return;

        ForceProjector proj = (ForceProjector)paramEntity.block;
        if(Intersector.isInRegularPolygon(proj.sides, paramEntity.x, paramEntity.y,
                paramEntity.realRadius(), proj.shieldRotation, unit.x, unit.y)){
            unit.vel.setZero();
            unit.apply(AWStatusEffects.electricRooting);
        }
    };

    public UltimateForceProjector(String name){
        super(name);
        crushDamageMultiplier = 0.7f;
        buildType = UltimateForceBuild::new; // without this, a plain ForceBuild is created
    }

    @Override
    public void setStats(){
        super.setStats();

        if(enhanceConsumer instanceof ConsumeItems coni){
            stats.add(Stat.booster, table -> {
                table.row();
                table.table(c -> {
                    for(ItemStack stack : coni.items){
                        c.table(Styles.grayPanel, b -> {
                            b.image(stack.item.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                            b.table(info -> {
                                info.add(stack.item.localizedName).left().row();
                                info.add(Strings.autoFixed(phaseUseTime / 60f, 2) + " " + StatUnit.seconds.localized())
                                    .color(Color.lightGray).left();
                            });
                            b.table(bt -> bt.add(Core.bundle.get("aw.stat.flyingblock")).left())
                                .right().grow().pad(10f).padRight(15f);
                        }).growX().pad(5).padBottom(-5).row();
                    }
                }).growX().colspan(table.getColumns());
                table.row();
            });
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("shield");
        addBar("shield", (UltimateForceBuild entity) -> new Bar("stat.shieldhealth", Pal.accent,
            () -> entity.broken ? 0f : 1f - entity.buildup / (shieldHealth + phaseShieldBoost * entity.phaseHeat))
            .blink(Color.white));
    }

    public class UltimateForceBuild extends ForceBuild {
        public float enhanceHeat;

        @Override
        public void updateTile(){
            super.updateTile();

            boolean enhanceValid = enhanceConsumer != null && enhanceConsumer.efficiency(this) > 0;
            enhanceHeat = Mathf.lerpDelta(enhanceHeat, Mathf.num(enhanceValid), 0.1f);

            boolean blockFlying = enhanceValid && !broken && efficiency > 0;

            if(blockFlying && timer(timerUse, phaseUseTime)){
                consume();
            }

            float rad = realRadius();
            if(rad > 1 && blockFlying){
                paramEntity = this;
                Units.nearbyEnemies(team, x, y, rad + 10f, unitConsumer);
            }
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