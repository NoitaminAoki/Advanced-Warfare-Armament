package advancedwarfare.expand.bullets;

import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Player;
import mindustry.gen.Unit;

public class AccelBulletType extends BasicBulletType {
    public float velocityBegin = -1;
    public float velocityIncrease = 0;
    public float accelerateBegin = 0.1f;
    public float accelerateEnd = 0.6f;
    public Unit player1, player2;

    public Interp accelInterp = Interp.linear;

    public void disableAccel(){
        accelerateBegin = 10;
    }

    public AccelBulletType(){
        super();
    }

    public AccelBulletType(float velocityBegin, float velocityIncrease, Interp accelInterp, float damage, String bulletSprite){
        super(1, damage, bulletSprite);
        this.velocityBegin = velocityBegin;
        this.velocityIncrease = velocityIncrease;
        this.accelInterp = accelInterp;
    }

    public AccelBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
    }

    public AccelBulletType(float speed, float damage, Unit player1, Unit player2) {
        this(speed, damage, "bullet");
        this.player1 = player1;
        this.player2 = player2;
    }

    public AccelBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public AccelBulletType(float damage, String bulletSprite){
        super(1, damage, bulletSprite);
    }

    @Override
    protected float calculateRange(){
        if(velocityBegin < 0)velocityBegin = speed;

        boolean computeRange = rangeOverride < 0;
        float cal = 0;

        FloatSeq speeds = new FloatSeq();
        for(float i = 0; i <= 1; i += 0.05f){
            float s = velocityBegin + accelInterp.apply(Mathf.curve(i, accelerateBegin, accelerateEnd)) * velocityIncrease;
            speeds.add(s);
            if(computeRange)cal += s * lifetime * 0.05f;
        }
        speed = speeds.sum() / speeds.size;

        if(computeRange) {
            cal += 1;
        };

        return cal;
    }
    @Override
    public void init(){
        super.init();
    }

    /**
     * Use for Later
     */
//    @Override
//    public void updateHoming(Bullet b){
//        if(b.time >= this.homingDelay){
//            if(b.owner instanceof Unit){
//                Unit u = (Unit)b.owner;
//                if(u.isPlayer()){
//                    Player p = u.getPlayer();
//                    b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(p.mouseX, p.mouseY), homingPower * Time.delta * 50.0F));
//                }else super.updateHoming(b);
//            } else if(Vars.player != null) {
//                Player p = Vars.player;
//                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(p.mouseX, p.mouseY), homingPower * Time.delta * 50.0F));
//            } else super.updateHoming(b);
//        }
//    }
    @Override
    public void update(Bullet b){
        Log.info("[Check update:AccelBulletType] begin accelerate: " + (accelerateBegin < 1));
        if(accelerateBegin < 1)b.vel.setLength((velocityBegin + accelInterp.apply(Mathf.curve(b.fin(), accelerateBegin, accelerateEnd)) * velocityIncrease) * (drag != 0 ? (1 * Mathf.pow(b.drag, b.fin() * b.lifetime() / 6)) : 1));
        super.update(b);
    }
}
