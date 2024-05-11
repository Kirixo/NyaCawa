package com.project.nyacawa.domain.adapters

import com.project.nyacawa.data.Comment


typealias onDislikeCommentClick = (Comment) -> Unit
typealias onLikeCommentClick = (Comment) -> Unit
typealias onShareCommentClick = (Comment) -> Unit