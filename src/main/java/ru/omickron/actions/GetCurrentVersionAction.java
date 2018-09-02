package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import org.apache.maven.project.MavenProject;
import ru.omickron.model.Version;

@Named
public class GetCurrentVersionAction {
    @Inject
    private MavenProject mavenProject;

    @NonNull
    public Version get() {
        return Version.parse( mavenProject.getVersion() );
    }
}
