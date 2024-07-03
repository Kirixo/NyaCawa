package com.project.nyacawa.domain.adapters.comment

import com.project.nyacawa.data.Comment


typealias onDislikeCommentClick = (Comment) -> Unit
typealias onLikeCommentClick = (Comment) -> Unit
typealias onShareCommentClick = (Comment) -> Unit