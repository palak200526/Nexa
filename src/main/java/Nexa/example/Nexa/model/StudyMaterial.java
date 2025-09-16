package Nexa.example.Nexa.model;

import jakarta.persistence.*;

@Entity
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher uploadedBy;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String studentName;
    private String studentId;
    private String title; // title of the study material
    private String description; // optional description
    private String fileUrl; // link to uploaded file (can be local path or cloud link)

    public StudyMaterial() {
    }

    public StudyMaterial(String studentName, String studentId, String title, String description, String fileUrl) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
    }

    // Getters and Setters

    public Teacher getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(Teacher uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
