package ru.mihkopylov.actor.extra;

import groovy.lang.GroovyClassLoader;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import ru.mihkopylov.actor.Actor;

@AllArgsConstructor
public class ExtraGroovyActor implements Actor {
    @NonNull
    private final ExtraActor extraActor;

    @Nullable
    @Override
    public String act( @Nullable String input ) {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class<?> groovyClass = groovyClassLoader.parseClass( extraActor.getCode() );
        if (!Actor.class.isAssignableFrom( groovyClass )) {
            throw new RuntimeException( "Groovy class should implement " + Actor.class.getName() );
        }
        try {
            Actor actor = (Actor) groovyClass.newInstance();
            return actor.act( input );
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException( "Script execution finished with exception", e );
        }
    }

    @NonNull
    @Override
    public String getName() {
        return extraActor.getName();
    }
}
