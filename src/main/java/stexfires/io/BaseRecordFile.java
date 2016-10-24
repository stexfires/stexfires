package stexfires.io;

import stexfires.core.Record;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class BaseRecordFile<W extends Record, R extends W> implements ReadableRecordFile<R>, WritableRecordFile<W> {

    protected final Path path;

    public BaseRecordFile(final Path path) {
        Objects.requireNonNull(path);
        this.path = path;
    }

    @Override
    public final Path getPath() {
        return path;
    }

    @Override
    public ReadableRecordProducer<R> openProducer() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WritableRecordConsumer<W> openConsumer(OpenOption... writeOptions) throws IOException {
        throw new UnsupportedOperationException();
    }

    protected BufferedWriter newBufferedWriter(CharsetEncoder charsetEncoder,
                                               OpenOption... writeOptions) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, writeOptions), charsetEncoder));
    }

    protected BufferedReader newBufferedReader(CharsetDecoder charsetDecoder,
                                               OpenOption... readOptions) throws IOException {
        return new BufferedReader(new InputStreamReader(Files.newInputStream(path, readOptions), charsetDecoder));
    }

    protected CharsetDecoder newCharsetDecoder(Charset charset) {
        return newCharsetDecoder(charset, CodingErrorAction.REPORT, null);
    }

    protected CharsetDecoder newCharsetDecoder(Charset charset,
                                               CodingErrorAction malformedInputAction) {
        return newCharsetDecoder(charset, malformedInputAction, null);
    }

    protected CharsetDecoder newCharsetDecoder(Charset charset,
                                               CodingErrorAction malformedInputAction,
                                               String replacement) {
        CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(malformedInputAction);
        if (malformedInputAction == CodingErrorAction.REPLACE && replacement != null) {
            charsetDecoder = charsetDecoder.replaceWith(replacement);
        }
        return charsetDecoder;
    }

    protected CharsetEncoder newCharsetEncoder(Charset charset) {
        return newCharsetEncoder(charset, CodingErrorAction.REPORT, null);
    }

    protected CharsetEncoder newCharsetEncoder(Charset charset,
                                               CodingErrorAction unmappableCharacterAction) {
        return newCharsetEncoder(charset, unmappableCharacterAction, null);
    }

    protected CharsetEncoder newCharsetEncoder(Charset charset,
                                               CodingErrorAction unmappableCharacterAction,
                                               byte[] replacement) {
        CharsetEncoder charsetEncoder = charset.newEncoder().onUnmappableCharacter(unmappableCharacterAction);
        if (unmappableCharacterAction == CodingErrorAction.REPLACE && replacement != null) {
            charsetEncoder = charsetEncoder.replaceWith(replacement);
        }
        return charsetEncoder;
    }

}
