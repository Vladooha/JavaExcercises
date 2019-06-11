package com.vladooha.data.repo;

import com.sun.istack.internal.Nullable;
import com.vladooha.data.entity.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StudentFileDB {
    private File file;
    private List<Student> students;

    public StudentFileDB(String path) {
        file = new File(path);
        students = new LinkedList<>();

        parseFile();
    }

    public Student getByNum(int num) {
        if (num < 1) {
            return null;
        }

        Iterator<Student> iter = students.iterator();
        int index = 0;
        while (iter.hasNext()) {
            index++;
            Student nextStudent = iter.next();

            if (index == num) {
                return nextStudent;
            }
        }

        return null;
    }

    public List<Student> getAllOrderedByFile() {
        return students
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Student> getAllOrderedByFileNotUnique() {
        return students;
    }

    public List<Student> getAllOrderedByFIOAndBirth() {
        return students
                .stream()
                .distinct()
                .sorted((s1, s2) -> {
                    int compareResult = s1.getSurname().compareToIgnoreCase(s2.getSurname());
                    if (compareResult != 0) {
                        return compareResult;
                    }

                    compareResult = s1.getName().compareToIgnoreCase(s2.getName());
                    if (compareResult != 0) {
                        return compareResult;
                    }

                    compareResult = s1.getThirdname().compareToIgnoreCase(s2.getThirdname());
                    if (compareResult != 0) {
                        return compareResult;
                    }

                    compareResult = s1.getBirth().compareTo(s2.getBirth());
                    return compareResult;
                })
                .collect(Collectors.toList());
    }

    public List<Student> getAllByCourse(int course) {
        return students
                .stream()
                .filter(s -> s.getCourse() == course)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Student> getAllBySubject(String subject) {
        return students
                .stream()
                .filter(s -> s.getSubject().equals(subject))
                .distinct()
                .collect(Collectors.toList());
    }

    @Nullable
    public String getPath() {
        if (file != null) {
            return file.getAbsolutePath();
        }

        return null;
    }

    public int getCount() {
        return students
                .stream()
                .distinct()
                .collect(Collectors.toList())
                .size();
    }
    public int getCountNotUnique() {
        return students.size();
    }

    private boolean parseFile() {
        try {
            FileInputStream fis = new FileInputStream(file);
            Scanner scanner = new Scanner(fis);
            Student buffStudent = null;
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();

                if (buffStudent == null) {
                    if (currLine.matches(
                            "[a-zA-Zа-яА-Я]{2,20}, [a-zA-Zа-яА-Я]{2,20}, [a-zA-Zа-яА-Я]{2,20}, " +
                                    "[мМжЖ]{1,1}, [0-9]{2}\\.[0-9]{2}\\.[0-9]{4}, [0-9]{1}")) {
                        try {
                            currLine = currLine.replace(" ", "");
                            String[] fields = currLine.split(",");
                            buffStudent = new Student();
                            buffStudent.setSurname(fields[0]);
                            buffStudent.setName(fields[1]);
                            buffStudent.setThirdname(fields[2]);
                            buffStudent.setSex(fields[3].matches("[мМ]?"));
                            Date birth = new SimpleDateFormat("dd.MM.yyyy").parse(fields[4]);
                            buffStudent.setBirth(birth);
                            buffStudent.setCourse(Integer.parseInt(fields[5]));
                        } catch (Exception e) {
                            buffStudent = null;
                        }
                    }
                } else {
                    buffStudent.setSubject(currLine);
                    students.add(buffStudent);
                    buffStudent = null;
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Can't open file '%s'", file));

            return false;
        }
    }
}
