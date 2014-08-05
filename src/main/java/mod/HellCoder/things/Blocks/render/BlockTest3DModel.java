package mod.HellCoder.things.Blocks.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockTest3DModel  extends ModelBase{
	
	 ModelRenderer Shape1;
	    ModelRenderer Shape2;
	    ModelRenderer Shape3;
	    ModelRenderer Shape4;
	    ModelRenderer Shape5;
	    ModelRenderer Shape6;
	  
	  public BlockTest3DModel()
	  {
	    textureWidth = 64;
	    textureHeight = 32;
	    
	      Shape1 = new ModelRenderer(this, 0, 0);
	      Shape1.addBox(0F, 0F, 0F, 16, 4, 16);
	      Shape1.setRotationPoint(0F, 0F, 0F);
	      Shape1.setTextureSize(64, 32);
	      Shape1.mirror = true;
	      setRotation(Shape1, 0F, 0F, 0F);
	      Shape2 = new ModelRenderer(this, 0, 0);
	      Shape2.addBox(0F, 0F, 0F, 4, 8, 4);
	      Shape2.setRotationPoint(6F, 4F, 10F);
	      Shape2.setTextureSize(64, 32);
	      Shape2.mirror = true;
	      setRotation(Shape2, 3.141593F, 0F, 0F);
	      Shape3 = new ModelRenderer(this, 0, 0);
	      Shape3.addBox(0F, 0F, 0F, 1, 1, 1);
	      Shape3.setRotationPoint(6F, -5F, 9F);
	      Shape3.setTextureSize(64, 32);
	      Shape3.mirror = true;
	      setRotation(Shape3, 0F, 0F, 0F);
	      Shape4 = new ModelRenderer(this, 0, 0);
	      Shape4.addBox(0F, 0F, 0F, 1, 1, 1);
	      Shape4.setRotationPoint(6F, -5F, 6F);
	      Shape4.setTextureSize(64, 32);
	      Shape4.mirror = true;
	      setRotation(Shape4, 0F, 0F, 0F);
	      Shape5 = new ModelRenderer(this, 0, 0);
	      Shape5.addBox(0F, 0F, 0F, 1, 5, 1);
	      Shape5.setRotationPoint(6F, -4F, 8.5F);
	      Shape5.setTextureSize(64, 32);
	      Shape5.mirror = true;
	      setRotation(Shape5, 1.570796F, 1.570796F, 0F);
	      Shape6 = new ModelRenderer(this, 0, 0);
	      Shape6.addBox(0F, 0F, 0F, 1, 1, 1);
	      Shape6.setRotationPoint(10.5F, -5F, 7.5F);
	      Shape6.setTextureSize(64, 32);
	      Shape6.mirror = true;
	      setRotation(Shape6, 0F, 0F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    Shape1.render(f5);
	    Shape2.render(f5);
	    Shape3.render(f5);
	    Shape4.render(f5);
	    Shape5.render(f5);
	    Shape6.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) //Add Entity entity here
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity); //Add entity here
	  }

}
