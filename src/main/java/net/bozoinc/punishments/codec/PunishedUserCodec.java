package net.bozoinc.punishments.codec;

import net.bozoinc.punishments.entity.PunishedUser;
import net.bozoinc.punishments.entity.Punishment;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class PunishedUserCodec implements Codec<PunishedUser> {

    private final DocumentCodec documentCodec = new DocumentCodec();

    @Override
    public PunishedUser decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);

        return PunishedUser.builder()
            .uuid(document.get("uuid", UUID.class))
            .name(document.getString("name"))
            .punishments(document.getList("punishments", Punishment.class))
            .build();
    }

    @Override
    public void encode(BsonWriter writer, PunishedUser value, EncoderContext encoderContext) {
        Document document = new Document();

        document.put("uuid", value.getUuid());
        document.put("name", value.getName());
        document.put("punishments", value.getPunishments());

        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<PunishedUser> getEncoderClass() {
        return PunishedUser.class;
    }

}
