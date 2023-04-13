package bi.deep;

import bi.deep.filtering.EventFilter;
import com.fasterxml.jackson.databind.Module;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.apache.druid.guice.JsonConfigProvider;
import org.apache.druid.guice.ManageLifecycle;
import org.apache.druid.initialization.DruidModule;
import org.apache.druid.java.util.common.logger.Logger;
import org.apache.druid.java.util.emitter.core.Emitter;

import java.util.Collections;
import java.util.List;

public class FilteringEmitterModule implements DruidModule {
    private static final Logger log = new Logger(FilteringEmitterModule.class);

    @Override
    public List<? extends Module> getJacksonModules() {
        return Collections.emptyList();
    }

    @Override
    public void configure(Binder binder) {
        JsonConfigProvider.bind(binder, "druid.emitter.filtering", FilteringEmitterConfig.class);
    }

    @Provides
    @ManageLifecycle
    @Named("filtering")
    public Emitter getEmitter(FilteringEmitterConfig config, final Injector injector) {
        config.validate();
        log.info("Creating Filtering Emitter with %s", config.getEmitter());
        Emitter inner = injector.getInstance(Key.get(Emitter.class, Names.named(config.getEmitter())));

        return new FilteringEmitter(inner, EventFilter.of(config));
    }
}
