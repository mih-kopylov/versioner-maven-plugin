package ru.omickron.actions;

import java.io.File;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.ObjectId;

@Named
public class MergeAction {
    @SneakyThrows
    public void merge( @NonNull String branchName ) {
        Git git = Git.open( new File( "./.git" ) );
        ObjectId branchObjectId = git.getRepository().resolve( branchName );
        MergeResult result =
                git.merge().include( branchObjectId ).setFastForward( MergeCommand.FastForwardMode.FF_ONLY ).call();
        if (!result.getConflicts().isEmpty()) {
            throw new MojoFailureException( String.format( "Can't merge branch '%s'", branchName ) );
        }
    }
}
