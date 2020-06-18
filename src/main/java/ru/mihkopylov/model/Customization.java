package ru.mihkopylov.model;

import javax.inject.Named;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Named
@Singleton
@Getter
@Setter
@ToString
public class Customization {
    private String releaseBranchPrefix;
}
