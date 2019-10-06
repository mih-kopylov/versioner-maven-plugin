package ru.mihkopylov.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;
import ru.mihkopylov.service.GitService;

@Named
public class CommitActor implements Actor {
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    @Nullable
    public String act( @Nullable String message ) {
        Git git = gitService.git();
        git.add().addFilepattern( "." ).call();
        git.commit().setMessage( Objects.requireNonNull( message ) ).call();
        return null;
    }
}
