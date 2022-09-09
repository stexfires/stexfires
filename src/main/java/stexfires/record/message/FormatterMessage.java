package stexfires.record.message;

import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.TextRecord;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @see java.util.Formatter
 * @since 0.1
 */
public class FormatterMessage<T extends TextRecord> implements RecordMessage<T> {

    private final String format;
    private final Locale locale;
    private final int numberOfFieldTexts;
    private final String fillUpText;

    public FormatterMessage(String format, Locale locale, int numberOfFieldTexts, @Nullable String fillUpText) {
        Objects.requireNonNull(format);
        Objects.requireNonNull(locale);
        if (numberOfFieldTexts < 0) {
            throw new IllegalArgumentException("Illegal size! numberOfFieldTexts=" + numberOfFieldTexts);
        }
        this.format = format;
        this.locale = locale;
        this.numberOfFieldTexts = numberOfFieldTexts;
        this.fillUpText = fillUpText;
    }

    public static <T extends TextRecord> FormatterMessage<T> withoutTexts(String format, Locale locale) {
        return new FormatterMessage<>(format, locale, 0, null);
    }

    @Override
    public final String createMessage(T record) {
        List<Object> args = new ArrayList<>(4 + numberOfFieldTexts);

        // add 4 standard arguments
        args.add(record.getClass().getName());
        args.add(record.category());
        args.add(record.recordId());
        args.add(record.size());

        if (numberOfFieldTexts > 0) {
            // add limited record texts
            record.streamOfFields()
                  .limit(numberOfFieldTexts)
                  .map(Field::text)
                  .forEachOrdered(args::add);

            // fill up missing texts
            if (numberOfFieldTexts > record.size()) {
                Stream.generate(() -> fillUpText)
                      .limit(numberOfFieldTexts - record.size())
                      .forEachOrdered(args::add);
            }
        }

        // format arguments
        try (Formatter formatter = new Formatter(locale)) {
            formatter.format(format, args.toArray());
            return formatter.out().toString();
        }
    }

}
