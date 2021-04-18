package net.stonegomes.punishments.punishment.serializer;

import net.stonegomes.commons.serializer.Serializer;
import net.stonegomes.punishments.punishment.Punishment;
import net.stonegomes.punishments.punishment.PunishmentType;

public class PunishmentSerializer implements Serializer<Punishment> {

    @Override
    public String serialize(Punishment value) {
        return value.getId() +
            ":" +
            value.getAuthor() +
            ":" +
            value.getReason() +
            ":" +
            value.isActive() +
            ":" +
            value.getTime() +
            ":" +
            value.getPunishmentDuration() +
            ":" +
            value.getType().toString();
    }

    @Override
    public Punishment deserialize(String key) {
        String[] split = key.split(":");

        return Punishment.builder()
            .id(split[0])
            .author(split[1])
            .reason(split[2])
            .active(Boolean.parseBoolean(split[3]))
            .time(Long.parseLong(split[4]))
            .punishmentDuration(split[5].equals("null") ? null : Long.parseLong(split[5]))
            .type(PunishmentType.valueOf(split[6]))
            .build();
    }

}
