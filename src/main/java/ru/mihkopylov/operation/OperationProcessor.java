package ru.mihkopylov.operation;

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
import ru.mihkopylov.actor.Actor;
import ru.mihkopylov.actor.extra.ExtraActor;
import ru.mihkopylov.service.ExtraActorMapperService;

import static java.util.stream.Collectors.toMap;

@Named
@Singleton
public class OperationProcessor {
    @Inject
    private ExtraActorMapperService extraActorMapperService;
    @Inject
    private List<Actor> actors;

    public void run( @NonNull Log log, @NonNull Operation operation, @NonNull List<ExtraActor> extraActors ) {
        log.info( "Running operation " + operation.getName() );
        Map<String, Actor> actorMap = actors.stream().collect( toMap( Actor :: getName, Function.identity() ) );
        log.debug( "extra actors: " + extraActors );
        actorMap.putAll( extraActors.stream()
                .map( extraActorMapperService :: toActor )
                .collect( toMap( Actor :: getName, Function.identity() ) ) );
        log.debug( "actors: " + actorMap );
        Map<String, String> inputOutput = new HashMap<>();
        for (Action action : operation.getActions()) {
            String actorName = action.getActor();
            log.debug( "actor name = " + actorName );
            Actor actor =
                    Objects.requireNonNull( actorMap.get( actorName ), "Can't find actor with name " + actorName );
            log.debug( "actor class = " + actor.getClass() );
            String input = inputOutput.get( action.getInput() );
            log.debug( "input = " + input );
            String output = actor.act( input );
            log.info( String.format( "%s = %s(%s)", output, actorName, input ) );
            inputOutput.put( action.getOutput(), output );
        }
    }
}
