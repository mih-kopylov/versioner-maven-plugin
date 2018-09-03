package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
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
public class BranchAction {
    @Inject
    private MavenSession mavenSession;
    @Inject
    private BuildPluginManager buildPluginManager;

    @SneakyThrows
    public void create( @NonNull String branchName ) {
        executeMojo( plugin( "org.apache.maven.plugins", "maven-scm-plugin", "1.10.0" ), goal( "branch" ),
                configuration( element( "basedir", "." ), element( "branch", branchName ) ),
                executionEnvironment( mavenSession, buildPluginManager ) );
    }
}
