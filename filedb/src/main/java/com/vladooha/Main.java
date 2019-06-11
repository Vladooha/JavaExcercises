package com.vladooha;

import com.vladooha.data.entity.Student;
import com.vladooha.data.repo.StudentFileDB;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentFileDB studentFileDB = null;

        int switcher = ' ';
        while (switcher != 'q' || switcher != 'Q') {
            System.out.println(
                    String.format(
                            "File: %s",
                            studentFileDB != null ? studentFileDB.getPath() : "*none*"));
            System.out.println(
                    String.format(
                            "Students count: %s [Unique students: %s]",
                            studentFileDB != null ? studentFileDB.getCountNotUnique() : "*none*",
                            studentFileDB != null ? studentFileDB.getCount() : "*none*"));
            System.out.println("==============");
            System.out.println("Menu:");
            System.out.println("==============");
            System.out.println("1. Choose file");
            System.out.println("2. All unique students in file order");
            System.out.println("3. All unique students sorted by FIO and birth");
            System.out.println("4. All unique students by course");
            System.out.println("5. All unique students by subject");
            System.out.println("q. Exit");
            System.out.println();

            switcher = scanner.nextInt();
            String trash = scanner.nextLine();

            switch (switcher) {
                case 1:
                    System.out.println("Enter file path:");
                    String filePath = scanner.nextLine();
                    studentFileDB = new StudentFileDB(filePath);
                    break;
                case 2:
                    if (studentFileDB != null) {
                        printList(studentFileDB.getAllOrderedByFile());
                        System.out.println("Press 'Enter' to continue...");
                        System.out.println();
                        scanner.nextLine();
                    } else {
                        System.err.println("Choose db file first!");
                    }
                    break;
                case 3:
                    if (studentFileDB != null) {
                        printList(studentFileDB.getAllOrderedByFIOAndBirth());
                        System.out.println("Press 'Enter' to continue...");
                        System.out.println();
                        System.out.println();
                        scanner.nextLine();
                    } else {
                        System.err.println("Choose db file first!");
                    }
                    break;
                case 4:
                    if (studentFileDB != null) {
                        System.out.println("Enter course num: ");
                        Integer courseNum = scanner.nextInt();
                        trash = scanner.nextLine();
                        printList(studentFileDB.getAllByCourse(courseNum));
                        System.out.println("Press 'Enter' to continue...");
                        System.out.println();
                        scanner.nextLine();
                    } else {
                        System.err.println("Choose db file first!");
                    }
                    break;
                case 5:
                    if (studentFileDB != null) {
                        System.out.println("Enter subject name: ");
                        String subjName = scanner.nextLine();
                        printList(studentFileDB.getAllBySubject(subjName));
                        System.out.println("Press 'Enter' to continue...");
                        scanner.nextLine();
                    } else {
                        System.err.println("Choose db file first!");
                    }
                    break;
            }

            //trash = scanner.nextLine();
        }
    }

    private static void printList(List<Student> students) {
        System.out.println("List:");
        System.out.println("==============");
        for (int i = 0; i < students.size(); ++i) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }
}
