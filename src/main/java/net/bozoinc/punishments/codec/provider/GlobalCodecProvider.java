package net.bozoinc.punishments.codec.provider;

import net.bozoinc.punishments.codec.PunishedUserCodec;
import net.bozoinc.punishments.codec.PunishmentCodec;
import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class GlobalCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == PunishedUser.class) return (Codec<T>) new PunishedUserCodec();
        if (clazz == Punishment.class) return (Codec<T>) new PunishmentCodec();

        return null;
    }

}
