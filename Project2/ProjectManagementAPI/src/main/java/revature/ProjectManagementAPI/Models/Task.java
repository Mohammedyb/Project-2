package revature.ProjectManagementAPI.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "description", length = 60)
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "due_time")
    private LocalTime dueTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user")
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project projects;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", dueTime=" + dueTime +
                ", users=" + users +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId().equals(task.getId()) && getName().equals(task.getName()) && getDescription().equals(task.getDescription()) && getDueDate().equals(task.getDueDate()) && getDueTime().equals(task.getDueTime()) && getUsers().equals(task.getUsers()) && getProjects().equals(task.getProjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getDueDate(), getDueTime(), getUsers(), getProjects());
    }
}
