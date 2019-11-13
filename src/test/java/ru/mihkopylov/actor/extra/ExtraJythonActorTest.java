package ru.mihkopylov.actor.extra;

import org.junit.Test;
import ru.mihkopylov.actor.Actor;

import static org.junit.Assert.assertEquals;

public class ExtraJythonActorTest {
    @Test
    public void testCustomActor() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName", ExtraActorEngine.JYTHON,
                "from ru.mihkopylov.actor import Actor as ActorInterface;\n"
                        + "class Actor(ActorInterface):\n"
                        + "    def act(self, input):\n"
                        + "        return \"0.0.0-SNAPSHOT\"\n\n" );
        //@formatter:on
        Actor actor = extraActor.getEngine().createActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "0.0.0-SNAPSHOT", actResult );
    }

    @Test
    public void testCustomActorWithMoreThanOneMethod() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName", ExtraActorEngine.JYTHON,
                "from ru.mihkopylov.actor import Actor as ActorInterface;\n"
                        + "class Actor(ActorInterface):\n"
                        + "    def act(self, input):\n"
                        + "        return self.getValue()\n\n"
                        + "    def getValue(self):\n"
                        + "        return \"0.0.0-SNAPSHOT\"\n\n" );
        //@formatter:on
        Actor actor = extraActor.getEngine().createActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "0.0.0-SNAPSHOT", actResult );
    }
}