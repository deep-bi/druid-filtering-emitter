package bi.deep;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public class FilteringEmitterConfig {

    /**
     * Emitter name to be wrapped by the FilteringEmitter
     */
    @JsonProperty
    @NotNull
    private String emitter = "noop";

    /**
     * Key in the EventMap to extract for filtering. By default, "metric" is used.
     * If given key is not in the event then the event passes.
     */
    @JsonProperty
    @NotNull
    private String key = "metric";

    @JsonProperty
    @NotNull
    private Set<String> allowList = Collections.emptySet();

    @JsonProperty
    @NotNull
    private Set<String> blockList = Collections.emptySet();

    @JsonProperty
    @NotNull
    private Set<String> startsWith = Collections.emptySet();

    @JsonProperty
    @NotNull
    private Set<String> regexMatch = Collections.emptySet();

    public void validate() {
        if (!allowList.isEmpty() && !blockList.isEmpty() && startsWith.isEmpty() && regexMatch.isEmpty()) {
            throw new RuntimeException("Defining both 'allowList' and 'blockList' without other filters is not supported.");
        }
    }

    public String getEmitter() {
        return emitter;
    }

    public String getKey() {
        return key;
    }

    public Set<String> getAllowList() {
        return allowList;
    }

    public Set<String> getBlockList() {
        return blockList;
    }

    public Set<String> getStartsWithList() {
        return startsWith;
    }

    public Set<String> getRegexMatchList() {
        return regexMatch;
    }
}
