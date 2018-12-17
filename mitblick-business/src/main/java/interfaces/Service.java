package interfaces;

import exception.BusinessException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface Service<T> {
    T create(@NotNull T t) throws BusinessException;

    void update(@NotNull T t);

    void delete(@NotNull T t);

    List<T> getAll();

    Optional<T> getById(@NotNull Long id);
}
