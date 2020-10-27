package net.bozoinc.punishments.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bozoinc.punishments.entity.type.PunishmentType;
import org.apache.commons.lang.RandomStringUtils;

@Builder
@Getter
public class Punishment {

    private final PunishmentType type;
    private final String reason, author;
    private final long time, punishmentTime;

    private final String id = RandomStringUtils.random(7, true, true).toUpperCase();

    @Setter
    private boolean active;

}
