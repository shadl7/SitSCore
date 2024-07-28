package su.shadl7.sitscore.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.tools.common.block.BlockToolTable;
import su.shadl7.sitscore.tileentity.TilePartBuilderEx;

@Mixin(value = BlockToolTable.class)
public class MixinBlockToolTable {
    @Inject(method = "createNewTileEntity", at= @At(value = "NEW",
            target = "()Lslimeknights/tconstruct/tools/common/tileentity/TilePartBuilder;"), cancellable = true)
    public void createNewTileEntity(World worldIn, int meta, CallbackInfoReturnable<TileEntity> cir) {
      cir.setReturnValue(new TilePartBuilderEx());
    }
}
