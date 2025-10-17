package com.taskmanager.taskmanager.entity;


import com.taskmanager.taskmanager.enums.EnumPriority;
import com.taskmanager.taskmanager.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "задачи")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatus status = EnumStatus.PROCESS;

    @Enumerated(EnumType.STRING)
    private EnumPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline) && status == task.status && Objects.equals(assignee, task.assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadline, status, assignee);
    }
}
