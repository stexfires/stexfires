package stexfires.examples.wiki;

import stexfires.io.RecordIOStreams;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.delimited.simple.SimpleDelimitedProducer;
import stexfires.io.markdown.list.MarkdownListConsumer;
import stexfires.io.markdown.list.MarkdownListFileSpec;
import stexfires.io.markdown.table.MarkdownTableConsumer;
import stexfires.io.markdown.table.MarkdownTableFieldSpec;
import stexfires.io.markdown.table.MarkdownTableFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.TextsMapper;
import stexfires.record.mapper.impl.ToValueFieldRecordMapper;
import stexfires.record.message.TextMessage;
import stexfires.record.producer.ProducerException;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class WikiTesting {

    private static final String RESOURCE_PATH = "stexfires/examples/wiki/";
    private static final int TITLE_MIN_WIDTH = 100;
    private static final int LINK_MIN_WIDTH = 200;
    private static final String TITLE_NAME = "Title";
    private static final String LINK_NAME = "Link";

    private WikiTesting() {
    }

    private static SimpleDelimitedFileSpec createProducerFileSpec() {
        List<SimpleDelimitedFieldSpec> fieldSpecsProducer = new ArrayList<>();
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        fieldSpecsProducer.add(new SimpleDelimitedFieldSpec());
        return SimpleDelimitedFileSpec.producerFileSpec(CharsetCoding.UTF_8_REPORTING,
                "\t",
                0,
                SimpleDelimitedFileSpec.DEFAULT_PRODUCER_READ_LINE_HANDLING,
                0, 0,
                true,
                fieldSpecsProducer
        );
    }

    private static MarkdownTableFileSpec createTableConsumerFileSpec(String title) {
        List<MarkdownTableFieldSpec> fieldSpecsConsumer = new ArrayList<>();
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec(TITLE_NAME, TITLE_MIN_WIDTH, null));
        fieldSpecsConsumer.add(new MarkdownTableFieldSpec(LINK_NAME, LINK_MIN_WIDTH, null));
        return MarkdownTableFileSpec.consumerFileSpec(CharsetCoding.UTF_8_REPORTING,
                LineSeparator.CR_LF,
                title,
                "",
                Alignment.START, fieldSpecsConsumer
        );
    }

    private static MarkdownListFileSpec createListConsumerFileSpec(String title) {
        return MarkdownListFileSpec.consumerFileSpec(CharsetCoding.UTF_8_REPORTING,
                LineSeparator.CR_LF,
                MarkdownListFileSpec.DEFAULT_CONSUMER_LIST_MARKER,
                title,
                "",
                true);
    }

    @SuppressWarnings("DataFlowIssue")
    private static void convertToMarkdownTable(String title, SimpleDelimitedProducer producer,
                                               OutputStream outputStream)
            throws ProducerException, ConsumerException, IOException {
        MarkdownTableFileSpec consumerFileSpec = createTableConsumerFileSpec(title);
        MarkdownTableConsumer consumer = consumerFileSpec.consumer(outputStream);
        RecordMapper<TextRecord, TextRecord> mapper = TextsMapper.applyRecordFunctions(
                r -> "[" + r.textAt(0) + "]" + "(" + r.textAt(1) + ")",
                r -> "[" + r.textAt(1).replace("http://", "").replace("https://", "")
                        + "]" + "(" + r.textAt(1) + ")");

        RecordIOStreams.transferMapped(producer, consumer, mapper);
    }

    private static void convertToMarkdownList(String title, SimpleDelimitedProducer producer,
                                              OutputStream outputStream)
            throws ProducerException, ConsumerException, IOException {
        MarkdownListFileSpec consumerFileSpec = createListConsumerFileSpec(title);
        MarkdownListConsumer consumer = consumerFileSpec.consumer(outputStream);
        RecordMapper<TextRecord, ValueRecord> mapper = TextsMapper.applyRecordFunctions(
                                                                          r -> "[" + r.textAt(0) + "]" + "(" + r.textAt(1) + ")")
                                                                  .andThen(new ToValueFieldRecordMapper<>(new TextMessage<>(0)));

        RecordIOStreams.transferMapped(producer, consumer, mapper);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        List<String> resources = new ArrayList<>();
        resources.add(RESOURCE_PATH + "Wiki_Testing_JUnit5.txt");
        resources.add(RESOURCE_PATH + "Wiki_Testing_Other.txt");

        List<String> headers = new ArrayList<>();
        headers.add("## JUnit 5 ##");
        headers.add("## Other testing or specification frameworks ##");

        SimpleDelimitedFileSpec producerFileSpec = createProducerFileSpec();

        // Markdown Table
        String outputFileNameTable = "Wiki_Testing_Table.md";
        File outputFileTable = new File(outputDirectory, outputFileNameTable);
        System.out.println("Generate MarkdownTable file: " + outputFileTable);

        try (OutputStream outputStream = new FileOutputStream(outputFileTable)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    if (inputStream == null) {
                        throw new IOException("Cannot find resource! " + resources.get(i));
                    }
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownTable(headers.get(i), producer, outputStream);
                }
            }
        } catch (ProducerException | ConsumerException | IOException e) {
            System.out.println("Cannot generate MarkdownTable file! " + e.getMessage());
        }

        // Markdown List
        String outputFileNameList = "Wiki_Testing_List.md";
        File outputFileList = new File(outputDirectory, outputFileNameList);
        System.out.println("Generate MarkdownList file: " + outputFileList);

        try (OutputStream outputStream = new FileOutputStream(outputFileList)) {
            for (int i = 0; i < resources.size(); i++) {
                try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(resources.get(i))) {
                    if (inputStream == null) {
                        throw new IOException("Cannot find resource! " + resources.get(i));
                    }
                    SimpleDelimitedProducer producer = producerFileSpec.producer(inputStream);
                    convertToMarkdownList(headers.get(i), producer, outputStream);
                }
            }
        } catch (ProducerException | ConsumerException | IOException e) {
            System.out.println("Cannot generate MarkdownList file! " + e.getMessage());
        }
    }

}
