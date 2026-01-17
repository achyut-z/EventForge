package io.eventforge.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Event {

    private final UUID id;

    private final String type;

    private final Instant occurredAt;

    private final String source;

    private final Map<String, Object> payload;

    private final int version;

    private final Map<String, String> metadata;

    private Event(UUID id, String type, Instant occurredAt, String source, Map<String, Object> payload, int version, Map<String, String> metadata) {

        if (id == null) throw new IllegalArgumentException("ID cannot be null.");
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Type cannot be null or blank.");
        if (occurredAt == null) throw new IllegalArgumentException("Occurred at cannot be null.");
        if (source == null || source.isBlank()) throw new IllegalArgumentException("Source cannot be null or blank.");
        if (payload == null) throw new IllegalArgumentException("Payload cannot be null.");
        if (version <= 0) throw new IllegalArgumentException("Version must be positive.");
        if (metadata == null) throw new IllegalArgumentException("Metadata cannot be null.");

        this.id = id;
        this.type = type;
        this.occurredAt = occurredAt;
        this.source = source;
        this.payload = defensiveCopy(payload);
        this.version = version;
        this.metadata = defensiveCopy(metadata);
    }

    private <K, V> Map<K, V> defensiveCopy(Map<K, V> sourceObject) {
        return Map.copyOf(sourceObject);
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public int getVersion() {
        return version;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public static Event create(String type, Instant occurredAt, String source, Map<String, Object> payload, int version, Map<String, String> metadata) {

        return new Event(UUID.randomUUID(), type, occurredAt, source, payload, version, metadata);
    }

    public static Event createWithoutMetadata(String type, Instant occurredAt, String source, Map<String, Object> payload, int version) {

        return new Event(UUID.randomUUID(), type, occurredAt, source, payload, version, Map.of());
    }
}
