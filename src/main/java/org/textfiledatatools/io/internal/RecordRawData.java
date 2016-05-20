package org.textfiledatatools.io.internal;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordRawData {

    private final String category;
    private final Long recordId;
    private final String rawData;

    public RecordRawData(String category, Long recordId, String rawData) {
        this.category = category;
        this.recordId = recordId;
        this.rawData = rawData;
    }

    public String getCategory() {
        return category;
    }

    public Long getRecordId() {
        return recordId;
    }

    public String getRawData() {
        return rawData;
    }

    @Override
    public String toString() {
        return "RecordRawData{" +
                "category='" + category + '\'' +
                ", recordId=" + recordId +
                ", rawData='" + rawData + '\'' +
                '}';
    }

}
