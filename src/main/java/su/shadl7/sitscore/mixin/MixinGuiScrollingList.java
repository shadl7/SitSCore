package su.shadl7.sitscore.mixin;

import net.minecraftforge.fml.client.GuiScrollingList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import su.shadl7.sitscore.gui.GuiPatternSelector;
import su.shadl7.sitscore.gui.IScrollerHack;

@Mixin(value = GuiScrollingList.class, remap = false)
public class MixinGuiScrollingList implements IScrollerHack {
    @Shadow private float scrollDistance;

    @ModifyConstant(method = "drawScreen(IIF)V", constant = @Constant(intValue = 4))
    public int border(int constant) {
        if (((Object) this) instanceof GuiPatternSelector)
            return 0;
        else
            return constant;
    }
    @ModifyConstant(method = "drawScreen(IIF)V", constant = @Constant(intValue = 6))
    public int widthScroll(int constant) {
        if (((Object) this) instanceof GuiPatternSelector)
            return 10;
        else
            return constant;
    }

    @Override
    public float sitSCore$getScrollDistance() {
        return scrollDistance;
    }
}
