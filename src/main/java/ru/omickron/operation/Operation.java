package ru.omickron.operation;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @NonNull
    private String name;
    @NonNull
    private List<Action> actions = new ArrayList<>();
}
