package bi.deep.filtering.steps;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bi.deep.filtering.steps.TrinaryBool.*;

public class RegexFilter implements FilterStep {
    private final List<Pattern> patterns;

    public RegexFilter(Set<String> regexes) {
        this.patterns = regexes.stream().map(Pattern::compile).collect(Collectors.toList());
    }

    @Override
    public TrinaryBool filter(String value) {
        if (patterns.stream().anyMatch(pattern -> pattern.matcher(value).matches())) return TRUE;
        else return MAYBE_FALSE;
    }

    @Override
    public boolean isEnabled() {
        return !patterns.isEmpty();
    }
}
