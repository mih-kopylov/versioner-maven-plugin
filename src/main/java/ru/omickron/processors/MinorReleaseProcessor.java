package ru.omickron.processors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.VersionType;
import ru.omickron.actions.BranchAction;
import ru.omickron.actions.CheckoutAction;
import ru.omickron.actions.CommitAction;
import ru.omickron.actions.GetCurrentVersionAction;
import ru.omickron.actions.SetVersionAction;
import ru.omickron.actions.TagAction;
import ru.omickron.model.Version;

@Named
@Singleton
public class MinorReleaseProcessor implements ReleaseProcessor {
    @Inject
    private SetVersionAction setVersionAction;
    @Inject
    private GetCurrentVersionAction getCurrentVersionAction;
    @Inject
    private CommitAction commitAction;
    @Inject
    private TagAction tagAction;
    @Inject
    private BranchAction branchAction;
    @Inject
    private CheckoutAction checkoutAction;

    @Override
    @NonNull
    public VersionType getType() {
        return VersionType.MINOR;
    }

    @Override
    public void process( @NonNull Log log ) {
        Version currentVersion = getCurrentVersionAction.get();
        Version releaseVersion = currentVersion.incMinor().release();
        String branchName = "release-" + releaseVersion.getMajor() + "." + releaseVersion.getMinor();
        branchAction.create( branchName );
        checkoutAction.checkout( branchName );

        setVersionAction.set( releaseVersion );
        commitAction.commit( releaseVersion.toString() );
        tagAction.set( releaseVersion.toString() );
        Version nextVersion = releaseVersion.incMinor().snapshot();
        setVersionAction.set( nextVersion );
        commitAction.commit( nextVersion.toString() );
    }
}
