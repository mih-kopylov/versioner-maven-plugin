package ru.mihkopylov.actor.extra;

import lombok.NonNull;
import ru.mihkopylov.actor.Actor;

public enum ExtraActorEngine {
    JYTHON {
        @NonNull
        @Override
        public Actor createActor( @NonNull ExtraActor extraActor ) {
            return new ExtraJythonActor( extraActor );
        }
    },
    GROOVY {
        @NonNull
        @Override
        public Actor createActor( @NonNull ExtraActor extraActor ) {
            return new ExtraGroovyActor( extraActor );
        }
    };

    @NonNull
    public abstract Actor createActor( @NonNull ExtraActor extraActor );
}
