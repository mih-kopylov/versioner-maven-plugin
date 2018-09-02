package ru.omickron.processors;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.VersionType;
import ru.omickron.actions.GetCurrentVersionAction;
import ru.omickron.actions.SetVersionAction;
import ru.omickron.model.Version;

@Named
public class PatchReleaseProcessor implements ReleaseProcessor {
    @Inject
    private SetVersionAction setVersionAction;
    @Inject
    private GetCurrentVersionAction getCurrentVersionAction;

    @Override
    @NonNull
    public VersionType getType() {
        return VersionType.PATCH;
    }

    @Override
    @SneakyThrows
    public void process( @NonNull Log log ) {
        Version currentVersion = getCurrentVersionAction.get();
        setVersionAction.set( currentVersion.incPatch().release() );
    }
}

