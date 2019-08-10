package ru.mihkopylov.actor;

import java.io.File;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import ru.mihkopylov.service.GitService;

@Named
public class CommitActor implements Actor {
    private static final String SUFFIX = "pom.xml";
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    @Nullable
    public String act( @Nullable String message ) {
        Status status = gitService.git().status().call();
        //add only poms to the commit as only they should be changed
        status.getModified().stream().filter( o -> o.endsWith( SUFFIX ) ).forEach( this :: addFile );
        gitService.git().commit().setMessage( Objects.requireNonNull( message ) ).call();
        return null;
    }

    @SneakyThrows
    private void addFile( String o ) {
        gitService.git().add().addFilepattern( o ).call();
    }

    @SneakyThrows
    private Git createGit() {
        return Git.open( new File( "./.git" ) );
    }
}
