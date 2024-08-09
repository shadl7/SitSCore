//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cwelth.banishedcore.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportHelper extends Teleporter {
    public WorldServer wServer;
    private double X;
    private double Y;
    private double Z;
    private int dimID;

    public TeleportHelper(WorldServer worldIn, double X, double Y, double Z, int dimID) {
        super(worldIn);
        this.wServer = worldIn;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.dimID = dimID;
    }

    public void placeInPortal(Entity entityIn, float rotationYaw) {
        entityIn.setLocationAndAngles(this.X, this.Y, this.Z, entityIn.rotationYaw, entityIn.rotationPitch);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0;
    }
}
