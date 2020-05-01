package ui;

import bidriectional.Student;
import bidriectional.University;

import java.util.HashMap;
import java.util.List;

public class MoMain {


    public static HashMap<Student, List<University>> studentListHashMap = new HashMap<>();


    public static void print(List<University> universities) {

        for (University u: universities) {
            System.out.print(u.getName());
            System.out.print(",");
        }
        System.out.println();

    }

    public static void main(String[] args) {
//        University university = new University("UBC");
//        University university1 = new University("SFU");
//        init(university, university1);
//        String student = "5";
//        List<University> universities = studentListHashMap.get(new Student(student,student,student));
//        print(universities);
        new UI();





//
//        try {
//            saveReport(moNeuralNetwork);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int i = 0;
//        int j = i + 1;
//        int k = 0;

//        StringBuilder data = new StringBuilder();
//        Scanner input = new Scanner(System.in);
//        do {
//
//            System.out.print("Enter a neural network: ");
//            String line  = input.nextLine();
//
//            if (line.equals("quit")) {
//                break;
//            }
//            data.append(line).append("\n");
//
//        } while (true);
//
//
//        System.out.print(data + "\n");
//        MoNeuralNetwork moNeuralNetwork = new MoNeuralNetwork(data.toString());
//
//        do {
//            System.out.print("try it out: ");
//            String line = input.nextLine();
//            float[] inputs = MoFunctions.getFloatVersion(line);
//
//            moNeuralNetwork.calculate(inputs);
//
//        } while (true);
    }

    private static void init(University university, University university1) {
        for (int i = 0; i < 100; i++) {
            String name = i + "";
            String email = i + "";
            String address = i + "";
//            String email = name + "@gmail.com";
//            String address = name + "avenue";
            Student student = new Student(name,email,address);
            if (i % 10 == 0) {
                student.acceptUniversity(university);
                student.acceptUniversity(university1);
            } else if (i % 5 == 0) {
                student.acceptUniversity(university);
            } else {
                student.acceptUniversity(university1);
            }
            studentListHashMap.put(student,student.getAcceptedUniversities());
        }
    }
}
