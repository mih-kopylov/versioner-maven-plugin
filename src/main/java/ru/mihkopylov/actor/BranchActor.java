package ru.mihkopylov.actor;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import ru.mihkopylov.service.GitService;

@Named
public class BranchActor implements Actor {
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    @Nullable
    public String act( @Nullable String branchName ) {
        gitService.git().branchCreate().setName( branchName ).call();
        return branchName;
    }
}
