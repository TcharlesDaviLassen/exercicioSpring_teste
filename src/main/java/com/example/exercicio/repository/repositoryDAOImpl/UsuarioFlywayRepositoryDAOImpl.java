package com.example.exercicio.repository.repositoryDAOImpl;

import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.UsuarioFlyway;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Repository
public class UsuarioFlywayRepositoryDAOImpl {

    @Autowired
    private EntityManager entityManager;

    public List<UsuarioFlyway> filtrarUsuarios(UsuarioFlyway filtro) {
        //        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //        CriteriaQuery<UsuarioFlyway> criteriaQuery = criteriaBuilder.createQuery(UsuarioFlyway.class);
        //        Root<UsuarioFlyway> root = criteriaQuery.from(UsuarioFlyway.class);
        //
        //        List<Predicate> predicates = new ArrayList<>();
        //
        //        // Adicione critérios de filtro conforme necessário
        //        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
        //            predicates.add((Predicate) criteriaBuilder.like(root.get("nome"), "%" + filtro.getNome() + "%"));
        //        }
        //
        //        if (filtro.getNumero() != null && !filtro.getNumero().isEmpty()) {
        //            predicates.add((Predicate) criteriaBuilder.like(root.get("numero"), "%" + filtro.getNumero() + "%"));
        //        }
        //
        //        if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
        //            predicates.add((Predicate) criteriaBuilder.like(root.get("email"), "%" + filtro.getEmail() + "%"));
        //        }
        //
        //        if (filtro.getData() != null && !filtro.getData().isEmpty()) {
        //            predicates.add((Predicate) criteriaBuilder.like(root.get("data"), "%" + filtro.getData() + "%"));
        //        }
        //
        //        if (filtro.getUsuarioEnumTypeEnum() != null && filtro.getUsuarioEnumTypeEnum().describeConstable().isPresent()) {
        //            predicates.add((Predicate) criteriaBuilder.like(root.get("usuarioEnumTypeEnum"), "%" + filtro.getUsuarioEnumTypeEnum() + "%"));
        //        }
        //
        //        // Combine os predicados com uma condição "AND"
        //        //  criteriaQuery.where(predicates.toArray(new Predicate[0]));
        //
        //        // Execute a consulta
        //        return entityManager.createQuery(criteriaQuery).getResultList();


        StringBuilder queryBuilder = new StringBuilder("SELECT u FROM UsuarioFlyway u WHERE 1=1");

        Map<String, Object> parametros = new HashMap<>();

        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            queryBuilder.append(" AND u.nome LIKE :nome");
            parametros.put("nome", "%" + filtro.getNome() + "%");
        }

        if (filtro.getNumero() != null && !filtro.getNumero().isEmpty()) {
            queryBuilder.append(" AND u.numero LIKE :numero");
            parametros.put("numero", "%" + filtro.getNumero() + "%");
        }

        if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
            queryBuilder.append(" AND u.email LIKE :email");
            parametros.put("email", "%" + filtro.getEmail() + "%");
        }

        if (filtro.getData() != null && !filtro.getData().isEmpty()) {
            queryBuilder.append(" AND u.data LIKE :data");
            parametros.put("data", "%" + filtro.getData() + "%");
        }

        if (filtro.getUsuarioEnumTypeEnum() != null && !filtro.getUsuarioEnumTypeEnum().name().isEmpty()) {
            queryBuilder.append(" AND u.usuarioEnumTypeEnum = :usuarioEnumTypeEnum");
            parametros.put("usuarioEnumTypeEnum", filtro.getUsuarioEnumTypeEnum() );
        }

        TypedQuery<UsuarioFlyway> query = entityManager.createQuery(queryBuilder.toString(), UsuarioFlyway.class);

        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}