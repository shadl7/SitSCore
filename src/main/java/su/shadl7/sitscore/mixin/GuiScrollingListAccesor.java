package su.shadl7.sitscore.mixin;

import net.minecraftforge.fml.client.GuiScrollingList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScrollingList.class)
public interface GuiScrollingListAccesor {
    @Accessor
    float getScrollDistance();
}
