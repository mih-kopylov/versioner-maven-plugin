package ru.mihkopylov.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.SneakyThrows;
import ru.mihkopylov.model.Version;
import ru.mihkopylov.service.GitService;

@Named
public class BranchActor implements Actor {
    @Inject
    private GitService gitService;

    @Override
    @SneakyThrows
    @Nullable
    public String act( @Nullable String versionName ) {
        Version version = Version.parse( Objects.requireNonNull( versionName ) );
        String branchName = "release-" + version.getMajor() + "." + version.getMinor();
        gitService.git().branchCreate().setName( branchName ).call();
        return branchName;
    }
}