package net.bozoinc.punishments.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bozoinc.punishments.entity.type.PunishmentType;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PunishedUser {

    private UUID uuid;
    private String name;
    private List<Punishment> punishments;

    public List<Punishment> findActivePunishments() {
        List<Punishment> activePunishments = new ArrayList<>();
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
