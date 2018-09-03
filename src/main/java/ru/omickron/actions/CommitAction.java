package ru.omickron.actions;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import ru.omickron.service.GitService;

@Named
public class CommitAction {
    private static final String SUFFIX = "pom.xml";
    @Inject
    private GitService gitService;

    @SneakyThrows
    public void commit( @NonNull String message ) {
        Status status = gitService.git().status().call();
        //add only poms to the commit as only they should be changed
        status.getModified().stream().filter( o -> o.endsWith( SUFFIX ) ).forEach( this :: addFile );
        gitService.git().commit().setMessage( message ).call();
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
