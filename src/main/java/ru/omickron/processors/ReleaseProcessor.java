package ru.omickron.processors;

import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.VersionType;

public interface ReleaseProcessor {
    @NonNull
    VersionType getType();

    void process( @NonNull Log log );
}
