package net.bozoinc.punishments.codec;

import net.bozoinc.punishments.entity.Punishment;
import net.bozoinc.punishments.entity.type.PunishmentType;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;

import javax.print.Doc;

public class PunishmentCodec implements Codec<Punishment> {

    private final Codec<Document> documentCodec = new DocumentCodec();

    @Override
    public Punishment decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);

        return Punishment.builder()
            .id(document.getString("id"))
            .author(document.getString("author"))
            .reason(document.getString("reason"))
            .active(document.getBoolean("active"))
            .type(document.get("type", PunishmentType.class))
            .time(document.getLong("time"))
            .timeLeft(document.getLong("timeLeft"))
            .punishmentDuration(document.getLong("punishmentDuration"))
            .build();
    }

    @Override
    public void encode(BsonWriter writer, Punishment value, EncoderContext encoderContext) {
        Document document = new Document();

        document.put("id", value.getId());
        document.put("author", value.getAuthor());
        document.put("reason", value.getReason());
        document.put("active", value.isActive());
        document.put("type", value.getType());
        document.put("time", value.getTime());
        document.put("timeLeft", value.getTimeLeft());
        document.put("punishmentDuration", value.getPunishmentDuration());

        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<Punishment> getEncoderClass() {
        return Punishment.class;
    }

}
