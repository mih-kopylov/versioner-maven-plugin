package ru.omickron;

import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import ru.omickron.processors.ReleaseProcessor;
import ru.omickron.processors.VersionType;

@Mojo(name = "release", aggregator = true)
public class ReleaseMojo extends AbstractMojo {
    @Parameter(defaultValue = "PATCH", property = "type")
    private VersionType type;
    @Inject
    private MavenProject mavenProject;
    @Inject
    private List<ReleaseProcessor> processors;

    @Override
    public void execute() {
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
