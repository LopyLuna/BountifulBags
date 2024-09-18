package uwu.lopyluna.bbags.infrastructure;

import net.minecraftforge.data.event.GatherDataEvent;

public class Datagen {
    public static void gatherData(GatherDataEvent event) {
        RegistrateTags.addGenerators();
    }
}
