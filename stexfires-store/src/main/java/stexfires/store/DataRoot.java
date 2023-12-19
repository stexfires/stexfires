package stexfires.store;

public class DataRoot {
    private String content;
    private DataRecord dataRecord;

    public DataRoot() {
        super();
    }

    public DataRecord getDataRecord() {
        return dataRecord;
    }

    public void setDataRecord(DataRecord dataRecord) {
        this.dataRecord = dataRecord;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Root: " + this.content;
    }

}
