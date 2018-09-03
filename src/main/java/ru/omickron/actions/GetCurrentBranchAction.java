package ru.omickron.actions;

import java.io.File;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.jgit.api.Git;

import static java.util.Objects.isNull;

@Named
public class GetCurrentBranchAction {
    private static final String PREFIX = "refs/heads/";

    @NonNull
    @SneakyThrows
    public String get() {
        Git git = Git.open( new File( "./.git" ) );
        String fullBranchName = git.getRepository().getFullBranch();
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
