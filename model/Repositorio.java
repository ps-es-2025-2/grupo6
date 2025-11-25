package model;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

/**
 * Repositório genérico responsável por intermediar todas as operações CRUD via ORMLite.
 * Contém métodos básicos e expõe o DAO para consultas específicas.
 */
public class Repositorio<T, ID> {

    private final Class<T> entityClass;
    private final Database database;
    private final Dao<T, ID> dao;

    public Repositorio(Database database, Class<T> entityClass) {
        this.database = database;
        this.entityClass = entityClass;
        try {
            this.dao = DaoManager.createDao(database.getConnection(), entityClass);
            TableUtils.createTableIfNotExists(database.getConnection(), entityClass);
        } catch (SQLException e) {
            throw new IllegalStateException("Falha ao inicializar repositório de " + entityClass.getSimpleName(), e);
        }
    }

    protected Dao<T, ID> getDao() {
        return dao;
    }

    public T create(T entity) throws SQLException {
        dao.createOrUpdate(entity);
        return entity;
    }

    public void update(T entity) throws SQLException {
        dao.update(entity);
    }

    public void delete(T entity) throws SQLException {
        dao.delete(entity);
    }

    public T loadFromId(ID id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<T> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.err.println("Erro ao carregar todos os registros de " + entityClass.getSimpleName() + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Database getDatabase() {
        return database;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}