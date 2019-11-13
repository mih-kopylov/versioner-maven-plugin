package ru.mihkopylov.actor.extra;

import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import ru.mihkopylov.actor.Actor;

@AllArgsConstructor
public class ExtraJythonActor implements Actor {
    @NonNull
    private final ExtraActor extraActor;

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
}
