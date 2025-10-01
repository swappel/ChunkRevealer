package de.swappel.chunkGenRevealer.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class ProgressScreenMixin extends Screen {

    protected ProgressScreenMixin(Text title) {
        super(title);
    }

    @Inject(
            method = "render", at = @At("HEAD"), cancellable = true
    )
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        ci.cancel();
    }
}
