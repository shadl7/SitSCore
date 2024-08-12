package su.shadl7.sitscore.mixin;

import com.teammetallurgy.atum.init.AtumRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AtumRecipes.class, remap = false)
public class MixinAtumRecipes {
    @Inject(method = "addKilnRecipes", at = @At(value = "HEAD"), cancellable = true)
    private static void addKilnRecipes(CallbackInfo ci) {
        ci.cancel();
    }
}
