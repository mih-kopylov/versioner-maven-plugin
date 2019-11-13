package ru.mihkopylov.actor.extra;

import org.junit.Test;
import ru.mihkopylov.actor.Actor;

import static org.junit.Assert.assertEquals;

public class ExtraGroovyActorTest {
    @Test
    public void simpleScript() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName", ExtraActorEngine.GROOVY,
                "import ru.mihkopylov.actor.Actor\n"
                        + "class MyActor implements Actor {\n"
                        + "  def String act(String input) {\n"
                        + "    return '0.0.0-SNAPSHOT'\n"
                        + "  }\n"
                        +"}");
        //@formatter:on
        Actor actor = extraActor.getEngine().createActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "0.0.0-SNAPSHOT", actResult );
    }

    @Test
    public void supportsGrab() {
        //@formatter:off
        ExtraActor extraActor = new ExtraActor( "actorName", ExtraActorEngine.GROOVY,
                "@Grab('org.codehaus.groovy:groovy-json:2.5.8')\n"+
                "import groovy.json.JsonSlurper\n"+
                "import ru.mihkopylov.actor.Actor\n"
                        + "class MyActor implements Actor {\n"
                        + "  def String act(String input) {\n"
                        + "    def jsonSlurper = new JsonSlurper()\n"
                        + "    return jsonSlurper.class.getSimpleName()\n"
                        + "  }\n"
                        +"}");
        //@formatter:on
        Actor actor = extraActor.getEngine().createActor( extraActor );
        String actResult = actor.act( "1.2.3" );
        assertEquals( "JsonSlurper", actResult );
    }
}