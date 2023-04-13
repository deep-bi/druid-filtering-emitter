package bi.deep.filtering.steps;

public interface FilterStep {

    boolean isEnabled();

    TrinaryBool filter(String value);

}
