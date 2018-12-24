package ru.omickron.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import ru.omickron.service.GitService;

@Named
public class CheckoutActor implements Actor {
    @Inject
    private GitService gitService;

    @SneakyThrows
    @Override
    @Nullable
    public String act( @Nullable String branchName ) {
        gitService.git().checkout().setName( Objects.requireNonNull( branchName ) ).call();
        return branchName;
    }
}
