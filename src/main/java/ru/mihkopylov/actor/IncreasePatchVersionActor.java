package ru.mihkopylov.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Named;
import ru.mihkopylov.model.Version;

@Named
public class IncreasePatchVersionActor implements Actor {
    @Nullable
    @Override
    public String act( @Nullable String input ) {
        return Version.parse( Objects.requireNonNull( input ) ).incPatch().toString();
    }
}