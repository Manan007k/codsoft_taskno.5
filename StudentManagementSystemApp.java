import java.io.*;
import java.util.*;

// Class to represent an individual student
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', rollNumber=" + rollNumber + ", grade='" + grade + "'}";
    }
}

// Class to manage the collection of students
class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean removeStudent(int rollNumber) {
        return students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void saveToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(students);
        }
    }

    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (List<Student>) in.readObject();
        }
    }
}

// Main class to run the Student Management System interface
public class StudentManagementSystemApp {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem system = new StudentManagementSystem();
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        try {
            system.loadFromFile(FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("Student Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    saveAndExit();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        if (name.isEmpty() || grade.isEmpty() || rollNumber <= 0) {
            System.out.println("Invalid input. All fields must be filled correctly.");
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        system.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void removeStudent() {
        System.out.print("Enter roll number of student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (system.removeStudent(rollNumber)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void searchStudent() {
        System.out.print("Enter roll number of student to search: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents() {
        List<Student> students = system.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void saveAndExit() {
        try {
            system.saveToFile(FILE_NAME);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
        System.out.println("Exiting application. Thank you!");
    }
}
    

