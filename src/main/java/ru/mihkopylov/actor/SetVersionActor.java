package ru.mihkopylov.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.BuildPluginManager;

import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;

@Named
public class SetVersionActor implements Actor {
    @Inject
    private MavenSession mavenSession;
    @Inject
    private BuildPluginManager buildPluginManager;

    @Override
    @SneakyThrows
    public String act( @Nullable String version ) {
        executeMojo( plugin( "org.codehaus.mojo", "versions-maven-plugin", "2.6" ), goal( "set" ),
                configuration( element( "newVersion", Objects.requireNonNull( version ) ),
                        element( "generateBackupPoms", "false" ) ),
                executionEnvironment( mavenSession, buildPluginManager ) );
        return version;
    }
}
