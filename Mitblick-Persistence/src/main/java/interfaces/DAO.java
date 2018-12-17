package interfaces;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    T create(@NotNull T t);

    void update(@NotNull T t);

    void delete(@NotNull T t);

    List<T> getAll();

    Optional<T> getById(@NotNull Long id);
}
