package com.itrex.java.lab;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.Training;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.repository.CoachRepository;
import com.itrex.java.lab.repository.RoleRepository;
import com.itrex.java.lab.repository.TrainingRepository;
import com.itrex.java.lab.repository.UserRepository;
import com.itrex.java.lab.repository.impl.hibernate.HibernateCoachRepositoryImpl;
import com.itrex.java.lab.repository.impl.hibernate.HibernateRoleRepositoryImpl;
import com.itrex.java.lab.repository.impl.hibernate.HibernateTrainingRepositoryImpl;
import com.itrex.java.lab.repository.impl.hibernate.HibernateUserRepositoryImpl;
import com.itrex.java.lab.service.FlywayService;
import com.itrex.java.lab.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RunnerHibernate {

    public static void main(String[] args) throws GymException, SQLException {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        System.out.println("============CREATE SESSION================");
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("=============CREATE UserRepository================");

        UserRepository userRepository = new HibernateUserRepositoryImpl(session);
        RoleRepository roleRepository = new HibernateRoleRepositoryImpl(session);
        CoachRepository coachRepository = new HibernateCoachRepositoryImpl(session);
        TrainingRepository trainingRepository = new HibernateTrainingRepositoryImpl(session);

//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//
//            userRepository.delete(1);
//            System.out.println("User with id=1 deleted");
//
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new GymException("Delete user EXCEPTION: ", ex);
//        }
//
        List<User> users = userRepository.selectAll();
        System.out.println(users);


//        List<Role> roles = roleRepository.selectAll();
//        System.out.println(roles);

//        List<Coach> coaches  = coachRepository.selectAll();
//        System.out.println(coaches);

//        transaction = null;
//        try {
//            transaction = session.beginTransaction();
//
//            trainingRepository.deleteTrainingByCoach(2);
//            System.out.println("Training with coach id=2 deleted");
//
//            transaction.commit();
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new GymException("Delete user EXCEPTION: ", ex);
//        }
//        List<Training> trainings = trainingRepository.selectAll();
//        System.out.println(trainings);

        System.out.println("===========================");
        System.out.println(userRepository.getAllUsersByRole("client"));
        System.out.println("===========================");
        System.out.println(roleRepository.getAllUserRoles(2));
        System.out.println("=================CLOSE SESSION====================");
        session.close();
        System.out.println("=================SHUT DOWN APP====================");
    }
}
