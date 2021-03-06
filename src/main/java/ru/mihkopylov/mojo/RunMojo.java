package ru.mihkopylov.mojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.components.interactivity.Prompter;
import ru.mihkopylov.actor.extra.ExtraActor;
import ru.mihkopylov.model.Customization;
import ru.mihkopylov.operation.Action;
import ru.mihkopylov.operation.Operation;
import ru.mihkopylov.operation.OperationProcessor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Mojo(name = "run", aggregator = true)
public class RunMojo extends AbstractMojo {
    @Parameter(property = "operation")
    private String operationName;
    @Parameter
    private List<Operation> operations;
    @Parameter
    private List<ExtraActor> extraActors;
    @Parameter(defaultValue = "release-", required = true)
    private String releaseBranchPrefix;
    @Inject
    private OperationProcessor operationProcessor;
    @Inject
    private MavenProject mavenProject;
    @Inject
    private Prompter prompter;
    @Inject
    private Customization customization;

    @Override
    @SneakyThrows
    public void execute() {
        Map<String, Operation> operationMap = getOperationMap();
        if (isNull( operationName )) {
            operationName =
                    prompter.prompt( String.format( "Enter the operation: %s", operationMap.keySet() ), "patch" );
        }
        Operation operation = Objects.requireNonNull( operationMap.get( operationName.toLowerCase() ),
                "No operation configured with name" );
        customization.setReleaseBranchPrefix( releaseBranchPrefix );
        getLog().info( String.format( "Processing %s operation on project %s:%s:%s", operation.getName(),
                mavenProject.getGroupId(), mavenProject.getArtifactId(), mavenProject.getVersion() ) );
        getLog().debug( String.format( "Customization: %s", customization ) );
        operationProcessor.run( getLog(), operation,
                Optional.ofNullable( extraActors ).orElse( Collections.emptyList() ) );
    }

    @NonNull
    private Map<String, Operation> getOperationMap() {
        Map<String, Operation> result = new HashMap<>();
        for (Operation operation : getDefaultOperations()) {
            result.put( operation.getName().toLowerCase(), operation );
        }
        if (nonNull( operations )) {
            for (Operation operation : operations) {
                result.put( operation.getName().toLowerCase(), operation );
            }
        }
        return result;
    }

    @NonNull
    private List<Operation> getDefaultOperations() {
        List<Operation> result = new ArrayList<>();
        result.add( getMajorOperation() );
        result.add( getMinorOperation() );
        result.add( getPatchOperation() );
        return result;
    }

    @NonNull
    private Operation getMajorOperation() {
        List<Action> actions = new ArrayList<>();
        actions.add( new Action( "getCurrentBranch", null, "originBranch" ) );
        actions.add( new Action( "getCurrentVersion", null, "currentVersion" ) );
        actions.add( new Action( "increaseMajorVersion", "currentVersion", "releaseVersion" ) );
        actions.add( new Action( "releaseVersion", "releaseVersion", "releaseVersion" ) );
        actions.add( new Action( "createReleaseBranchName", "releaseVersion", "releaseBranch" ) );
        actions.add( new Action( "branch", "releaseBranch", null ) );
        actions.add( new Action( "checkout", "releaseBranch", null ) );
        actions.add( new Action( "setVersion", "releaseVersion", null ) );
        actions.add( new Action( "commit", "releaseVersion", null ) );
        actions.add( new Action( "tag", "releaseVersion", null ) );
        actions.add( new Action( "increasePatchVersion", "releaseVersion", "nextVersion" ) );
        actions.add( new Action( "snapshotVersion", "nextVersion", "nextVersion" ) );
        actions.add( new Action( "setVersion", "nextVersion", null ) );
        actions.add( new Action( "commit", "nextVersion", null ) );
        actions.add( new Action( "checkout", "originBranch", null ) );
        actions.add( new Action( "merge", "releaseBranch", null ) );
        return new Operation( "major", actions );
    }

    @NonNull
    private Operation getMinorOperation() {
        List<Action> actions = new ArrayList<>();
        actions.add( new Action( "getCurrentBranch", null, "originBranch" ) );
        actions.add( new Action( "getCurrentVersion", null, "currentVersion" ) );
        actions.add( new Action( "increaseMinorVersion", "currentVersion", "releaseVersion" ) );
        actions.add( new Action( "releaseVersion", "releaseVersion", "releaseVersion" ) );
        actions.add( new Action( "createReleaseBranchName", "releaseVersion", "releaseBranch" ) );
        actions.add( new Action( "branch", "releaseBranch", null ) );
        actions.add( new Action( "checkout", "releaseBranch", null ) );
        actions.add( new Action( "setVersion", "releaseVersion", null ) );
        actions.add( new Action( "commit", "releaseVersion", null ) );
        actions.add( new Action( "tag", "releaseVersion", null ) );
        actions.add( new Action( "increasePatchVersion", "releaseVersion", "nextVersion" ) );
        actions.add( new Action( "snapshotVersion", "nextVersion", "nextVersion" ) );
        actions.add( new Action( "setVersion", "nextVersion", null ) );
        actions.add( new Action( "commit", "nextVersion", null ) );
        actions.add( new Action( "checkout", "originBranch", null ) );
        actions.add( new Action( "merge", "releaseBranch", null ) );
        return new Operation( "minor", actions );
    }

    @NonNull
    private Operation getPatchOperation() {
        List<Action> actions = new ArrayList<>();
        actions.add( new Action( "getCurrentVersion", null, "currentVersion" ) );
        actions.add( new Action( "releaseVersion", "currentVersion", "releaseVersion" ) );
        actions.add( new Action( "setVersion", "releaseVersion", null ) );
        actions.add( new Action( "commit", "releaseVersion", null ) );
        actions.add( new Action( "tag", "releaseVersion", null ) );
        actions.add( new Action( "increasePatchVersion", "releaseVersion", "nextVersion" ) );
        actions.add( new Action( "snapshotVersion", "nextVersion", "nextVersion" ) );
        actions.add( new Action( "setVersion", "nextVersion", null ) );
        actions.add( new Action( "commit", "nextVersion", null ) );
        return new Operation( "patch", actions );
    }
}
