package net.bozoinc.punishments.entity;

import lombok.*;
import net.bozoinc.punishments.entity.type.PunishmentType;
import org.apache.commons.lang.RandomStringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Punishment {

    private PunishmentType type;
    private String reason, author;
    private String id;

    @Setter
    private long time, timeLeft, punishmentDuration;

    @Setter
    private boolean active;

}
