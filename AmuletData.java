package com.example.amuletmod.screen;

import com.example.amuletmod.AmuletMod;
import com.example.amuletmod.item.AmuletData;
import com.example.amuletmod.item.AmuletItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AmuletTableScreen extends HandledScreen<AmuletTableScreenHandler> {

    private static final Identifier TEXTURE = AmuletMod.id("textures/gui/amulet_table.png");

    public AmuletTableScreen(AmuletTableScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 186;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        // Add 8 buttons for stat upgrades
        int startX = this.x + 50;
        int startY = this.y + 18;
        for (int i = 0; i < AmuletData.STATS.length; i++) {
            final int idx = i;
            int row = i / 4;
            int col = i % 4;
            ButtonWidget btn = ButtonWidget.builder(
                    Text.literal("+" + AmuletData.STAT_NAMES_RU[i].substring(0, Math.min(4, AmuletData.STAT_NAMES_RU[i].length()))),
                    button -> {
                        MinecraftClient mc = MinecraftClient.getInstance();
                        ClientPlayerInteractionManager im = mc.interactionManager;
                        if (im != null) {
                            im.clickButton(handler.syncId, idx);
                        }
                    }
            ).dimensions(startX + col * 19, startY + row * 11, 18, 10).build();
            addDrawableChild(btn);
        }
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        // Use a vanilla-ish background. If you provide a texture at TEXTURE path, replace below:
        ctx.fill(this.x, this.y, this.x + backgroundWidth, this.y + backgroundHeight, 0xFF8B8B8B);
        ctx.fill(this.x + 4, this.y + 4, this.x + backgroundWidth - 4, this.y + backgroundHeight - 4, 0xFFC6C6C6);
        // slot outlines
        ctx.fill(this.x + 25, this.y + 46, this.x + 43, this.y + 64, 0xFF8B8B8B);
        ctx.fill(this.x + 133, this.y + 46, this.x + 151, this.y + 64, 0xFF8B8B8B);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        drawMouseoverTooltip(ctx, mouseX, mouseY);

        // Show stat levels next to buttons
        ItemStack amulet = handler.getSlot(0).getStack();
        if (amulet.getItem() instanceof AmuletItem) {
            int xs = this.x + 8;
            int ys = this.y + 70;
            for (int i = 0; i < AmuletData.STATS.length; i++) {
                int lvl = AmuletData.getLevel(amulet, AmuletData.STATS[i]);
                ctx.drawText(this.textRenderer,
                        AmuletData.STAT_NAMES_RU[i] + ": " + lvl,
                        xs + (i % 2) * 80, ys + (i / 2) * 9, 0x404040, false);
            }
        } else {
            ctx.drawText(this.textRenderer, "Положите амулет в левый слот",
                    this.x + 30, this.y + 75, 0x404040, false);
            ctx.drawText(this.textRenderer, "и алмазы в правый слот",
                    this.x + 30, this.y + 85, 0x404040, false);
        }
    }
}
