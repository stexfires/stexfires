package stexfires.io.json;

import java.util.*;

/**
 * @since 0.1
 */
public record JsonFieldSpec(
        String unescapedName,
        String escapedName,
        ValueType valueType,
        NullHandling nullHandling,
        ValidityCheck validityCheck
) {

    public JsonFieldSpec {
        Objects.requireNonNull(unescapedName);
        Objects.requireNonNull(escapedName);
        Objects.requireNonNull(valueType);
        Objects.requireNonNull(nullHandling);
        Objects.requireNonNull(validityCheck);

        if (!JsonUtil.escapeJsonString(unescapedName).equals(escapedName)
                || !JsonUtil.unescapeJsonString(escapedName).equals(unescapedName)) {
            throw new IllegalArgumentException("unescapedName and escapedName do not match");
        }
    }

    public static JsonFieldSpec booleanType(String unescapedName,
                                            NullHandling nullHandling,
                                            ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.BOOLEAN,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec numberType(String unescapedName,
                                           NullHandling nullHandling,
                                           ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.NUMBER,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec stringUnescapedType(String unescapedName,
                                                    NullHandling nullHandling) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.STRING_UNESCAPED,
                nullHandling,
                ValidityCheck.CHECK_NOT_NECESSARY);
    }

    public static JsonFieldSpec stringEscapedType(String unescapedName,
                                                  NullHandling nullHandling,
                                                  ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.STRING_ESCAPED,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec stringEscapedWithQuotationMarksType(String unescapedName,
                                                                    NullHandling nullHandling,
                                                                    ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.STRING_ESCAPED_WITH_QUOTATION_MARKS,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec arrayElementsType(String unescapedName,
                                                  NullHandling nullHandling,
                                                  ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.ARRAY_ELEMENTS,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec arrayType(String unescapedName,
                                          NullHandling nullHandling,
                                          ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.ARRAY,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec objectMembersType(String unescapedName,
                                                  NullHandling nullHandling,
                                                  ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.OBJECT_MEMBERS,
                nullHandling,
                validityCheck);
    }

    public static JsonFieldSpec objectType(String unescapedName,
                                           NullHandling nullHandling,
                                           ValidityCheck validityCheck) {
        Objects.requireNonNull(unescapedName);
        return new JsonFieldSpec(
                unescapedName,
                JsonUtil.escapeJsonString(unescapedName),
                ValueType.OBJECT,
                nullHandling,
                validityCheck);
    }

    public boolean checkValueNecessary() {
        return validityCheck == ValidityCheck.CHECK_VALUE;
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

    public enum ValidityCheck {

        CHECK_VALUE, CHECK_NOT_NECESSARY

    }

}
