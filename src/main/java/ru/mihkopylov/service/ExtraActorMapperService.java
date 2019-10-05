package ru.mihkopylov.service;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import ru.mihkopylov.actor.Actor;
import ru.mihkopylov.actor.extra.ExtraActor;

@Named
@Singleton
public class ExtraActorMapperService {
    @NonNull
    public Actor toActor( @NonNull ExtraActor extraActor ) {
        return new Actor() {
            @Nullable
            @Override
            public String act( @Nullable String input ) {
                PythonInterpreter interpreter = new PythonInterpreter();
                interpreter.exec( extraActor.getCode() );
                PyObject pythonActorDefinition = interpreter.get( "Actor" );
                Actor actor = (Actor) pythonActorDefinition.__call__().__tojava__( Actor.class );
                return actor.act( input );
            }

            @NonNull
            @Override
            public String getName() {
                return extraActor.getName();
            }
        };
    }
}
