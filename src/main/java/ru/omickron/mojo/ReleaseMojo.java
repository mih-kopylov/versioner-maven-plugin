package ru.omickron.mojo;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.components.interactivity.Prompter;
import ru.omickron.processors.ReleaseProcessor;
import ru.omickron.processors.VersionType;

import static java.util.Objects.isNull;

@Mojo(name = "release", aggregator = true)
public class ReleaseMojo extends AbstractMojo {
    @Parameter(property = "type")
    private VersionType type;
    @Inject
    private MavenProject mavenProject;
    @Inject
    private List<ReleaseProcessor> processors;
    @Inject
    private Prompter prompter;

    @Override
    @SneakyThrows
    public void execute() {
        if (isNull( type )) {
            String prompt =
                    prompter.prompt( String.format( "Enter the type: %s", Arrays.toString( VersionType.values() ) ),
                            VersionType.PATCH.name() ).toUpperCase();
            type = VersionType.valueOf( prompt );
        }
        getLog().info( String.format( "Processing %s operation on project %s:%s:%s", type, mavenProject.getGroupId(),
                mavenProject.getArtifactId(), mavenProject.getVersion() ) );
        ReleaseProcessor processor = getProcessor( type );
        processor.process( getLog() );
    }

    @NonNull
    private ReleaseProcessor getProcessor( @NonNull final VersionType type ) {
        return processors.stream()
                .filter( o -> o.getType().equals( type ) )
                .findAny()
                .orElseThrow( () -> new RuntimeException( "No processor found for type " + type ) );
    }
}
