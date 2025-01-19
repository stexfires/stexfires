package stexfires.io.json;

import java.util.*;

/**
 * @since 0.1
 */
public record JsonFieldSpec(
        String escapedName,
        ValueType valueType,
        NullHandling nullHandling,
        ValidityCheck validityCheck
) {

    public JsonFieldSpec {
        Objects.requireNonNull(escapedName);
        Objects.requireNonNull(valueType);
        Objects.requireNonNull(nullHandling);
        Objects.requireNonNull(validityCheck);
    }

    public static JsonFieldSpec numberType(String unescapedName,
                                           NullHandling nullHandling,
                                           ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                ValueType.NUMBER,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec stringUnescapedType(String unescapedName,
                                                    NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                ValueType.STRING_UNESCAPED,
                nullHandling,
                ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringEscapedType(String unescapedName,
                                                  NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                ValueType.STRING_ESCAPED,
                nullHandling,
                ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringEscapedWithQuotationMarksType(String unescapedName,
                                                                    NullHandling nullHandling) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                ValueType.STRING_ESCAPED_WITH_QUOTATION_MARKS,
                nullHandling,
                ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec booleanType(String unescapedName,
                                            NullHandling nullHandling,
                                            ValidityCheck validityCheck) {
        return new JsonFieldSpec(
                escapeName(unescapedName),
                ValueType.BOOLEAN,
                nullHandling,
                validityCheck);
    }

    public static String escapeName(String unescapedName) {
        Objects.requireNonNull(unescapedName);
        return JsonUtil.escapeJsonString(unescapedName);
    }

    public boolean checkValueNecessary() {
        return validityCheck == ValidityCheck.CHECK_VALUE;
    }

    public enum ValidityCheck {

        CHECK_VALUE, CHECK_NOT_NECESSARY

    }

    public enum ValueType {

        BOOLEAN,
        NUMBER,
        STRING_UNESCAPED,
        STRING_ESCAPED,
        STRING_ESCAPED_WITH_QUOTATION_MARKS,
        ARRAY_ELEMENTS,
        ARRAY,
        OBJECT_MEMBERS,
        OBJECT

    }

    public enum NullHandling {

        NOT_ALLOWED, ALLOWED_USE_LITERAL, ALLOWED_OMIT_FIELD

    }

}
