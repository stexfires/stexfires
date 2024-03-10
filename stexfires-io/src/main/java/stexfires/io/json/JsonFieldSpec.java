package stexfires.io.json;

import java.util.Objects;

/**
 * @since 0.1
 */
public record JsonFieldSpec(
        String escapedName,
        JsonUtil.ValueType valueType,
        JsonUtil.NullHandling nullHandling,
        JsonUtil.ValidityCheck validityCheck
) {

    public JsonFieldSpec {
        Objects.requireNonNull(escapedName);
        Objects.requireNonNull(valueType);
        Objects.requireNonNull(nullHandling);
        Objects.requireNonNull(validityCheck);
    }

    public static JsonFieldSpec numberType(String unescapedName,
                                           JsonUtil.NullHandling nullHandling,
                                           JsonUtil.ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.NUMBER,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec stringUnescapedType(String unescapedName,
                                                    JsonUtil.NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.STRING_UNESCAPED,
                nullHandling,
                JsonUtil.ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringEscapedType(String unescapedName,
                                                  JsonUtil.NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.STRING_ESCAPED,
                nullHandling,
                JsonUtil.ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringEscapedWithQuotationMarksType(String unescapedName,
                                                                    JsonUtil.NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.STRING_ESCAPED_WITH_QUOTATION_MARKS,
                nullHandling,
                JsonUtil.ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec booleanType(String unescapedName,
                                            JsonUtil.NullHandling nullHandling,
                                            JsonUtil.ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                JsonUtil.ValueType.BOOLEAN,
                nullHandling,
                validityCheck);
    }

    public static String escapeName(String unescapedName) {
        Objects.requireNonNull(unescapedName);
        return JsonUtil.escapeJsonString(unescapedName);
    }

    public boolean checkValueNecessary() {
        return validityCheck == JsonUtil.ValidityCheck.CHECK_VALUE;
    }

}
