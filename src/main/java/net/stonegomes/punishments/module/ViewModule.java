package net.stonegomes.punishments.module;

import lombok.Getter;
import me.saiintbrisson.minecraft.ViewFrame;
import net.stonegomes.commons.module.Module;
import net.stonegomes.punishments.PunishmentsPlugin;
import net.stonegomes.punishments.view.HistoryPaginatedView;

public class ViewModule extends Module {

    @Getter
    private static final ViewFrame viewFrame = new ViewFrame(PunishmentsPlugin.getInstance());

    @Override
    public void handleEnable() {
        viewFrame.register(new HistoryPaginatedView());
    }

}
