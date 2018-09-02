package ru.omickron;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "release", aggregator = true)
public class ReleaseMojo extends AbstractMojo {
    @Parameter(defaultValue = "PATCH", property = "type")
    private VersionType type;
    @Component
    private MavenProject mavenProject;
    @Component
    private PluginDescriptor pluginDescriptor;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info( "type: " + type );
        getLog().info(
                String.format( "Processing project %s:%s:%s", mavenProject.getGroupId(), mavenProject.getArtifactId(),
                        mavenProject.getVersion() ) );
    }
}
