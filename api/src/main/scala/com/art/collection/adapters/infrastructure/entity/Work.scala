package com.art.collection.adapters.infrastructure.entity

import java.time.LocalDateTime
import java.util.UUID

enum WorkVisibility:
  case Public, Private

case class Work(
    id: UUID,
    title: String,
    creator: String,
    description: Option[String],
    workVisibility: WorkVisibility,
    isAiLearning: Boolean,
    createdAt: LocalDateTime
)

case class WorkTag(
    id: Int,
    name: String
)

case class WorkTagAssociations(
    workId: UUID,
    workTagId: Int
)
