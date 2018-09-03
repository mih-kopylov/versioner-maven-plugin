package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import ru.omickron.service.GitService;

import static java.util.Objects.isNull;

@Named
public class GetCurrentBranchAction {
    /**
     * local branches names prefix
     */
    private static final String PREFIX = "refs/heads/";
    @Inject
    private GitService gitService;

    @NonNull
    @SneakyThrows
    public String get() {
        String fullBranchName = gitService.git().getRepository().getFullBranch();
        if (isNull( fullBranchName )) {
            throw new MojoFailureException( "No current branch found" );
        }
        if (!fullBranchName.startsWith( PREFIX )) {
            throw new MojoFailureException(
                    String.format( "Current branch '%s' doesn't start with '%s'", fullBranchName, PREFIX ) );
        }
        return fullBranchName.substring( PREFIX.length() );
    }
}
