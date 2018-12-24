package ru.omickron.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Named;
import ru.omickron.model.Version;

@Named
public class ReleaseVersionActor implements Actor {
    @Nullable
    @Override
    public String act( @Nullable String input ) {
        return Version.parse( Objects.requireNonNull( input ) ).release().toString();
    }
}
