package ru.mihkopylov.mojo;

import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "release", aggregator = true)
public class ReleaseMojo extends RunMojo {
    @Override
    public void execute() {
        getLog().warn( "`versioner:release` mojo is deprecated. Use `versioner:run` instead" );
        super.execute();
    }
}
