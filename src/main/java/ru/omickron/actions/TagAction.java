package ru.omickron.actions;

import javax.inject.Inject;
import javax.inject.Named;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.omickron.service.GitService;

@Named
public class TagAction {
    @Inject
    private GitService gitService;

    @SneakyThrows
    public void set( @NonNull String tagName ) {
        gitService.git().tag().setName( tagName ).setMessage( tagName ).call();
    }
}
