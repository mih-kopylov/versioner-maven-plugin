package ru.omickron.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import ru.omickron.service.GitService;

@Named
public class TagActor implements Actor {
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    public String act( @Nullable String tagName ) {
        gitService.git()
                .tag()
                .setName( Objects.requireNonNull( tagName ) )
                .setMessage( Objects.requireNonNull( tagName ) )
                .call();
        return tagName;
    }
}
