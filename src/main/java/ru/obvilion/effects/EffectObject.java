package ru.obvilion.effects;

import arc.graphics.Color;
import mindustry.entities.Effect;
import mindustry.gen.Call;
import mindustry.net.NetConnection;

public class EffectObject {
    public final Effect effect;
    public final Color color;

    public final int rotation;
    public final float xPos;
    public final float yPos;

    public EffectObject(Effect effect, float x, float y, int rotation, Color color) {
        this.effect = effect;
        this.color = color;
        this.rotation = rotation;

        xPos = x;
        yPos = y;
    }

    public void draw(NetConnection con) {
        Call.effect(con, effect, xPos, yPos, rotation, color);
    }

    public void draw() {
        Call.effect(effect, xPos * 8, yPos * 8, rotation, color);
    }
}
