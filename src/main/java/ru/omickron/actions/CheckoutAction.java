package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.omickron.service.GitService;

@Named
public class CheckoutAction {
    @Inject
    private GitService gitService;

    @SneakyThrows
    public void checkout( @NonNull String branchName ) {
        gitService.git().checkout().setName( branchName ).call();
    }
}
