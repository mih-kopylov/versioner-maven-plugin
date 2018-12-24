package ru.omickron.operation;

import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    @NonNull
    private String actor;
    @Nullable
    private String input;
    @Nullable
    private String output;
}
