<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your result · MathWhiz</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-shell">
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/"><span class="brand-mark">π</span><span>MathWhiz</span></a>
        <span class="difficulty-pill"><span class="status-dot"></span> ROUND COMPLETE</span>
    </header>

    <main class="content-width result-width">
        <section class="result-hero">
            <p class="eyebrow">ROUND COMPLETE · <c:out value="${gameResult.playerName}"/></p>
            <h1><c:out value="${gameResult.performanceMessage}"/></h1>
            <div class="score-display"><strong><c:out value="${gameResult.score}"/></strong><span>/ 10</span></div>
            <p class="muted">Your final score</p>
        </section>

        <c:choose>
            <c:when test="${gameResult.saved}">
                <div class="alert alert-success" role="status"><c:out value="${gameResult.saveMessage}"/></div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning" role="status"><c:out value="${gameResult.saveMessage}"/></div>
            </c:otherwise>
        </c:choose>

        <section class="results-list" aria-label="Question results">
            <c:forEach var="item" items="${gameResult.questionResults}">
                <article class="result-row">
                    <span class="result-indicator ${item.correct ? 'is-correct' : 'is-incorrect'}" aria-hidden="true">
                        <c:out value="${item.correct ? '✓' : '×'}"/>
                    </span>
                    <div class="result-question">
                        <span class="topic-label">QUESTION 0<c:out value="${item.question.number}"/></span>
                        <h2><c:out value="${item.question.prompt}"/></h2>
                        <p>Your answer: <strong><c:out value="${item.submittedAnswer}"/></strong>
                            <c:if test="${not item.correct}"> · Correct answer: <strong><c:out value="${item.correctAnswer}"/></strong></c:if>
                        </p>
                    </div>
                    <div class="result-marks">
                        <strong><c:out value="${item.earnedMarks}"/></strong>
                        <span>/ <c:out value="${item.question.marks}"/> pts</span>
                    </div>
                </article>
            </c:forEach>
        </section>

        <div class="action-row">
            <a class="button button-primary" href="${pageContext.request.contextPath}/">Play again <span aria-hidden="true">→</span></a>
            <a class="button button-secondary" href="${pageContext.request.contextPath}/game?action=leaderboard">View leaderboard</a>
        </div>
    </main>
    <footer class="site-footer"><span>MathWhiz · NORMAL</span><span>THANK YOU FOR PLAYING</span></footer>
</div>
</body>
</html>
