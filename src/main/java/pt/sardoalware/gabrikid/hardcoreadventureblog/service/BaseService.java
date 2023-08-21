package pt.sardoalware.gabrikid.hardcoreadventureblog.service;

import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor
public abstract class BaseService {

    public final <T, ID, E extends Throwable> T validateRecordExistence(
            CrudRepository<T, ID> crudRepository,
            ID id,
            Supplier<E> throwableSupplier
    ) throws E {
        Optional<T> recordEntityOptional = crudRepository.findById(id);

        if (recordEntityOptional.isPresent()) {
            return recordEntityOptional.get();
        }
        else {
            throw throwableSupplier.get();
        }
    }

}