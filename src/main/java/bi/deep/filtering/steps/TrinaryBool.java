package bi.deep.filtering.steps;

import java.util.Objects;
import java.util.function.BinaryOperator;

public class TrinaryBool {
    private final boolean value;
    private final boolean known;

    private TrinaryBool(boolean value, boolean isKnown) {
        this.value = value;
        this.known = isKnown;
    }

    public boolean isKnown() {
        return known;
    }

    public boolean getValue() {
        return value;
    }

    public static TrinaryBool TRUE = new TrinaryBool(true, true);
    public static TrinaryBool FALSE = new TrinaryBool(false, true);

    public static TrinaryBool MAYBE_TRUE = new TrinaryBool(true, false);

    public static TrinaryBool MAYBE_FALSE = new TrinaryBool(false, false);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrinaryBool that = (TrinaryBool) o;
        return value == that.value && known == that.known;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, known);
    }

    @Override
    public String toString() {
        return "TrinaryBool{" +
                "value=" + value +
                ", known=" + known +
                '}';
    }
}

//public enum TrinaryBool {
//    TRUE(true, true),
//    FALSE(false, true),
//    MAYBE(false, false);
//
//    private final boolean value;
//    private final boolean shortCircuit;
//
//    public boolean getValue() {
//        return value;
//    }
//
//
//    public boolean canShortCircuit() {
//        return shortCircuit;
//    }
//
//    TrinaryBool(boolean value, boolean shortCircuit) {
//        this.value = value;
//        this.shortCircuit = shortCircuit;
//    }
//}
