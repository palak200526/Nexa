package Nexa.example.Nexa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "study_materials")
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String department;
    private String subject;
    private String fileUrl;

    // Many materials belong to one group
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher uploadedBy;

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public Teacher getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Teacher uploadedBy) { this.uploadedBy = uploadedBy; }
}
