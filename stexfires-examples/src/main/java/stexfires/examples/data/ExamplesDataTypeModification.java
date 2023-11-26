package stexfires.examples.data;

import stexfires.data.DataType;
import stexfires.data.DataTypes;
import stexfires.data.NumberDataTypeFormatter;
import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.producer.ConstantProducer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesDataTypeModification {

    private ExamplesDataTypeModification() {
    }

    public static void main(String... args) {
        // Integer
        DataType<Integer> integerDataTypeGermany = DataTypes.integerDataType(0, Locale.GERMANY);
        DataType<Integer> integerDataTypeUS = DataTypes.integerDataType(0, Locale.US);

        // BigDecimal
        DataType<BigDecimal> bigDecimalDataTypeUS = DataType.of(
                BigDecimal.class,
                BigDecimal.ZERO,
                new NumberDataTypeFormatter<>(NumberFormat.getNumberInstance(Locale.US), null));

        // Boolean
        DataType<Boolean> booleanDataType = DataTypes.booleanDataType(false, "TRUE", "FALSE");

        String textValue0 = "1.234";
        ValueRecord valueRecord = TextRecords.ofText(textValue0);
        ConstantProducer<ValueRecord> constantProducer = new ConstantProducer<>(3, valueRecord);

        {
            System.out.println("--- convert same class");

            Function<String, String> convertFunction = (String s) -> integerDataTypeUS.formatter().format(integerDataTypeGermany.parser().parse(s));
            UnaryOperator<String> convertOperator0 = (String s) -> integerDataTypeUS.formatter().format(integerDataTypeGermany.parser().parse(s));
            UnaryOperator<String> convertOperator1 = integerDataTypeGermany.textModifier(integerDataTypeUS);

            System.out.println(integerDataTypeUS.formatter().format(integerDataTypeGermany.parser().parse(textValue0)));
            System.out.println(convertFunction.apply(textValue0));
            System.out.println(convertOperator0.apply(textValue0));
            System.out.println(convertOperator1.apply(textValue0));

            System.out.println(integerDataTypeUS.formatter().format(integerDataTypeGermany.parser().parse(valueRecord.value())));
            System.out.println(convertFunction.apply(valueRecord.value()));
            System.out.println(convertOperator0.apply(valueRecord.value()));
            System.out.println(convertOperator1.apply(valueRecord.value()));

            RecordMapper<ValueRecord, ValueRecord> recordMapper0 = (ValueRecord r) -> r.withValue(integerDataTypeUS.formatter().format(integerDataTypeGermany.parser().parse(r.value())));
            RecordMapper<ValueRecord, ValueRecord> recordMapper1 = (ValueRecord r) -> r.withValue(convertOperator1.apply(r.value()));
            System.out.println(recordMapper0.map(valueRecord));
            constantProducer.produceStream().map(recordMapper0::map).forEach(RecordSystemOutUtil::printlnRecord);
            System.out.println(recordMapper1.map(valueRecord));
            constantProducer.produceStream().map(recordMapper1::map).forEach(RecordSystemOutUtil::printlnRecord);
        }

        {
            System.out.println("--- apply operator");

            UnaryOperator<Integer> integerUnaryOperator = (Integer i) -> i * i;
            UnaryOperator<String> convertOperator0 = (String s) -> integerDataTypeGermany.formatter().format(integerUnaryOperator.apply(integerDataTypeGermany.parser().parse(s)));
            UnaryOperator<String> convertOperator1 = integerDataTypeGermany.textModifier(integerUnaryOperator);
            UnaryOperator<String> convertOperator2 = integerDataTypeGermany.textModifier(integerDataTypeGermany, (i) -> i * 2);

            System.out.println(convertOperator0.apply(textValue0));
            System.out.println(convertOperator1.apply(textValue0));
            System.out.println(convertOperator2.apply(textValue0));
        }

        {
            System.out.println("--- convert different class");

            // BigDecimal
            //noinspection TrivialFunctionalExpressionUsage
            UnaryOperator<String> convertOperator0 = (String s) -> bigDecimalDataTypeUS.formatter().format(((Function<Integer, BigDecimal>) BigDecimal::valueOf).apply(integerDataTypeGermany.parser().parse(s)));
            UnaryOperator<String> convertOperator1 = integerDataTypeGermany.textModifierDifferentClass(bigDecimalDataTypeUS, BigDecimal::valueOf);
            // area of circle
            UnaryOperator<String> convertOperator2 = integerDataTypeGermany.textModifierDifferentClass(bigDecimalDataTypeUS, (i) -> BigDecimal.valueOf(i).pow(2).multiply(BigDecimal.valueOf(Math.PI)));
            UnaryOperator<String> convertOperator3 = integerDataTypeGermany.textModifierDifferentClass(bigDecimalDataTypeUS, BigDecimal::valueOf, (b) -> b.pow(2).multiply(BigDecimal.valueOf(Math.PI)));

            System.out.println(convertOperator0.apply(textValue0));
            System.out.println(convertOperator1.apply(textValue0));
            System.out.println(convertOperator2.apply(textValue0));
            System.out.println(convertOperator3.apply(textValue0));

            // Boolean
            // is even
            UnaryOperator<String> convertOperator4 = integerDataTypeGermany.textModifierDifferentClass(booleanDataType, (Integer i) -> i % 2 == 0);

            System.out.println(convertOperator4.apply(textValue0));
        }
    }

}
