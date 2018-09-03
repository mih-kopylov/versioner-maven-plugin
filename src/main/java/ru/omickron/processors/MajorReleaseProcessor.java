package ru.omickron.processors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import org.apache.maven.plugin.logging.Log;
import ru.omickron.actions.BranchAction;
import ru.omickron.actions.CheckoutAction;
import ru.omickron.actions.CommitAction;
import ru.omickron.actions.GetCurrentBranchAction;
import ru.omickron.actions.GetCurrentVersionAction;
import ru.omickron.actions.MergeAction;
import ru.omickron.actions.SetVersionAction;
import ru.omickron.actions.TagAction;
import ru.omickron.model.Version;

@Named
@Singleton
public class MajorReleaseProcessor implements ReleaseProcessor {
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
    @Inject
    private GetCurrentBranchAction getCurrentBranchAction;
    @Inject
    private MergeAction mergeAction;

    @Override
    @NonNull
    public VersionType getType() {
        return VersionType.MAJOR;
    }

    @Override
    public void process( @NonNull Log log ) {
        String originBranchName = getCurrentBranchAction.get();

        Version currentVersion = getCurrentVersionAction.get();
        Version releaseVersion = currentVersion.incMajor().release();
        String releaseBranchName = "release-" + releaseVersion.getMajor() + "." + releaseVersion.getMinor();
        branchAction.create( releaseBranchName );
        checkoutAction.checkout( releaseBranchName );

        setVersionAction.set( releaseVersion );
        commitAction.commit( releaseVersion.toString() );
        tagAction.set( releaseVersion.toString() );
        Version nextVersion = releaseVersion.incPatch().snapshot();
        setVersionAction.set( nextVersion );
        commitAction.commit( nextVersion.toString() );

        checkoutAction.checkout( originBranchName );
        mergeAction.merge( releaseBranchName );
    }
}
