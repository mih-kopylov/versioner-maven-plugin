package ru.omickron;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VersionType {
    MAJOR( true ),
    MINOR( true ),
    PATCH( false );
    private final boolean createBranch;
}
