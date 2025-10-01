package de.swappel.chunkGenRevealer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private MinecraftClient client;

    @Shadow
    private WorldRenderer worldRenderer;

    @Shadow
    private Frustum frustum;

    @Shadow
    private ShaderProgram shaderProgram;

    @Shadow
    private ObjectAllocator bufferAllocator;

    @Inject(
            method = "render(Lnet/minecraft/client/render/RenderTickCounter;Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderWorldEarly(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        if (this.client.currentScreen instanceof LevelLoadingScreen && this.client.world != null) {

            float f = tickCounter.getTickProgress(true);

            MatrixStack matrices = new MatrixStack();

            this.client.gameRenderer.getCamera().update(
                    this.client.world,
                    this.client.getCameraEntity(),
                    false,
                    false,
                    f
            );

            this.worldRenderer.render(
                    this.bufferAllocator,
                    tickCounter,
                    this.client.gameRenderer.shoul
            );

        }
    }

    // TODO: Fix the worldRenderer.render method and then also fix the shadow issues.

}
