package stan.ripto.materialgenerators.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stan.ripto.materialgenerators.MaterialGenerators;

@Mixin(ResourceLocation.class)
public class RLMixin {
    @Shadow
    @Final
    private String namespace;

    @Shadow
    @Final
    private String path;

    @Inject(
            method = "<init>(Ljava/lang/String;Ljava/lang/String;)V",
            at = @At("TAIL")
    )
    public void materialgenerators$init(String pNamespace, String pPath, CallbackInfo ci) {
        if (pPath.isEmpty()) {
            MaterialGenerators.LOGGER.info("こんにちは");
        }
    }
}
