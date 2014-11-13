package mod.HellCoder.HellCoderCore.Utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class FPlayer extends EntityClientPlayerMP {

    public FPlayer(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient p_i45064_4_, StatFileWriter p_i45064_5_) {
        super(p_i45064_1_, p_i45064_2_, p_i45064_3_, p_i45064_4_, p_i45064_5_);
    }

    @Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {return false;}

    @Override
    public void heal(float p_70691_1_) {}

    @Override
    public void mountEntity(Entity p_70078_1_) {}

    @Override
    public void onUpdate() {}

    @Override
    public void sendMotionUpdates() {}

    @Override
    public EntityItem dropOneItem(boolean p_71040_1_) {return null;}

    @Override
    public void joinEntityItemWithWorld(EntityItem p_71012_1_) {}

    @Override
    public void sendChatMessage(String p_71165_1_) {}

    @Override
    public void swingItem() {}

    @Override
    public void respawnPlayer() {}

    @Override
    protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) {}

    @Override
    public void closeScreen() {}

    @Override
    public void closeScreenNoPacket() {}

    @Override
    public void setPlayerSPHealth(float p_71150_1_) {}

    @Override
    public void addStat(StatBase p_71064_1_, int p_71064_2_) {}

    @Override
    public void sendPlayerAbilities() {}

    @Override
    protected void func_110318_g() {}

    @Override
    public void func_110322_i() {}

    @Override
    public void func_142020_c(String p_142020_1_) {}

    @Override
    public String func_142021_k() {return null;}

    @Override
    public StatFileWriter getStatFileWriter() {return null;}
}
