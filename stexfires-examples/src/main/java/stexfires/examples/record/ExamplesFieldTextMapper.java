package stexfires.examples.record;

import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.mapper.field.*;
import stexfires.util.Strings;
import stexfires.util.function.StringUnaryOperators;
import stexfires.util.supplier.SequenceSupplier;
import stexfires.util.supplier.Suppliers;

import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesFieldTextMapper {

    private ExamplesFieldTextMapper() {
    }

    private static Stream<TextField> generateStream() {
        return Stream.of(
                new TextField(0, 3, "value1"),
                new TextField(1, 3, null),
                new TextField(2, 3, ""),
                new TextField(3, 3, "value2")
        );
    }

    private static void printMapper(String title, FieldTextMapper fieldTextMapper) {
        System.out.println("--" + title);
        Strings.printLine(TextFields.mapToTexts(generateStream(), fieldTextMapper), Strings.DEFAULT_DELIMITER);
    }

    private static void showAddSuffixFieldValueMapper() {
        System.out.println("-showAddSuffixFieldValueMapper---");

        printMapper("constructor", new AddSuffixFieldTextMapper("-suffix"));
    }

    private static void showAddPrefixFieldValueMapper() {
        System.out.println("-showAddPrefixFieldValueMapper---");

        printMapper("constructor", new AddPrefixFieldTextMapper("prefix-"));
    }

    private static void showConditionalFieldValueMapper() {
        System.out.println("-showConditionalFieldValueMapper---");

        printMapper("constructor valueIsNull", new ConditionalFieldTextMapper(
                TextField::isNull,
                new ConstantFieldTextMapper("<field value is null>"),
                new IdentityFieldTextMapper()));
    }

    private static void showConstantFieldValueMapper() {
        System.out.println("-showConstantFieldValueMapper---");

        printMapper("constructor null", new ConstantFieldTextMapper(null));
        printMapper("constructor constant", new ConstantFieldTextMapper("constant"));
    }

    private static void showFieldValueMapper() {
        System.out.println("-showFieldValueMapper---");

        printMapper("append", new AddPrefixFieldTextMapper("<").append(">"));
        printMapper("prepend", new ReplaceNullFieldTextMapper("NULL").prepend("<").append(">"));
    }

    private static void showIdentityFieldValueMapper() {
        System.out.println("-showIdentityFieldValueMapper---");

        printMapper("constructor", new IdentityFieldTextMapper());
    }

    private static void showIndexedFieldValueMapper() {
        System.out.println("-showIndexedFieldValueMapper---");

        printMapper("constructor", new IndexedFieldTextMapper(
                i -> i == 1 ? Optional.of(new ConstantFieldTextMapper("index 1")) : Optional.empty(),
                new ConstantFieldTextMapper("else")
        ));

        printMapper("byArray", IndexedFieldTextMapper.byArray(
                new AddPrefixFieldTextMapper("Index-0: "),
                new AddPrefixFieldTextMapper("Index-1: "),
                new AddPrefixFieldTextMapper("Index-2: "),
                new AddPrefixFieldTextMapper("Index-3: ")
        ));

        List<FieldTextMapper> fieldTextMappersList = new ArrayList<>();
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-0: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-1: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-2: "));
        fieldTextMappersList.add(new AddPrefixFieldTextMapper("Index-3: "));

        printMapper("byList", IndexedFieldTextMapper.byList(fieldTextMappersList));

        Map<Integer, FieldTextMapper> fieldValueMappersMap = new TreeMap<>();
        fieldValueMappersMap.put(0, new AddPrefixFieldTextMapper("Index-0: "));
        fieldValueMappersMap.put(1, new AddPrefixFieldTextMapper("Index-1: "));
        fieldValueMappersMap.put(2, new AddPrefixFieldTextMapper("Index-2: "));
        fieldValueMappersMap.put(3, new AddPrefixFieldTextMapper("Index-3: "));

        printMapper("byMap", IndexedFieldTextMapper.byMap(fieldValueMappersMap));
    }

    private static void showReplaceNullFieldValueMapper() {
        System.out.println("-showReplaceNullFieldValueMapper---");

        printMapper("constructor NOT_NULL", new ReplaceNullFieldTextMapper("NOT_NULL"));
    }

    private static void showStringOperationFieldValueMapper() {
        System.out.println("-showStringOperationFieldValueMapper---");

        printMapper("constructor trimToEmpty", new StringOperationFieldTextMapper(StringUnaryOperators.trimToEmpty()));
        printMapper("constructor reverse", new StringOperationFieldTextMapper(StringUnaryOperators.reverse()));
        printMapper("constructor upperCase", new StringOperationFieldTextMapper(StringUnaryOperators.upperCase(Locale.ENGLISH)));
        printMapper("constructor identity", new StringOperationFieldTextMapper(StringUnaryOperators.identity()));
    }

    private static void showSupplierFieldValueMapper() {
        System.out.println("-showSupplierFieldValueMapper---");

        printMapper("constructor localTimeNowAsString", new SupplierFieldTextMapper(Suppliers.localTimeNowAsString()));
        printMapper("constructor sequenceAsString", new SupplierFieldTextMapper(SequenceSupplier.asString(1000L)));
        printMapper("constructor threadNameAsString", new SupplierFieldTextMapper(Suppliers.threadNameAsString()));
    }

    public static void main(String... args) {
        showAddSuffixFieldValueMapper();
        showAddPrefixFieldValueMapper();
        showConditionalFieldValueMapper();
        showConstantFieldValueMapper();
        showFieldValueMapper();
        showIdentityFieldValueMapper();
        showIndexedFieldValueMapper();
        showReplaceNullFieldValueMapper();
        showStringOperationFieldValueMapper();
        showSupplierFieldValueMapper();
    }

}
