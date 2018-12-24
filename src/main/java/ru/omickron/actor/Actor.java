package ru.omickron.actor;

import javax.annotation.Nullable;

public interface Actor {
    @Nullable
    String act( @Nullable String input );
}
