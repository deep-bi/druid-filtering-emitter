package bi.deep.filtering.steps;

import java.util.Set;

import static bi.deep.filtering.steps.TrinaryBool.*;

public class StartsWithFilter implements FilterStep {
    private final Set<String> prefixes;

    public StartsWithFilter(Set<String> prefixes) {
        this.prefixes = prefixes;
    }

    @Override
    public TrinaryBool filter(String value) {
        if (prefixes.isEmpty() || prefixes.stream().anyMatch(value::startsWith)) return TRUE;
        else return MAYBE_FALSE;
    }

    @Override
    public boolean isEnabled() {
        return !prefixes.isEmpty();
    }
}

