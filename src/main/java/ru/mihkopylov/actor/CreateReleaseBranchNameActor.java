package ru.mihkopylov.actor;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import ru.mihkopylov.model.Customization;
import ru.mihkopylov.model.Version;

@Named
public class CreateReleaseBranchNameActor implements Actor {
    @Inject
    private Customization customization;

    @Nullable
    @Override
    public String act( @Nullable String versionName ) {
        Version version = Version.parse( Objects.requireNonNull( versionName ) );
        return customization.getReleaseBranchPrefix() + version.getMajor() + "." + version.getMinor();
    }
}
