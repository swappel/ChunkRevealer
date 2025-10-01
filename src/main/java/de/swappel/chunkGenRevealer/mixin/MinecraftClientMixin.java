package de.swappel.chunkGenRevealer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MinecraftClientMixin {

    @Shadow
    private MinecraftClient client;

    @Inject(
            // This is the likely signature. If it fails, check the exact parameters.
            method = "render(Lnet/minecraft/client/render/RenderTickCounter;Z)V",
            // Inject at the very beginning of the method
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderWorldEarly(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {

        if (this.client.currentScreen instanceof LevelLoadingScreen && this.client.world != null) {
            ci.cancel();
        }

    }

}
