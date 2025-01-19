package stexfires.examples.data;

import org.jspecify.annotations.Nullable;
import stexfires.data.DataType;
import stexfires.data.DataTypes;
import stexfires.data.NumberDataTypeFormatter;
import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.TextsMapper;
import stexfires.record.mapper.field.FieldTextMapper;
import stexfires.record.mapper.field.IndexedFieldTextMapper;
import stexfires.record.mapper.field.StringOperationFieldTextMapper;
import stexfires.record.producer.ConstantProducer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.*;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesDataTypeModification {

    private ExamplesDataTypeModification() {
    }

    public static void main(String... args) {
        // Integer
        DataType<Integer> integerDataTypeDE = DataTypes.integerDataType(0, Locale.GERMANY);
        DataType<Integer> integerDataTypeUS = DataTypes.integerDataType(0, Locale.US);

        // BigDecimal
        DataType<BigDecimal> bigDecimalDataTypeUS = DataType.of(
                BigDecimal.class,
                BigDecimal.ZERO,
                new NumberDataTypeFormatter<>(NumberFormat.getNumberInstance(Locale.US), null));

        // Boolean
        DataType<Boolean> booleanDataType0 = DataTypes.booleanDataType(false, "TRUE", "FALSE");
        DataType<Boolean> booleanDataType1 = DataTypes.booleanDataType(false, "yes", "no");

        String textValue0 = "1.234";
        ValueRecord valueRecord = TextRecords.ofText(textValue0);
        TextRecord textRecord = TextRecords.ofTexts(textValue0, "TRUE");
        ConstantProducer<ValueRecord> constantProducerValueRecord = new ConstantProducer<>(3, valueRecord);
        ConstantProducer<TextRecord> constantProducerTextRecord = new ConstantProducer<>(3, textRecord);

        {
            System.out.println("--- convert same class");

            Function<@Nullable String, @Nullable String> integerDE2USFunction = (String s) -> integerDataTypeUS.formatter().format(integerDataTypeDE.parser().parse(s));
            UnaryOperator<@Nullable String> integerDE2USOperator0 = (String s) -> integerDataTypeUS.formatter().format(integerDataTypeDE.parser().parse(s));
            UnaryOperator<@Nullable String> integerDE2US1 = integerDataTypeDE.textModifier(integerDataTypeUS);
            FieldTextMapper integerDE2USMapper1 = integerDataTypeDE.fieldTextMapper(integerDataTypeUS);

            System.out.println(integerDataTypeUS.formatter().format(integerDataTypeDE.parser().parse(textValue0)));
            System.out.println(integerDE2USFunction.apply(textValue0));
            System.out.println(integerDE2USOperator0.apply(textValue0));
            System.out.println(integerDE2US1.apply(textValue0));

            System.out.println(integerDataTypeUS.formatter().format(integerDataTypeDE.parser().parse(valueRecord.value())));
            System.out.println(integerDE2USFunction.apply(valueRecord.value()));
            System.out.println(integerDE2USOperator0.apply(valueRecord.value()));
            System.out.println(integerDE2US1.apply(valueRecord.value()));

            RecordMapper<ValueRecord, ValueRecord> recordMapper0 = (ValueRecord r) -> r.withValue(integerDataTypeUS.formatter().format(integerDataTypeDE.parser().parse(r.value())));
            RecordMapper<ValueRecord, ValueRecord> recordMapper1 = (ValueRecord r) -> r.withValue(integerDE2US1.apply(r.value()));
            System.out.println(recordMapper0.map(valueRecord));
            constantProducerValueRecord.produceStream().map(recordMapper0::map).forEach(RecordSystemOutUtil::printlnRecord);
            System.out.println(recordMapper1.map(valueRecord));
            constantProducerValueRecord.produceStream().map(recordMapper1::map).forEach(RecordSystemOutUtil::printlnRecord);

            TextsMapper<ValueRecord> valueRecordTextsMapper = TextsMapper.applyTextOperators(
                    integerDE2US1
            );
            TextsMapper<TextRecord> textRecordMapper0 = TextsMapper.mapAllFields(IndexedFieldTextMapper.byArray(
                    integerDE2USMapper1,
                    new StringOperationFieldTextMapper(booleanDataType0.textModifier(booleanDataType1))
            ));
            TextsMapper<TextRecord> textRecordMapper1 = TextsMapper.applyTextOperators(
                    UnaryOperator.identity(),
                    booleanDataType0.textModifier(booleanDataType1)
            );
            TextsMapper<TextRecord> textRecordMapper2 = TextsMapper.mapOneField(record -> record.fieldAt(0), integerDE2USMapper1);

            constantProducerValueRecord.produceStream()
                                       .map(valueRecordTextsMapper::map)
                                       .forEach(RecordSystemOutUtil::printlnRecord);
            constantProducerTextRecord.produceStream()
                                      .map(textRecordMapper0::map)
                                      .forEach(RecordSystemOutUtil::printlnRecord);
            constantProducerTextRecord.produceStream()
                                      .map(textRecordMapper1::map)
                                      .forEach(RecordSystemOutUtil::printlnRecord);
            constantProducerTextRecord.produceStream()
                                      .map(textRecordMapper2::map)
                                      .forEach(RecordSystemOutUtil::printlnRecord);
        }

        {
            System.out.println("--- apply operator");

            UnaryOperator<@Nullable Integer> integerUnaryOperator = (i) -> (i == null) ? null : i * i;
            UnaryOperator<@Nullable String> convertOperator0 = (String s) -> integerDataTypeDE.formatter().format(integerUnaryOperator.apply(integerDataTypeDE.parser().parse(s)));
            UnaryOperator<@Nullable String> convertOperator1 = integerDataTypeDE.textModifier(integerUnaryOperator);
            UnaryOperator<@Nullable String> convertOperator2 = integerDataTypeDE.textModifier(integerDataTypeDE, (i) -> (i == null) ? null : i * 2);

            System.out.println(convertOperator0.apply(textValue0));
            System.out.println(convertOperator1.apply(textValue0));
            System.out.println(convertOperator2.apply(textValue0));
        }

        {
            System.out.println("--- convert different class");

            // BigDecimal
            //noinspection TrivialFunctionalExpressionUsage
            UnaryOperator<@Nullable String> convertOperator0 = (s) -> bigDecimalDataTypeUS.formatter().format(((Function<@Nullable Integer, @Nullable BigDecimal>) val -> val != null ? BigDecimal.valueOf(val) : null).apply(integerDataTypeDE.parser().parse(s)));
            UnaryOperator<@Nullable String> convertOperator1 = integerDataTypeDE.textModifierDifferentClass(bigDecimalDataTypeUS, val -> val != null ? BigDecimal.valueOf(val) : null);
            // area of circle
            UnaryOperator<@Nullable String> convertOperator2 = integerDataTypeDE.textModifierDifferentClass(bigDecimalDataTypeUS, (i) -> (i == null) ? null : BigDecimal.valueOf(i).pow(2).multiply(BigDecimal.valueOf(Math.PI)));
            UnaryOperator<@Nullable String> convertOperator3 = integerDataTypeDE.textModifierDifferentClass(bigDecimalDataTypeUS, val -> val != null ? BigDecimal.valueOf(val) : null, (b) -> (b == null) ? null : b.pow(2).multiply(BigDecimal.valueOf(Math.PI)));

            System.out.println(convertOperator0.apply(textValue0));
            System.out.println(convertOperator1.apply(textValue0));
            System.out.println(convertOperator2.apply(textValue0));
            System.out.println(convertOperator3.apply(textValue0));

            // Boolean
            // is even
            UnaryOperator<@Nullable String> convertOperator4 = integerDataTypeDE.textModifierDifferentClass(booleanDataType0, (i) -> (i == null) ? Boolean.FALSE : i % 2 == 0);

            System.out.println(convertOperator4.apply(textValue0));
        }
    }

}
