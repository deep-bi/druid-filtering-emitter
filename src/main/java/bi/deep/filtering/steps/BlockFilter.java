package bi.deep.filtering.steps;

import java.util.Set;

import static bi.deep.filtering.steps.TrinaryBool.FALSE;
import static bi.deep.filtering.steps.TrinaryBool.MAYBE_TRUE;

public class BlockFilter implements FilterStep {
    private final Set<String> excluded;

    public BlockFilter(Set<String> excluded) {
        this.excluded = excluded;
    }

    @Override
    public TrinaryBool filter(String value) {
        if (excluded.contains(value)) return FALSE;
        else return MAYBE_TRUE;
    }

    @Override
    public boolean isEnabled() {
        return !excluded.isEmpty();
    }
}
