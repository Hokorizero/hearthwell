package wolforce.client.models;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import wolforce.entities.EntityPower;

public abstract class MyRender<T extends Entity> extends Render<T> {

	ResourceLocation resloc;
	private ModelBase model;

	public MyRender(RenderManager renderManager, ModelBase model, ResourceLocation resloc) {
		super(renderManager);
		this.model = model;
		this.resloc = resloc;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return resloc;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		setupTranslation(x, y, z);
		// this.setup(entity, x, y, z, entityYaw, partialTicks);
		this.bindTexture(resloc);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.model.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	// public abstract void setup(T entity, double x, double y, double z, float
	// entityYaw, float partialTicks);
	// {
	// GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
	// float f = (float) p_188311_1_.getTimeSinceHit() - p_188311_3_;
	// float f1 = p_188311_1_.getDamageTaken() - p_188311_3_;
	//
	// if (f1 < 0.0F) {
	// f1 = 0.0F;
	// }
	//
	// if (f > 0.0F) {
	// GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * (float)
	// p_188311_1_.getForwardDirection(), 1.0F, 0.0F, 0.0F);
	// }
	// GlStateManager.scale(1.0F, 1.0F, 1.0F);
	// }

	public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_) {
		GlStateManager.translate((float) p_188309_1_, (float) p_188309_3_ + 0.375F, (float) p_188309_5_);
	}

}
