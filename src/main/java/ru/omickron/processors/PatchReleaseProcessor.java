package ru.omickron.processors;

import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.VersionType;

@Named
@Singleton
public class PatchReleaseProcessor implements ReleaseProcessor {
    @Override
    @NonNull
    public VersionType getType() {
        return VersionType.PATCH;
    }

    @Override
    public void process( @NonNull Log log ) {

    }
}
