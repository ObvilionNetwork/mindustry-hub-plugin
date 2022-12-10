package ru.obvilion.servers;

import mindustry.Vars;
import mindustry.world.Block;

public class Server {
    public final Block block;
    public final String name;
    public final String ip;
    public final int port;
    public final int xPos;
    public final int yPos;

    public final int lowBorderX;
    public final int lowBorderY;

    public final int upBorderX;
    public final int upBorderY;

    public final boolean notIncludeOnline;

    public Server(String name, String ip, int port, String blockName, int blockX, int blockY, boolean notIncludeOnline) {
        this.block = Vars.content.block(blockName);
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.notIncludeOnline = notIncludeOnline;

        xPos = blockX;
        yPos = blockY;

        lowBorderX = xPos - 1;
        lowBorderY = yPos - 1;
        upBorderX = xPos + block.size + 1;
        upBorderY = yPos + block.size + 1;
    }

    public boolean check(int x, int y) {
        return x > lowBorderX && x < upBorderX && y > lowBorderY && y < upBorderY;
    }
}
