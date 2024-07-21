package com.example.jpa.deo;

import com.example.jpa.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wadi
 * Date 7/21/2024
 **/

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDao {

    private final EntityManager em;

    public List<Employee> findAllBySimpleQuery(
            String firstname,
            String lastname,
            String email
    ) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        //Select * from Employee
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        //prepare where clause
        Predicate firstnamePredicate = criteriaBuilder.
                equal(employeeRoot.get("firstname"), firstname);
        Predicate lastnamePredicate = criteriaBuilder.
                equal(employeeRoot.get("lastname"), lastname);
        Predicate emailPredicate = criteriaBuilder.
                equal(employeeRoot.get("email"), email);
        Predicate orPredicate = criteriaBuilder.
                or(firstnamePredicate,lastnamePredicate, emailPredicate);

        criteriaQuery.where(orPredicate);

        TypedQuery<Employee> query = em.createQuery(criteriaQuery);

        return query.getResultList();

    }



    public List<Employee> findAllByCriteriaQuery(
            SearchRequest request
    ) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        //select from employee
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        if (request.getFirstname() != null) {
            Predicate firstnamePredicate = criteriaBuilder.equal(
                    employeeRoot.get("firstname"), request.getFirstname());
            predicates.add(firstnamePredicate);
        }
        if (request.getLastname() != null) {
            Predicate lastnamePredicate = criteriaBuilder.equal(
                    employeeRoot.get("lastname"), request.getLastname());
            predicates.add(lastnamePredicate);
        }
        if (request.getEmail() != null) {
            Predicate emailPredicate = criteriaBuilder.equal(
                    employeeRoot.get("email"), request.getEmail());
            predicates.add(emailPredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();

    }

}
