<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form class="review-bar">
    <img src="/images/default-img.jpg" alt="user photo" ng-if="!currentUser.profilePhotoId" class="comment-avatar">
    <img src="/static/images/users/{{ currentUser.profilePhotoId }}" alt="user photo" ng-if="currentUser.profilePhotoId" class="comment-avatar">
    <input type="text" class="review-input" placeholder="发表评论" id="comment-input" required>
    <input type="submit" value="评论" class="submit-review" id="review-btn" data-id="${param.postId}" data-username="${poster.username}">
</form>
