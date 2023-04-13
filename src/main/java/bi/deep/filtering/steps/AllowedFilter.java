package bi.deep.filtering.steps;

import java.util.Set;

import static bi.deep.filtering.steps.TrinaryBool.*;

public class AllowedFilter implements FilterStep {
    private final Set<String> allowed;

    public AllowedFilter(Set<String> allowed) {
        this.allowed = allowed;
    }

    @Override
    public TrinaryBool filter(String value) {
        if (allowed.contains(value)) return TRUE;
        else return MAYBE_FALSE;
    }

    @Override
    public boolean isEnabled() {
        return !allowed.isEmpty();
    }
}
