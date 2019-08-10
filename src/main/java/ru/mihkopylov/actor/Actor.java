package ru.mihkopylov.actor;

import javax.annotation.Nullable;

public interface Actor {
    @Nullable
    String act( @Nullable String input );
}
