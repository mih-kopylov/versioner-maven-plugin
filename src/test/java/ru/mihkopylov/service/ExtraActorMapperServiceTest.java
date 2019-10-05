package ru.mihkopylov.service;

import org.junit.Test;
import ru.mihkopylov.actor.Actor;
import ru.mihkopylov.actor.extra.ExtraActor;

import static org.junit.Assert.assertEquals;

public class ExtraActorMapperServiceTest {
    private final ExtraActorMapperService service = new ExtraActorMapperService();

    @Test
    public void testCustomActor() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName",
                "from ru.mihkopylov.actor import Actor as ActorInterface;\n"
                        + "class Actor(ActorInterface):\n"
                        + "    def act(self, input):\n"
                        + "        return \"0.0.0-SNAPSHOT\"\n\n" );
        //@formatter:on
        Actor actor = service.toActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "0.0.0-SNAPSHOT", actResult );
    }

    @Test
    public void testCustomActorWithMoreThanOneMethod() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName",
                "from ru.mihkopylov.actor import Actor as ActorInterface;\n"
                        + "class Actor(ActorInterface):\n"
                        + "    def act(self, input):\n"
                        + "        return self.getValue()\n\n"
                        + "    def getValue(self):\n"
                        + "        return \"0.0.0-SNAPSHOT\"\n\n" );
        //@formatter:on
        Actor actor = service.toActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "0.0.0-SNAPSHOT", actResult );
    }
}