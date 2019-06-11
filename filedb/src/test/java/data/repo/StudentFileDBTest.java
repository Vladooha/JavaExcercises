package data.repo;

import com.vladooha.data.entity.Student;
import com.vladooha.data.repo.StudentFileDB;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class StudentFileDBTest {
    private StudentFileDB studentFileDB;
    private String DBPATH = "/home/vladooha/studentdb";

    @Before
    public void initTest() {
         studentFileDB = new StudentFileDB(DBPATH);
    }

    @Test
    public void getCount_dbWith2UniqueStudents_2rows() {
        // Act
        int size = studentFileDB.getCount();

        // Assert
        assertEquals(2, size);
    }

    @Test
    public void getCountNotUnique_dbWith3Students_3rows() {
        // Act
        int size = studentFileDB.getCountNotUnique();

        // Assert
        assertEquals(3, size);
    }

    @Test
    public void getPath_dbOnDBPATH_samePath() {
        // Act
        String dbPath = studentFileDB.getPath();

        // Assert
        assertEquals(DBPATH, dbPath);
    }

    @Test
    public void getByNum_dbWith3Students_OrderedAsInFileAndOnly3StudentsAnd1And3Same() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student student1 = new Student();
            student1.setSurname("Фамилия");
            student1.setName("Имя");
            student1.setThirdname("Отчество");
            student1.setBirth(formatter.parse("02.02.1999"));

            Student student2 = new Student();
            student2.setSurname("Surname");
            student2.setName("Name");
            student2.setThirdname("Thirname");
            student2.setBirth(formatter.parse("02.02.1989"));

            Student student3 = new Student();
            student3.setSurname("Фамилия");
            student3.setName("Имя");
            student3.setThirdname("Отчество");
            student3.setBirth(formatter.parse("02.02.1999"));

            // Act
            Student student1FromDb = studentFileDB.getByNum(1);
            Student student2FromDb = studentFileDB.getByNum(2);
            Student student3FromDb = studentFileDB.getByNum(3);
            Student student4FromDb = studentFileDB.getByNum(4);

            // Assert
            assertEquals(student1FromDb, student1);
            assertEquals(student2FromDb, student2);
            assertEquals(student3FromDb, student3);
            assertEquals(student4FromDb, null);
            assertEquals(student1FromDb, student3FromDb);
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }

    @Test
    public void getAllOrderedByFile_dbWith2UniqueStudents_2studentsAndOrderedAsInFileAndStudentsOnlyFromFile() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student student1 = new Student();
            student1.setSurname("Фамилия");
            student1.setName("Имя");
            student1.setThirdname("Отчество");
            student1.setBirth(formatter.parse("02.02.1999"));

            Student student2 = new Student();
            student2.setSurname("Surname");
            student2.setName("Name");
            student2.setThirdname("Thirname");
            student2.setBirth(formatter.parse("02.02.1989"));

            Student randomStudent = new Student();
            randomStudent.setSurname("Rand");
            randomStudent.setName("Rand");
            randomStudent.setThirdname("Rand");
            randomStudent.setBirth(formatter.parse("02.02.1999"));

            // Act
            List<Student> students = studentFileDB.getAllOrderedByFile();

            // Assert
            assertEquals(students.size(), 2);
            assertEquals(students.get(0), student1);
            assertEquals(students.get(1), student2);
            assertFalse(students.contains(randomStudent));
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }

    @Test
    public void getAllOrderedByFileNotUnique_dbWith3Students_3studentsAndOrderedAsInFileAndStudentsOnlyFromFile() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student student1 = new Student();
            student1.setSurname("Фамилия");
            student1.setName("Имя");
            student1.setThirdname("Отчество");
            student1.setBirth(formatter.parse("02.02.1999"));

            Student student2 = new Student();
            student2.setSurname("Surname");
            student2.setName("Name");
            student2.setThirdname("Thirname");
            student2.setBirth(formatter.parse("02.02.1989"));

            Student student3 = new Student();
            student3.setSurname("Фамилия");
            student3.setName("Имя");
            student3.setThirdname("Отчество");
            student3.setBirth(formatter.parse("02.02.1999"));

            Student randomStudent = new Student();
            randomStudent.setSurname("Rand");
            randomStudent.setName("Rand");
            randomStudent.setThirdname("Rand");
            randomStudent.setBirth(formatter.parse("02.02.1999"));

            // Act
            List<Student> students = studentFileDB.getAllOrderedByFileNotUnique();

            // Assert
            assertEquals(students.size(), 3);
            assertEquals(students.get(0), student1);
            assertEquals(students.get(1), student2);
            assertEquals(students.get(2), student3);
            assertFalse(students.contains(randomStudent));
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }

    @Test
    public void getAllOrderedByFIOAndBirth_dbWith2UniqueStudents_2studentsAndOrderedByFIOAndBirthAndStudentsOnlyFromFile() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student student1 = new Student();
            student1.setSurname("Surname");
            student1.setName("Name");
            student1.setThirdname("Thirname");
            student1.setBirth(formatter.parse("02.02.1989"));

            Student student2 = new Student();
            student2.setSurname("Фамилия");
            student2.setName("Имя");
            student2.setThirdname("Отчество");
            student2.setBirth(formatter.parse("02.02.1999"));

            Student randomStudent = new Student();
            randomStudent.setSurname("Rand");
            randomStudent.setName("Rand");
            randomStudent.setThirdname("Rand");
            randomStudent.setBirth(formatter.parse("02.02.1999"));

            // Act
            List<Student> students = studentFileDB.getAllOrderedByFIOAndBirth();

            // Assert
            assertEquals(students.size(), 2);
            assertEquals(students.get(0), student1);
            assertEquals(students.get(1), student2);
            assertFalse(students.contains(randomStudent));
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }

    @Test
    public void getAllByCourse_dbWith2UniqueStudentsOnly1From1Course_1StudentAndStudentCourseIs1AndStudentsOnlyFromFile() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student randomStudent = new Student();
            randomStudent.setSurname("Rand");
            randomStudent.setName("Rand");
            randomStudent.setThirdname("Rand");
            randomStudent.setBirth(formatter.parse("02.02.1999"));

            // Act
            List<Student> students = studentFileDB.getAllByCourse(1);

            // Assert
            assertEquals(students.size(), 1);
            assertEquals(students.get(0).getCourse(), 1);
            assertFalse(students.contains(randomStudent));
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }

    @Test
    public void getAllBySubject_dbWith2UniqueStudentsOnly2WithCurrentSubj_2StudentAndStudentSubjIsSubjAndStudentsOnlyFromFile() {
        // Arrange
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Student randomStudent = new Student();
            randomStudent.setSurname("Rand");
            randomStudent.setName("Rand");
            randomStudent.setThirdname("Rand");
            randomStudent.setBirth(formatter.parse("02.02.1999"));

            // Act
            List<Student> students = studentFileDB.getAllBySubject("Subj");

            // Assert
            assertEquals(students.size(), 2);
            assertEquals(students.get(0).getSubject(), "Subj");
            assertEquals(students.get(1).getSubject(), "Subj");
            assertFalse(students.contains(randomStudent));
        } catch (ParseException e) {
            fail("Wrong test stub data");
        }
    }
}
