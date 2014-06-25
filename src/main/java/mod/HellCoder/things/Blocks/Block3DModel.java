package mod.HellCoder.things.Blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class Block3DModel
{
    private IModelCustom modelAludel;
    
    public static final ResourceLocation ALUDEL = getResourceLocation("models/aludel.obj");
    public static final ResourceLocation ALUDELPNG = getResourceLocation("models/aludel.png");

    public Block3DModel()
    {
        modelAludel = AdvancedModelLoader.loadModel(ALUDEL);
    }

    public void render()
    {
        modelAludel.renderPart("Base");
    }
    
    public static ResourceLocation getResourceLocation(String modId, String path)
    {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path)
    {
        return getResourceLocation("friendscraft", path);
    }
}
