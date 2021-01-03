package ru.obvilion.servers;

import mindustry.Vars;
import mindustry.world.Block;

public class Server {
    public final Block block;
    public final String name;
    public final String ip;
    public final int port;
    public final int xPos, yPos;

    public final int lowBorderX, lowBorderY;
    public final int upBorderX, upBorderY;

    public Server(String name, String ip, int port, String blockName, int blockX, int blockY) {
        this.block = Vars.content.block(blockName);
        this.name = name;
        this.ip = ip;
        this.port = port;

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
