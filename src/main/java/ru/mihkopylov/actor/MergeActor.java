package ru.mihkopylov.actor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.ObjectId;
import ru.mihkopylov.service.GitService;

@Named
public class MergeActor implements Actor {
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    public String act( @Nullable String branchName ) {
        ObjectId branchObjectId = gitService.git().getRepository().resolve( branchName );
        MergeResult result = gitService.git()
                .merge()
                .include( branchObjectId )
                .setFastForward( MergeCommand.FastForwardMode.FF_ONLY )
                .call();
        if (!result.getMergeStatus().isSuccessful()) {
            throw new MojoFailureException( String.format( "Can't merge branch '%s'", branchName ) );
        }
        return null;
    }
}
