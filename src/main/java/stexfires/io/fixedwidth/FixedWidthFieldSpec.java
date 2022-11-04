package stexfires.io.fixedwidth;

import org.jetbrains.annotations.Nullable;
import stexfires.util.Alignment;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record FixedWidthFieldSpec(int startIndex, int width,
                                  @Nullable Alignment alignment, @Nullable Character fillCharacter) {

}
