package stexfires.io.json;

import java.util.Objects;

/**
 * @since 0.1
 */
public record JsonFieldSpec(
        String escapedName,
        JsonUtil.ValueType valueType,
        JsonUtil.NullHandling nullHandling,
        JsonUtil.ValidityCheck validityCheck,
        JsonUtil.StringEscape stringEscape
) {

    public JsonFieldSpec {
        Objects.requireNonNull(escapedName);
        Objects.requireNonNull(valueType);
        Objects.requireNonNull(nullHandling);
        Objects.requireNonNull(validityCheck);
        Objects.requireNonNull(stringEscape);
    }

    public static JsonFieldSpec numberType(String unescapedName,
                                           JsonUtil.NullHandling nullHandling,
                                           JsonUtil.ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.NUMBER,
                nullHandling,
                validityCheck,
                JsonUtil.StringEscape.ESCAPE_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringType(String unescapedName,
                                           JsonUtil.NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.STRING,
                nullHandling,
                JsonUtil.ValidityCheck.CHECK_NOT_NECESSARY,
                JsonUtil.StringEscape.ESCAPE_STRING);
    }

    public static JsonFieldSpec booleanType(String unescapedName,
                                            JsonUtil.NullHandling nullHandling,
                                            JsonUtil.ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.BOOLEAN,
                nullHandling,
                validityCheck,
                JsonUtil.StringEscape.ESCAPE_NOT_NECESSARY);
    }

    public static String escapeName(String unescapedName) {
        Objects.requireNonNull(unescapedName);
        return JsonUtil.escapeJsonString(unescapedName);
    }

    public boolean escapeStringNecessary() {
        return (stringEscape == JsonUtil.StringEscape.ESCAPE_STRING) && (valueType == JsonUtil.ValueType.STRING);
    }

    public boolean checkValueNecessary() {
        return validityCheck == JsonUtil.ValidityCheck.CHECK_VALUE;
    }

}
