package net.bozoinc.punishments.entity;

import lombok.Builder;
import lombok.Getter;
import net.bozoinc.punishments.entity.type.PunishmentType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
public class PunishedUser {

    private final UUID uuid;
    private final String name;
    private final Set<Punishment> punishments;

    public Set<Punishment> findActivePunishments() {
        Set<Punishment> activePunishments = new HashSet<>();
        for (Punishment punishment : punishments) {
            if (punishment.isActive()) activePunishments.add(punishment);
        }

        return activePunishments;
    }

    public Punishment findActivePunishment(PunishmentType... punishmentTypes) {
        return findActivePunishments().stream()
                .filter(punishment -> Arrays.asList(punishmentTypes).contains(punishment.getType()))
                .findFirst()
                .orElse(null);
    }

    public void addPunishment(Punishment punishment) {
        punishments.add(punishment);
    }

}
