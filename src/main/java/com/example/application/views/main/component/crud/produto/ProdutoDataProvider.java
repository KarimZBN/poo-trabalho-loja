package com.example.application.views.main.component.crud.produto;

import com.example.application.dao.ClienteDAO;
import com.example.application.dao.ProdutoDAO;
import com.example.application.data.entity.ClienteDTO;
import com.example.application.data.entity.ProdutoDTO;
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

public class ProdutoDataProvider extends AbstractBackEndDataProvider<ProdutoDTO, CrudFilter> {

    final List<ProdutoDTO> database = new ArrayList<>(getProdutos());

    private List<ProdutoDTO> getProdutos() {
        List<ProdutoDTO> clientes = new ArrayList<>();
        try {
            clientes.addAll(ProdutoDAO.listarProdutos());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    private Consumer<Long> sizeChangeListener;

    @Override
    protected Stream<ProdutoDTO> fetchFromBackEnd(Query<ProdutoDTO, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<ProdutoDTO> stream = database.stream();

        if (query.getFilter().isPresent()) {
            stream = stream.filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<ProdutoDTO, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }


    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<ProdutoDTO> predicate(CrudFilter filter) {

        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<ProdutoDTO>) produto -> {
                    try {
                        Object value = valueOf(constraint.getKey(), produto);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).reduce(Predicate::and).orElse(e -> true);
    }
    private static Comparator<ProdutoDTO> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream().map(sortClause -> {
            try {
                Comparator<ProdutoDTO> comparator = Comparator.comparing(
                        produto -> (Comparable) valueOf(sortClause.getKey(),
                                produto));

                if (sortClause.getValue() == SortDirection.DESCENDING) {
                    comparator = comparator.reversed();
                }

                return comparator;

            } catch (Exception ex) {
                return (Comparator<ProdutoDTO>) (o1, o2) -> 0;
            }
        }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, ProdutoDTO produto) {
        try {
            Field field = ProdutoDTO.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(produto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void persist(ProdutoDTO item) throws SQLException {
        if (item.getId() == null) {
            item.setId(database.stream().map(ProdutoDTO::getId).max(Comparator.naturalOrder())
                    .orElse(0) + 1);
        }

        final Optional<ProdutoDTO> existingItem = find(item.getId());
        if (existingItem.isPresent()) {
            int position = database.indexOf(existingItem.get());
            database.remove(existingItem.get());
            database.add(position, item);
            ProdutoDTO antigoDados = ProdutoDAO.buscarProdutoPorId(item.getId(), item);
            ProdutoDAO.atualizarProduto(antigoDados);
            database.clear();
            database.addAll(ProdutoDAO.listarProdutos());
        } else {
            ProdutoDAO.adicionarProduto(item);
            database.add(item);
            database.clear();
            database.addAll(ProdutoDAO.listarProdutos());
        }

    }

    Optional<ProdutoDTO> find(Integer id) {
        return database.stream().filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    void delete(ProdutoDTO item) throws SQLException {
        ProdutoDAO.deletarProduto(item.getId());
        database.removeIf(entity -> entity.getId().equals(item.getId()));
    }
}
