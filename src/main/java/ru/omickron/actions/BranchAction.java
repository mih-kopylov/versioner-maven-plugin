package ru.omickron.actions;

import java.io.File;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;

@Named
public class BranchAction {
    @SneakyThrows
    public void create( @NonNull String branchName ) {
        Git git = Git.open( new File( "./.git" ) );
        git.branchCreate().setName( branchName ).call();
    }
}
