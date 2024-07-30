package su.shadl7.sitscore.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import su.shadl7.sitscore.PacketHandler;
import su.shadl7.sitscore.network.PacketButtonSync;

@SideOnly(Side.CLIENT)
public class GuiButtonPattern extends Gui {
    private final int selectedPattern;
    private final ItemStack pattern;

    public GuiButtonPattern (int selectedPattern) {
        this.selectedPattern = selectedPattern;
        this.pattern = TinkerRegistry.getStencilTableCrafting().get(selectedPattern);
    }

    public void draw(Minecraft mc, int x, int y) {
        var itemRender = mc.getRenderItem();
        itemRender.renderItemIntoGUI(this.pattern, x, y);
    }

    public ItemStack getPattern() {
        return pattern;
    }

    public void onClick() {
        PacketHandler.INSTANCE.sendToServer(new PacketButtonSync(selectedPattern));
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
