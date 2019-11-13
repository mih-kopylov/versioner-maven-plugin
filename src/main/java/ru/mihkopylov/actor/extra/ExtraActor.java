package ru.mihkopylov.actor.extra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExtraActor {
    @NonNull
    private String name;
    @NonNull
    private ExtraActorEngine engine = ExtraActorEngine.JYTHON;
    @NonNull
    private String code;
}
