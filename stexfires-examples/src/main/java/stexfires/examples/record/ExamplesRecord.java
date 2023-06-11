package stexfires.examples.record;

import stexfires.record.KeyValueRecord;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.util.Strings;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesRecord {

    private ExamplesRecord() {
    }

    private static void showEmptyRecord() {
        System.out.println("-showEmptyRecord---");

        RecordSystemOutUtil.printlnRecordExtended(TextRecords.empty());
        System.out.println();
    }

    private static void showKeyValueCommentFieldsRecord() {
        System.out.println("-showKeyValueCommentFieldsRecord---");

        {
            KeyValueCommentFieldsRecord record = new KeyValueCommentFieldsRecord("key", "value", "comment");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println("commentField:              " + record.commentField());
            System.out.println("comment:                   " + record.comment());
            System.out.println("commentIndex:              " + record.commentIndex());
            System.out.println("commentAsOptional:         " + record.commentAsOptional());
            System.out.println("commentAsStream:           " + Strings.join(record.commentAsStream()));
            System.out.println("withComment:               " + record.withComment("new comment"));
            System.out.println();
        }
        {
            KeyValueCommentFieldsRecord record = new KeyValueCommentFieldsRecord("category", 0L, "key", "value", "comment");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println("commentField:              " + record.commentField());
            System.out.println("comment:                   " + record.comment());
            System.out.println("commentIndex:              " + record.commentIndex());
            System.out.println("commentAsOptional:         " + record.commentAsOptional());
            System.out.println("commentAsStream:           " + Strings.join(record.commentAsStream()));
            System.out.println("withComment:               " + record.withComment("new comment"));
            System.out.println();
        }
        {
            KeyValueCommentFieldsRecord record = new KeyValueCommentFieldsRecord("key", null, null);
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println("commentField:              " + record.commentField());
            System.out.println("comment:                   " + record.comment());
            System.out.println("commentIndex:              " + record.commentIndex());
            System.out.println("commentAsOptional:         " + record.commentAsOptional());
            System.out.println("commentAsStream:           " + Strings.join(record.commentAsStream()));
            System.out.println("withComment:               " + record.withComment("new comment"));
            System.out.println();
        }
    }

    private static void showKeyValueFieldsRecord() {
        System.out.println("-showKeyValueFieldsRecord---");

        {
            KeyValueRecord record = new KeyValueFieldsRecord("key", "value");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
        {
            KeyValueRecord record = new KeyValueFieldsRecord("category", 1_234_567_890L, "key", "value");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
        {
            KeyValueRecord record = new KeyValueFieldsRecord("key", null);
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("keyField:                  " + record.keyField());
            System.out.println("key:                       " + record.key());
            System.out.println("keyAsStream:               " + Strings.join(record.keyAsStream()));
            System.out.println("keyIndex:                  " + record.keyIndex());
            System.out.println("withKey:                   " + record.withKey("newKey"));
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
    }

    private static void showManyFieldsRecord() {
        System.out.println("-showManyFieldsRecord---");

        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord());

        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord(Strings.list("value1", "value2", "value3")));
        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord("category", 1_234_567_890L, Strings.list("value1", "value2", "value3")));

        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord(Strings.stream("value1", "value2", "value3")));
        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord("category", 1_234_567_890L, Strings.stream("value1", "value2", "value3")));

        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord("value1", "value2", "value3"));
        RecordSystemOutUtil.printlnRecordExtended(new ManyFieldsRecord("category", 1_234_567_890L, "value1", "value2", "value3"));
        System.out.println();
    }

    private static void showTwoFieldsRecord() {
        System.out.println("-showTwoFieldsRecord---");

        {
            TwoFieldsRecord record = new TwoFieldsRecord("first", "second");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("secondField:               " + record.secondField());
            System.out.println("secondText:                " + record.secondText());
            System.out.println("withSwappedTexts:          " + record.withSwappedTexts());
            System.out.println();
        }
        {
            TwoFieldsRecord record = new TwoFieldsRecord("category", 1_234_567_890L, "first", "second");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("secondField:               " + record.secondField());
            System.out.println("secondText:                " + record.secondText());
            System.out.println("withSwappedTexts:          " + record.withSwappedTexts());
            System.out.println();
        }
        {
            TwoFieldsRecord record = new TwoFieldsRecord(null, null);
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("secondField:               " + record.secondField());
            System.out.println("secondText:                " + record.secondText());
            System.out.println("withSwappedTexts:          " + record.withSwappedTexts());
            System.out.println();
        }
    }

    private static void showValueFieldRecord() {
        System.out.println("-showValueFieldRecord---");

        {
            ValueRecord record = new ValueFieldRecord("value");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
        {
            ValueRecord record = new ValueFieldRecord("category", 1_234_567_890L, "value");
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
        {
            ValueRecord record = new ValueFieldRecord(null);
            RecordSystemOutUtil.printlnRecordExtended(record);
            System.out.println("valueField:                " + record.valueField());
            System.out.println("value:                     " + record.value());
            System.out.println("valueIndex:                " + record.valueIndex());
            System.out.println("valueAsOptional:           " + record.valueAsOptional());
            System.out.println("valueAsStream:             " + Strings.join(record.valueAsStream()));
            System.out.println("withValue:                 " + record.withValue("new value"));
            System.out.println();
        }
    }

    public static void main(String... args) {
        showEmptyRecord();
        showKeyValueCommentFieldsRecord();
        showKeyValueFieldsRecord();
        showManyFieldsRecord();
        showTwoFieldsRecord();
        showValueFieldRecord();
    }

}