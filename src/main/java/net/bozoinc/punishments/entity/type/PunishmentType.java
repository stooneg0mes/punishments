package net.bozoinc.punishments.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PunishmentType {

    MUTE,
    TEMPORARY_MUTE,
    BAN,
    TEMPORARY_BAN,
    KICK;

}
