package net.flytre.food_highlight.mixin;

import net.flytre.flytre_lib.api.base.math.Rectangle;
import net.flytre.flytre_lib.api.base.util.RenderUtils;
import net.flytre.food_highlight.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(method = "renderInGuiWithOverrides(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;III)V", at = @At("HEAD"))
    public void food_highlight$highlightItem(LivingEntity entity, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!stack.isFood())
            return;

        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        HungerManager hm = player.getHungerManager();
        FoodComponent component = stack.getItem().getFoodComponent();
        assert component != null;

        boolean fullHunger = component.getHunger() + hm.getFoodLevel() <= 20;
        boolean fullSaturation = component.getSaturationModifier() + hm.getSaturationLevel() < 20;
        boolean canEat = hm.isNotFull() || component.isAlwaysEdible();

        Config config = Config.HANDLER.getConfig();

        if (fullHunger && fullSaturation)
            RenderUtils.drawRectangle(new Rectangle(x, y, 16, 16), config.fullColor.value);
        else if ((fullHunger || fullSaturation) && canEat && config.renderPartialColor)
            RenderUtils.drawRectangle(new Rectangle(x, y, 16, 16), config.partialColor.value);
    }
}
