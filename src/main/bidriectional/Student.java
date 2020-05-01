package bidriectional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private String fullName;
    private String email;
    private String address;
    private List<University> acceptedUniversities;


    public Student(String name,String email,String address,University...universities) {
        this.fullName = name;
        this.email = email;
        this.address = address;
        this.acceptedUniversities = new ArrayList<>();
        initUniversities(universities);
    }

    private void initUniversities(University[] universities) {
        for (int i = 0; i < universities.length; i++) {
            this.acceptUniversity(universities[i]);
        }
    }

    public void acceptUniversity(University university) {
        if (!this.acceptedUniversities.contains(university)) {
            this.acceptedUniversities.add(university);
            university.acceptStudent(this);
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
        Student student = (Student) o;
        return Objects.equals(fullName, student.fullName)
                && Objects.equals(email, student.email)
                && Objects.equals(address, student.address);
    }

    public List<University> getAcceptedUniversities() {
        return acceptedUniversities;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, address);
    }
}

