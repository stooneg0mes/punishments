package net.stonegomes.punishments.punishment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Punishment {

    private final PunishmentType type;
    private final String reason, author;
    private final String id;
    private final Long time, punishmentDuration;

    @Setter
    private boolean active;

    /*
    Time & duration
     */

    public long getTimeLeft() {
        return time + punishmentDuration;
    }

    public boolean hasPunishmentDuration() {
        return punishmentDuration != null;
    }

}
