package ru.omickron.operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.actor.Actor;

import static java.util.stream.Collectors.toMap;

@Named
@Singleton
public class OperationProcessor {
    @Inject
    private List<Actor> actors;

    public void run( @NonNull Log log, @NonNull Operation operation ) {
        log.info( "Running operation " + operation.getName() );
        Map<String, Actor> actorMap = actors.stream().collect( toMap( this :: getActorName, Function.identity() ) );
        Map<String, String> inputOutput = new HashMap<>();
        for (Action action : operation.getActions()) {
            log.debug( "actor name = " + action.getActor() );
            Actor actor = Objects.requireNonNull( actorMap.get( action.getActor().toLowerCase() ),
                    "Can't find actor with name " + action.getActor() );
            String input = inputOutput.get( action.getInput() );
            log.debug( "input = " + input );
            String output = actor.act( input );
            log.info( String.format( "%s = %s(%s)", output, action.getActor(), input ) );
            inputOutput.put( action.getOutput(), output );
        }
    }

    @NonNull
    private String getActorName( @NonNull Actor actor ) {
        String className = actor.getClass().getSimpleName();
        return className.substring( 0, className.length() - "actor".length() ).toLowerCase();
    }
}
