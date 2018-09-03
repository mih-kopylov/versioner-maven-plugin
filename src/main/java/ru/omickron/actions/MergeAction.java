package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.ObjectId;
import ru.omickron.service.GitService;

@Named
public class MergeAction {
    @Inject
    private GitService gitService;

    @SneakyThrows
    public void merge( @NonNull String branchName ) {
        ObjectId branchObjectId = gitService.git().getRepository().resolve( branchName );
        MergeResult result = gitService.git()
                .merge()
                .include( branchObjectId )
                .setFastForward( MergeCommand.FastForwardMode.FF_ONLY )
                .call();
        if (!result.getConflicts().isEmpty()) {
            throw new MojoFailureException( String.format( "Can't merge branch '%s'", branchName ) );
        }
    }
}
