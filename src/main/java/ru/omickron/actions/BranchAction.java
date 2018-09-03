package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.omickron.service.GitService;

@Named
public class BranchAction {
    @Inject
    private GitService gitService;

    @SneakyThrows
    public void create( @NonNull String branchName ) {
        gitService.git().branchCreate().setName( branchName ).call();
    }
}
