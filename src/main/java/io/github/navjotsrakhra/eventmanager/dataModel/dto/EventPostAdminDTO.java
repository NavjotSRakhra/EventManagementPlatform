/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.dataModel.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The EventPostAdminDTO class represents an exposed version of an EventPost, providing a simplified interface for creating event posts.
 * This DTO is used for admin purposes.
 *
 * @param id        The ID of the event post.
 * @param title     The title of the event.
 * @param content   The content of the event.
 * @param location  The location of the event.
 * @param startDay  The start day of the event.
 * @param endDay    The end day of the event.
 * @param startTime The start time of the event.
 * @param endTime   The end time of the event.
 * @param postedBy  The user who posted the event.
 * @param postedAt  The date and time the event was posted.
 */
public record EventPostAdminDTO(Long id, String title, String content, String location,
                                String enrollmentLink, String image,
                                LocalDate startDay, LocalDate endDay,
                                LocalTime startTime, LocalTime endTime, String postedBy,
                                java.time.LocalDateTime postedAt) {
}
