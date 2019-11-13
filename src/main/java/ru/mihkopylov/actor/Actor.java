package ru.mihkopylov.actor;

import javax.annotation.Nullable;
import lombok.NonNull;

public interface Actor {
    @Nullable
    String act( @Nullable String input );

    /**
     * By default gets class name in camelCase without `Actor` suffix
     * @return name
     */
    @NonNull
    default String getName() {
        String className = getClass().getSimpleName();
        String classNameWithoutSuffix =
                className.substring( 0, className.length() - Actor.class.getSimpleName().length() );
        return classNameWithoutSuffix.substring( 0, 1 ).toLowerCase() + classNameWithoutSuffix.substring( 1 );
    }
}
