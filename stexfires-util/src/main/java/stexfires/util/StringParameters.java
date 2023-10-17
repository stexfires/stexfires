package stexfires.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * Objects of this class contains parameters as key-value pairs. The keys and values are {@code String}s.
 * <p>
 * The parameter keys must not be {@code null} and must be valid according to {@link #checkValidityOfKey(String)}.
 * The parameter values must not be {@code null}.
 * <p>
 * A new instance of {@code StringParameters} can only be created with the {@code Builder}.
 * <p>
 * {@snippet :
 * new StringParameters.Builder()
 *                     .addSystemEnvironments()
 *                     .addSystemProperties()
 *                     .addParameter("key1", "value1")
 *                     .addParameters(Map.of("key2", "value2", "key3", "value3"))
 *                     .build();
 *}
 *
 * @since 0.1
 */
public final class StringParameters {

    private final Map<String, String> parameters;

    /**
     * Constructs a new {@code StringParameters}.
     * The constructor may only be called by the "Builder" class.
     * All parameter keys must be valid according to {@link #checkValidityOfKey(String)}.
     * All parameter values must not be {@code null}.
     *
     * @param parameters the parameters. Must not be {@code null}. Must not change after the constructor has been called.
     * @throws IllegalArgumentException if any parameter key is invalid
     * @throws NullPointerException     if the parameter map is {@code null} or any parameter value is {@code null}
     */
    private StringParameters(@NotNull Map<String, String> parameters) {
        Objects.requireNonNull(parameters);
        // Check all keys
        parameters.keySet().forEach(StringParameters::checkValidityOfKey);
        // Check all values
        parameters.values().forEach(Objects::requireNonNull);
        this.parameters = parameters;
    }

    /**
     * Checks the validity of the specified key.
     * Throws an {@code IllegalArgumentException} if the key is {@code null} or invalid.
     * <p>
     * The parameter key must not be {@code null}, must not be empty, muss not change by trimming,
     * must contain only valid codepoints, must contain only defined codepoints
     * and must not contain whitespace codepoints.
     *
     * @param key the parameter key. Can be {@code null}.
     * @throws IllegalArgumentException if the key is {@code null} or invalid
     */
    public static void checkValidityOfKey(@Nullable String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty");
        }
        if (!key.equals(key.trim())) {
            throw new IllegalArgumentException("key must not change by trimming");
        }
        if (!key.codePoints().allMatch(Character::isValidCodePoint)) {
            throw new IllegalArgumentException("key must contain only valid codepoints");
        }
        if (!key.codePoints().allMatch(Character::isDefined)) {
            throw new IllegalArgumentException("key must contain only defined codepoints");
        }
        if (key.codePoints().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("key must not contain whitespace codepoints");
        }
    }

    /**
     * Returns the value of the specified key as an {@code Optional}.
     * If the key is not present, an empty {@code Optional} is returned.
     *
     * @param key the parameter key. Must not be {@code null}.
     * @return the value of the specified key. If the key is not present, an empty {@code Optional} is returned.
     */
    public Optional<String> value(@NotNull String key) {
        Objects.requireNonNull(key);
        return Optional.ofNullable(parameters.get(key));
    }

    /**
     * Returns {@code true} if a parameter with the specified key is present.
     *
     * @param key the parameter key. Must not be {@code null}.
     * @return {@code true} if a parameter with the specified key is present
     */
    public boolean containsKey(@NotNull String key) {
        Objects.requireNonNull(key);
        return parameters.containsKey(key);
    }

    /**
     * Returns an unmodifiable set of the parameter keys.
     * <p>
     * All parameter keys are valid according to {@link #checkValidityOfKey(String)}.
     *
     * @return an unmodifiable set of the parameter keys
     */
    public Set<String> keySet() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Returns the number of parameters.
     *
     * @return the number of parameters
     */
    public int size() {
        return parameters.size();
    }

    /**
     * Returns {@code true} if this StringParameters contains no key-value mappings.
     *
     * @return {@code true} if this StringParameters contains no key-value mappings
     */
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    /**
     * Returns a new {@code Properties} object with the parameters.
     *
     * @return a new {@code Properties} object with the parameters
     */
    public Properties toProperties() {
        Properties properties = new Properties();
        parameters.forEach(properties::setProperty);
        return properties;
    }

    /**
     * A {@code Builder} for {@code StringParameters}.
     * <p>
     * The methods are all synchronized to ensure thread safety.
     * After the {@code build()} method has been called, the builder can no longer be used.
     * All methods throw an {@code IllegalStateException} after {@code build()} has been called.
     * <p>
     * The parameter keys must not be {@code null} and must be valid according to {@link #checkValidityOfKey(String)}.
     * <p>
     * The parameter values must not be {@code null}.
     * <p>
     * Each 'add' method overwrites the value of an existing key.
     * {@snippet :
     * new StringParameters.Builder()
     *                     .addSystemEnvironments()
     *                     .addSystemProperties()
     *                     .addParameter("key1", "value1")
     *                     .addParameters(Map.of("key2", "value2", "key3", "value3"))
     *                     .build();
     *}
     */
    public static final class Builder {

        private Map<String, String> parameters;

        /**
         * Constructs a new {@code Builder} for {@code StringParameters}.
         */
        public Builder() {
            parameters = new HashMap<>();
        }

        private synchronized void checkAndAddParameterInternal(@NotNull String key, @NotNull String value) {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            checkValidityOfKey(key);
            parameters.put(key, value);
        }

        /**
         * Adds a parameter with the specified key and value.
         *
         * @param key   the parameter key. Must not be {@code null}.
         * @param value the parameter value. Must not be {@code null}.
         * @return this {@code Builder}
         */
        public synchronized Builder addParameter(@NotNull String key, @NotNull String value) {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            checkAndAddParameterInternal(key, value);
            return this;
        }

        /**
         * Adds all parameters from the specified map.
         *
         * @param stringParameterMap the parameters to be added. Must not be {@code null}.
         * @return this {@code Builder}
         */
        public synchronized Builder addParameters(@NotNull Map<String, String> stringParameterMap) {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            Objects.requireNonNull(stringParameterMap);
            stringParameterMap.forEach(this::checkAndAddParameterInternal);
            return this;
        }

        /**
         * Adds all system environments as parameters.
         *
         * @return this {@code Builder}
         * @see System#getenv()
         */
        public synchronized Builder addSystemEnvironments() {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            System.getenv().forEach(this::checkAndAddParameterInternal);
            return this;
        }

        /**
         * Adds all system properties as parameters.
         *
         * @return this {@code Builder}
         * @see System#getProperties()
         */
        @SuppressWarnings("AccessOfSystemProperties")
        public synchronized Builder addSystemProperties() {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            System.getProperties().forEach((Object key, Object value) -> checkAndAddParameterInternal(String.valueOf(key), String.valueOf(value)));
            return this;
        }

        /**
         * Returns a new {@code StringParameters} with the added parameters.
         * All methods throw an {@code IllegalStateException} after {@code build()} has been called.
         *
         * @return a new {@code StringParameters} with the added parameters
         */
        public synchronized StringParameters build() {
            if (parameters == null) {
                throw new IllegalStateException("build() already called");
            }
            StringParameters stringParameters = new StringParameters(parameters);
            // Set parameters to null to prevent further use of the builder
            parameters = null;
            return stringParameters;
        }

    }

}
