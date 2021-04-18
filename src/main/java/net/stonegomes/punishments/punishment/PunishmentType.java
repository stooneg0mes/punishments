package net.stonegomes.punishments.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PunishmentType {

    MUTE("Mute"),
    TEMPORARY_MUTE("Temporary mute"),
    BAN("Permanent ban"),
    TEMPORARY_BAN("Temporary ban"),
    KICK("Kick");

    private final String name;

}
