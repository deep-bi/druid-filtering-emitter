package bi.deep.filtering;

import bi.deep.FilteringEmitterConfig;
import bi.deep.filtering.steps.TrinaryBool;
import bi.deep.filtering.steps.*;
import org.apache.druid.java.util.emitter.core.Event;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFilter implements Predicate<Event> {
    private final String key;
    private final List<FilterStep> steps;

    public EventFilter(String key, Set<String> allowList, Set<String> blockList, Set<String> startsWithList, Set<String> regexList) {
        if (!allowList.isEmpty() && !blockList.isEmpty() && startsWithList.isEmpty() && regexList.isEmpty()) {
            throw new RuntimeException("Defining both 'allowList' and 'blockList' without other filters is not supported.");
        }

        this.key = key;
        this.steps = Stream.of(
                        new AllowedFilter(allowList),
                        new BlockFilter(blockList),
                        new StartsWithFilter(startsWithList),
                        new RegexFilter(regexList))
                .filter(FilterStep::isEnabled)
                .collect(Collectors.toList());
    }

    public static EventFilter of(FilteringEmitterConfig config) {
        return new EventFilter(config.getKey(), config.getAllowList(), config.getBlockList(), config.getStartsWithList(), config.getRegexMatchList());
    }

    @Override
    public boolean test(Event event) {
        if (steps.isEmpty()) return true;

        Map<String, Object> kv = event.toMap();
        if (!kv.containsKey(key)) return true;

        String value = kv.get(key).toString();

        TrinaryBool result = TrinaryBool.MAYBE_FALSE;
        for (FilterStep step : steps) {
            result = step.filter(value);
            if (result.isKnown()) return result.getValue();
        }

        return result.getValue();
    }
}
