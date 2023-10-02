package com.example.application.views.main.component.crud.cliente;

import com.example.application.dao.ClienteDAO;
import com.example.application.data.entity.ClienteDTO;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ClienteDataProvider extends AbstractBackEndDataProvider<ClienteDTO, CrudFilter> {

    final List<ClienteDTO> database = new ArrayList<>(getClientes());

    private List<ClienteDTO> getClientes() {
        List<ClienteDTO> clientes = new ArrayList<>();
        try {
            clientes.addAll(ClienteDAO.getClientes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    private Consumer<Long> sizeChangeListener;

    @Override
    protected Stream<ClienteDTO> fetchFromBackEnd(Query<ClienteDTO, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<ClienteDTO> stream = database.stream();

        if (query.getFilter().isPresent()) {
            stream = stream.filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<ClienteDTO, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<ClienteDTO> predicate(CrudFilter filter) {

        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<ClienteDTO>) cliente -> {
                    try {
                        Object value = valueOf(constraint.getKey(), cliente);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).reduce(Predicate::and).orElse(e -> true);
    }
    private static Comparator<ClienteDTO> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream().map(sortClause -> {
            try {
                Comparator<ClienteDTO> comparator = Comparator.comparing(
                        cliente -> (Comparable) valueOf(sortClause.getKey(),
                                cliente));

                if (sortClause.getValue() == SortDirection.DESCENDING) {
                    comparator = comparator.reversed();
                }

                return comparator;

            } catch (Exception ex) {
                return (Comparator<ClienteDTO>) (o1, o2) -> 0;
            }
        }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, ClienteDTO cliente) {
        try {
            Field field = ClienteDTO.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(cliente);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void persist(ClienteDTO item) throws SQLException {
        if (item.getId() == null) {
            item.setId(database.stream().map(ClienteDTO::getId).max(Comparator.naturalOrder())
                    .orElse(0) + 1);
        }

        final Optional<ClienteDTO> existingItem = find(item.getId());
        if (existingItem.isPresent()) {
            int position = database.indexOf(existingItem.get());
            database.remove(existingItem.get());
            database.add(position, item);
            ClienteDTO antigoDados = ClienteDAO.buscaClienteId(item.getId(), item);
            ClienteDAO.atualizaCliente(antigoDados);
        } else {
            database.add(item);
            ClienteDAO.adicionarCliente(item);
        }

    }

    Optional<ClienteDTO> find(Integer id) {
        return database.stream().filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    void delete(ClienteDTO item) throws SQLException {
        ClienteDAO.excluirCliente(item.getId());
        database.removeIf(entity -> entity.getId().equals(item.getId()));
    }
}
