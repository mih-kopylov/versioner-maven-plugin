package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.BuildPluginManager;
import ru.omickron.model.Version;

import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;

@Named
public class SetVersionAction {
    @Inject
    private MavenSession mavenSession;
    @Inject
    private BuildPluginManager buildPluginManager;

    @SneakyThrows
    public void set( @NonNull Version version ) {
        executeMojo( plugin( "org.codehaus.mojo", "versions-maven-plugin", "2.6" ), goal( "set" ),
                configuration( element( "newVersion", version.toString() ), element( "generateBackupPoms", "false" ) ),
                executionEnvironment( mavenSession, buildPluginManager ) );
    }
}
