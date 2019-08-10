package ru.mihkopylov.actor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import org.apache.maven.project.MavenProject;
import ru.mihkopylov.model.Version;

@Named
public class GetCurrentVersionActor implements Actor {
    @Inject
    private MavenProject mavenProject;

    @Override
    @NonNull
    public String act( @Nullable String unused ) {
        return Version.parse( mavenProject.getVersion() ).toString();
    }
}
