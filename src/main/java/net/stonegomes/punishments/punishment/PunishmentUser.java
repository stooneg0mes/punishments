package net.stonegomes.punishments.punishment;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Getter
public class PunishmentUser {

    private final UUID uniqueId;
    private final List<Punishment> punishments;

    private boolean bypass;

    /*
    Punishments
     */

    public List<Punishment> findActivePunishments() {
        return punishments.stream()
            .filter(Punishment::isActive)
            .collect(Collectors.toList());
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

    public void removePunishment(Punishment punishment) {
        punishments.remove(punishment);
    }

    /*
    Bypass
     */

    public void setBypass(boolean bypass) {
        this.bypass = bypass;
    }

}
