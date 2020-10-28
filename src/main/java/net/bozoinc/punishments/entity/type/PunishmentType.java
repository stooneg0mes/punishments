package net.bozoinc.punishments.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PunishmentType {

    MUTE("Silenciamento"),
    TEMPORARY_MUTE("Silenciamento temporário"),
    BAN("Ban permanente"),
    TEMPORARY_BAN("Ban temporário"),
    KICK("Kick");

    private String name;

}
