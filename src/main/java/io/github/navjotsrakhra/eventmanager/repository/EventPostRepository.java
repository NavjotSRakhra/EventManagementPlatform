package io.github.navjotsrakhra.eventmanager.repository;

import io.github.navjotsrakhra.eventmanager.dataModel.EventPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPostRepository extends JpaRepository<EventPost, Long> {
}
