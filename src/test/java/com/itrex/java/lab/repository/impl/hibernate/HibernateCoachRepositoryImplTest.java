package com.itrex.java.lab.repository.impl.hibernate;

import com.itrex.java.lab.entity.Coach;
import com.itrex.java.lab.exception.GymException;
import com.itrex.java.lab.exception.NotFoundEx;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.CoachRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class HibernateCoachRepositoryImplTest extends BaseRepositoryTest {
    private final CoachRepository repository;

    public HibernateCoachRepositoryImplTest() {
        super();
        repository = new HibernateCoachRepositoryImpl(getSession());
    }

    @Test
    public void selectAll_validData_shouldReturnExistCoachesTest() throws GymException {
        //given && when
        int expected = 2;
        final List<Coach> result = repository.selectAll();
        int actual = result.size();

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectByID_validData_shouldReturnCorrectCoachTest() throws GymException, NotFoundEx {
        //given
        int id = 1;
        Coach expected = new Coach();
        expected.setId(1);
        expected.setName("Pavel");
        expected.setSurname("Baranov");
        expected.setPhone("+375336543352");
        expected.setSpecialization("bodybuilding");
        expected.setPriceOfActivity(23.50);

        // when
        Coach actual = repository.selectById(id);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void selectById_invalidData_shouldThrowException() {
        //given && when && then
        assertThrows(NotFoundEx.class, () -> repository.selectById(99));
    }

    @Test
    public void addALL_validData_shouldAddAllCoachesTest() throws GymException {
        //given
        ArrayList<Coach> expected = new ArrayList<>();
        Coach coach1 = new Coach();
        coach1.setId(3);
        coach1.setName("Pol");
        coach1.setSurname("Shone");
        coach1.setPhone("+37544582245");
        coach1.setSpecialization("Boxing");
        coach1.setPriceOfActivity(30.00);

        Coach coach2 = new Coach();
        coach2.setId(4);
        coach2.setName("Paul");
        coach2.setSurname("Todd");
        coach2.setPhone("+37544993245");
        coach2.setSpecialization("Fitness");
        coach2.setPriceOfActivity(31.00);
        expected.add(coach1);
        expected.add(coach2);

        //when
        repository.addAll(expected);
        List<Coach> expectedCoaches = repository.selectAll();

        //then
        assertEquals(expectedCoaches.size(), 4);
    }

    @Test
    public void add_validData_shouldAddCoachTest() throws GymException {
        //given
        Coach expected = new Coach();
        expected.setId(3);
        expected.setName("Pol");
        expected.setSurname("Shone");
        expected.setPhone("+37544585645");
        expected.setSpecialization("Fitness");
        expected.setPriceOfActivity(31.00);

        //when
        Coach actual = repository.add(expected);

        //then
        assertEquals(actual, expected);
    }

    @Test
    public void add_invalidData_shouldThrowExceptionTest() {
        //given
        Coach expected = new Coach();
        expected.setId(3);
        expected.setName("Pol");
        expected.setSurname("Shone");
        expected.setPhone("+37544585645");
        expected.setSpecialization(null);
        expected.setPriceOfActivity(25.00);

        //when && then
        assertThrows(GymException.class, () -> repository.add(expected));
    }

    @Test
    public void deleteCoach_validData_shouldDeleteExistCoachTest() throws GymException {
        //given
        int id = 1;
        int expected = 1;

        // when
        repository.deleteCoach(id);
        List<Coach> coaches = repository.selectAll();
        int actual = coaches.size();

        //then
        assertEquals(actual, expected);
    }

    //
    @Test
    public void update_validData_shouldReturnChangedCoachTest() throws GymException {
        //given
        Coach coach = new Coach();
        coach.setId(3);
        coach.setName("test");
        coach.setSurname("test");
        coach.setPhone("test");
        coach.setSpecialization("test");
        coach.setPriceOfActivity(25.00);

        //when
        coach.setName("Pasha");
        repository.add(coach);
        repository.update(coach);
        String actual = coach.getName();
        String expected = "Pasha";

        //then
        assertEquals(expected, actual);
    }
}
