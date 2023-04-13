package bi.deep;

import org.apache.druid.java.util.common.lifecycle.LifecycleStart;
import org.apache.druid.java.util.common.lifecycle.LifecycleStop;
import org.apache.druid.java.util.emitter.core.Emitter;
import org.apache.druid.java.util.emitter.core.Event;

import java.io.IOException;
import java.util.function.Predicate;

public class FilteringEmitter implements Emitter {

    private final Emitter inner;
    private final Predicate<Event> filter;

    public FilteringEmitter(Emitter inner, Predicate<Event> filter) {
        this.inner = inner;
        this.filter = filter;
    }

    @Override
    @LifecycleStart
    public void start() {
        inner.start();
    }

    @Override
    public void emit(Event event) {
        if (filter.test(event)) {
            inner.emit(event);
        }
    }

    @Override
    public void flush() throws IOException {
        inner.flush();
    }

    @Override
    @LifecycleStop
    public void close() throws IOException {
        inner.close();
    }

    @Override
    public String toString() {
        return "FilteringEmitter{" +
                "emitter=" + inner +
                "}";
    }
}
