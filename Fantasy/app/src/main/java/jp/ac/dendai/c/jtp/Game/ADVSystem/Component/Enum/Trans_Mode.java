package jp.ac.dendai.c.jtp.Game.ADVSystem.Component.Enum;

/**
 * Created by テツヤ on 2016/10/06.
 */
public enum Trans_Mode {
    fade{
        @Override
        public float transition(float time,float timeBuffer,Trans_Type type){
            float alpha = timeBuffer / time;
            if(type == Trans_Type.in){
                alpha = 1 - alpha;
            }
            return Math.max(Math.min(alpha,1),0);
        }
    };

    public abstract float transition(float time,float timeBuffer,Trans_Type type);
}
