package bidriectional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class University {

    private String name;
    private List<Student> acceptedStudents;

    public University(String name) {
        this.name = name;
        this.acceptedStudents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void acceptStudent(Student student) {
        if (!this.acceptedStudents.contains(student)) {
            this.acceptedStudents.add(student);
            student.acceptUniversity(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        University that = (University) o;
        return Objects.equals(name, that.name)
                && Objects.equals(acceptedStudents, that.acceptedStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, acceptedStudents);
    }
}
