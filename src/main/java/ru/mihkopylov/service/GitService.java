package ru.mihkopylov.service;

import java.io.File;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.eclipse.jgit.api.Git;

@Named
@Singleton
public class GitService {
    @NonNull
    @SneakyThrows
    public Git git() {
        return Git.open( new File( "./.git" ) );
    }
}
