<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Leaderboard · MathWhiz</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-shell">
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/"><span class="brand-mark">π</span><span>MathWhiz</span></a>
        <span class="difficulty-pill"><span class="status-dot"></span> TOP 10</span>
    </header>

    <main class="content-width leaderboard-width">
        <section class="page-heading leaderboard-heading">
            <div>
                <p class="eyebrow">THE HALL OF FAME</p>
                <h1>Leaderboard<span class="heading-period">.</span></h1>
                <p class="muted">The top 10 scores, fetched live from the MathWhiz database.</p>
            </div>
            <a class="button button-primary" href="${pageContext.request.contextPath}/">Back to home <span aria-hidden="true">→</span></a>
        </section>

        <c:if test="${not empty databaseError}">
            <div class="alert alert-error" role="alert"><c:out value="${databaseError}"/></div>
        </c:if>
        <c:choose>
            <c:when test="${not empty scores}">
                <div class="table-wrap">
                    <table class="score-table">
                        <thead>
                        <tr><th>RANK</th><th>PLAYER</th><th>SCORE</th><th>DIFFICULTY</th><th>PLAYED AT</th></tr>
                        </thead>
                        <tbody>
                        <c:forEach var="score" items="${scores}" varStatus="status">
                            <tr>
                                <td><span class="rank-number"><c:out value="${status.count}"/></span></td>
                                <td class="player-cell"><c:out value="${score.playerName}"/></td>
                                <td><span class="table-score"><c:out value="${score.score}"/><small>/ 10</small></span></td>
                                <td><span class="normal-tag"><c:out value="${score.difficulty}"/></span></td>
                                <td class="date-cell"><c:out value="${score.playedAt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:when test="${empty databaseError}">
                <section class="empty-state">
                    <span class="empty-icon">∑</span>
                    <h2>Be the first on the board</h2>
                    <p class="muted">No scores have been recorded yet. Play a round to claim the top spot.</p>
                    <a class="button button-primary" href="${pageContext.request.contextPath}/">Start playing <span aria-hidden="true">→</span></a>
                </section>
            </c:when>
        </c:choose>
    </main>
    <footer class="site-footer"><span>MathWhiz · NORMAL</span><span>TOP 10 PLAYERS</span></footer>
</div>
</body>
</html>
