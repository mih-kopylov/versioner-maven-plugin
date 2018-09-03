package ru.omickron.processors;

import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;

public interface ReleaseProcessor {
    @NonNull
    VersionType getType();

    void process( @NonNull Log log );
}
